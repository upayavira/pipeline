package org.odoko.pipeline.collectors;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.odoko.pipeline.dispatchers.MockDispatcher;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.locators.MockLocator;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractComponentTest;
import org.odoko.pipeline.pipelines.Pipeline;

public class HttpCollectorTest extends AbstractComponentTest {

	@Test
	public void testHttpCollection() throws Exception {
		
		Pipeline pipeline = new Pipeline();
		HttpCollector collector = new HttpCollector();		
		HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
		Mockito.when(mockHttpClient.execute(Mockito.any(HttpGet.class), Mockito.any(ResponseHandler.class)))
				.thenReturn("<ABC/>");
		collector.setHttpClient(mockHttpClient);
		pipeline.addComponent(collector);
		MockDispatcher dispatcher = new MockDispatcher();
		pipeline.addComponent(dispatcher);
		pipeline.wire();
		Locator locator = new MockLocator();
		for (Asset asset : locator.locate()) {
			asset.setProperty("url", "http://app.odoko.co.uk");
			pipeline.process(asset);
		}
		List<Asset> assets = dispatcher.getDispatchedAssets();
		assertEquals(10, assets.size());
		assertEquals("<ABC/>", assets.get(0).getValue());
	}
}
