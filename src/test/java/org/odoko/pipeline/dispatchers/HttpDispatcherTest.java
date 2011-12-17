package org.odoko.pipeline.dispatchers;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.odoko.pipeline.collectors.MockCollector;
import org.odoko.pipeline.locators.Locator;
import org.odoko.pipeline.locators.MockLocator;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractComponentTest;
import org.odoko.pipeline.pipelines.Pipeline;
import org.odoko.pipeline.pipelines.PipelineException;

public class HttpDispatcherTest extends AbstractComponentTest {

	@Test
	public void testHttpDispatch() throws Exception {
		
		Pipeline pipeline = new Pipeline();
		MockCollector collector = new MockCollector();		
		pipeline.addComponent(collector);
		HttpDispatcher dispatcher = new HttpDispatcher();
		dispatcher.setUrl("http://app.odoko.org");
		HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
		Mockito.when(mockHttpClient.execute(Mockito.any(HttpPost.class), Mockito.any(ResponseHandler.class)))
				.thenReturn("<success/>");
		dispatcher.setHttpClient(mockHttpClient);
		pipeline.addComponent(dispatcher);
		pipeline.wire();
		Locator locator = new MockLocator();
		for (Asset asset : locator.locate()) {
			asset.setProperty("url", "http://app.odoko.co.uk");
			pipeline.process(asset);
		}
	}

	@Test
	public void testFailedHttpDispatch() throws Exception {
		
		Pipeline pipeline = new Pipeline();
		MockCollector collector = new MockCollector();		
		pipeline.addComponent(collector);
		HttpDispatcher dispatcher = new HttpDispatcher();
		dispatcher.setUrl("http://app.odoko.org");
		HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
		Mockito.when(mockHttpClient.execute(Mockito.any(HttpPost.class), Mockito.any(ResponseHandler.class)))
				.thenThrow(new HttpResponseException(500, "Internal Server Error"));
		dispatcher.setHttpClient(mockHttpClient);
		pipeline.addComponent(dispatcher);
		pipeline.wire();
		Locator locator = new MockLocator();
		for (Asset asset : locator.locate()) {
			asset.setProperty("url", "http://app.odoko.co.uk");
			try {
				pipeline.process(asset);
			} catch (PipelineException e) {
				assertEquals("Internal Server Error", e.getMessage());
				return;
			}
		}
		//should never get here:
		assertEquals("Http client did not fail and it should have", 1, 0);
	}

}
