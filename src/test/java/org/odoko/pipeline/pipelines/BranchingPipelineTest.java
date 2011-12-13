package org.odoko.pipeline.pipelines;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.config.YamlConfiguration;
import org.odoko.pipeline.dispatchers.MockDispatcher;
import org.odoko.pipeline.handlers.AssetHandler;
import org.odoko.pipeline.handlers.UniqueUriAssetHandler;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.model.Asset;

public class BranchingPipelineTest {

	private static final String DB_FILE = "target/database.tmp";
	private static final String DEFAULT_QUEUE = "default";

	@Before
	public void setUp() {
		File db = new File(DB_FILE);
		if (db.exists()) {
			db.delete();
		}
	}

	@Test
	public void testBranchingPipeline() throws IOException, ConfigurationException {
		Configuration config = new YamlConfiguration();
		config.parse("src/test/resources/config/sample3.yaml");
		PipelineBuilder builder = new PipelineBuilder();
		Pipeline pipeline = builder.build(config, config.getPipeline("default"));
		Pipeline subpipe = builder.build(config, config.getPipeline("subpipe"));
		
		AssetHandler handler = new UniqueUriAssetHandler(DB_FILE);
		pipeline.setHandler(handler);

		Locator locator = builder.buildLocator(config, config.getLocator("default"));
		
		handler.addAssets(DEFAULT_QUEUE, locator.locate());

		int i=0;
		while (handler.hasNext(DEFAULT_QUEUE)) {
			i++;
			pipeline.process(handler.nextAsset(DEFAULT_QUEUE));
		}

		for (String queueName : handler.getQueueNames()) {
			if (!queueName.equals(DEFAULT_QUEUE)) {
				int j=0;
				while (handler.hasNext(queueName)) {
					j++;
					subpipe.process(handler.nextAsset(queueName));
				}
			}
		}
		
		List<Component> components = subpipe.getComponents(); 
		MockDispatcher dispatcher = (MockDispatcher)components.get(components.size()-1);
		List<Asset> finalAssets = dispatcher.getDispatchedAssets();
		assertEquals("Incorrect number of assets dispatched", 10, finalAssets.size());
		assertEquals("First asset not text/plain", "text/plain", finalAssets.get(0).getContentType());
		assertEquals("First asset is not upper-case, transformer did not work", "CONTENT FROM URL HTTP://WWW.ODOKO.ORG/ARTICLE-0", finalAssets.get(0).getValue());
	}
}
