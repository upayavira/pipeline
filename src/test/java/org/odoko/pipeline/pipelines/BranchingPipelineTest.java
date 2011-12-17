package org.odoko.pipeline.pipelines;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.odoko.pipeline.branchers.Brancher;
import org.odoko.pipeline.branchers.MockBrancher;
import org.odoko.pipeline.collectors.MockCollector;
import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.config.YamlConfiguration;
import org.odoko.pipeline.dispatchers.MockDispatcher;
import org.odoko.pipeline.handlers.AssetHandler;
import org.odoko.pipeline.handlers.UniqueUriAssetHandler;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.locators.MockLocator;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.transformers.MockTransformer;

public class BranchingPipelineTest {

	private static final String DB_FILE = "target/database.tmp";
	private static final String DEFAULT_QUEUE = "default";
	private static final String SECOND_QUEUE = "queue2";

	@Before
	public void setUp() {
		File db = new File(DB_FILE);
		if (db.exists()) {
			db.delete();
		}
	}

	@Test
	public void testBranchingPipeline() throws IOException, ConfigurationException {
		Pipeline pipeline1 = new Pipeline();
		Pipeline pipeline2 = new Pipeline();
		pipeline1.addComponent(new MockCollector());
		Brancher brancher = new MockBrancher();
		brancher.setQueue(SECOND_QUEUE);
		pipeline1.addComponent(brancher);
		pipeline1.wire();
		pipeline2.addComponent(new MockTransformer());
		MockDispatcher dispatcher = new MockDispatcher();
		pipeline2.addComponent(dispatcher);
		pipeline2.wire();
		AssetHandler handler = new UniqueUriAssetHandler(DB_FILE);
		pipeline1.setHandler(handler);

		Locator locator = new MockLocator();
		
		handler.addAssets(DEFAULT_QUEUE, locator.locate());

		while (handler.hasNext(DEFAULT_QUEUE)) {
			pipeline1.process(handler.nextAsset(DEFAULT_QUEUE));
		}
		
		while (handler.hasNext(SECOND_QUEUE)) {
			pipeline2.process(handler.nextAsset(SECOND_QUEUE));
		}
		
		List<Asset> finalAssets = dispatcher.getDispatchedAssets();
		assertEquals("Incorrect number of assets dispatched", 10, finalAssets.size());
		assertEquals("First asset not text/plain", "text/plain", finalAssets.get(0).getContentType());
		assertEquals("First asset is not upper-case, transformer did not work", "CONTENT FROM URL HTTP://WWW.ODOKO.ORG/ARTICLE-0", finalAssets.get(0).getValue());
	}
}
