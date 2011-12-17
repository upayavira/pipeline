package org.odoko.pipeline.dispatchers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.model.Asset.AssetState;
import org.odoko.pipeline.pipelines.AbstractConsumer;
import org.odoko.pipeline.pipelines.PipelineException;

public class HttpDispatcher extends AbstractConsumer implements Dispatcher {

	private HttpClient client;
	private String url;
	
	public HttpDispatcher() {
		setIncomingClass(String.class);
	}	
	
	public void setHttpClient(HttpClient client) {
	    this.client = client;	
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	@Override
	public void consume(Asset asset) {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost post = new HttpPost(this.url);
		try {
			post.setEntity(new StringEntity((String)asset.getValue()));
			client.execute(post, responseHandler);
			succeed(asset);
		} catch (HttpResponseException e) {
			throw new PipelineException(asset, AssetState.FAILED, getLocation(), e.getMessage());
		} catch (IOException e) {
			throw new PipelineException(asset, AssetState.FAILED, getLocation(), e.getMessage());
		}

		// TODO Auto-generated method stub

	}

}
