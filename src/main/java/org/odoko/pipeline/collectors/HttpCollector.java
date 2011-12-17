package org.odoko.pipeline.collectors;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.model.Asset.AssetState;
import org.odoko.pipeline.pipelines.AbstractProducer;
import org.odoko.pipeline.pipelines.PipelineException;

public class HttpCollector extends AbstractProducer implements Collector {
	
	HttpClient client;
	
	public HttpCollector() {
		setOutgoingClass(String.class);
	}	
	
	public void setHttpClient(HttpClient client) {
	    this.client = client;	
	}

	@Override
	public void process(Asset asset) {
		String url = asset.getProperty("url");
		HttpGet get = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			String body = client.execute(get, responseHandler);
			asset.setValue(body);
			next(asset);
		} catch (ClientProtocolException e) {
			throw new PipelineException(asset, AssetState.FAILED, getLocation(), e.getMessage());
		} catch (IOException e) {
			throw new PipelineException(asset, AssetState.FAILED, getLocation(), e.getMessage());
		}
	}
}
