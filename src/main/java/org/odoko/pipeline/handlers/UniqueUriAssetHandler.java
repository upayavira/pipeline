package org.odoko.pipeline.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.odoko.pipeline.model.Asset;

/**
 * Simplest asset handler tracking URIs that have been seen already.
 * 
 * This class uses a simple text file to track URIs. This will not scale, and
 * should not be used in production.
 * 
 * @author upayavira
 *
 */
public class UniqueUriAssetHandler extends SimpleAssetHandler {

	private PrintWriter writer;
	private List<String> seenUris = new ArrayList<String>();
	
	public UniqueUriAssetHandler(String databaseFile) throws IOException {
		File f = new File(databaseFile);
		if (f.exists()) {
		    BufferedReader reader = new BufferedReader(new FileReader(databaseFile));
		    String line;
		    while ((line = reader.readLine())!=null) {
		    	seenUris.add(line.trim());
		    }
		    reader.close();
		}
	    writer = new PrintWriter(new FileWriter(databaseFile, true));
	}
	
	@Override
	public void addAssets(String queue, List<Asset> assets) {
		for (Asset asset : assets) {
			addAsset(queue, asset);
		}
	}

	@Override
	public void addAsset(String queue, Asset asset) {
		String uri = asset.getUri();
		if (isUnique(queue, uri)) {
			writer.println(uri);
			seenUris.add(getSeenKey(queue, uri));
			super.addAsset(queue, asset);
		}
	}

	private boolean isUnique(String queue, String uri) {
		return !seenUris.contains(getSeenKey(queue, uri));
	}
	
	private String getSeenKey(String queue, String uri) {
		return queue + ":" + uri;
	}
}
