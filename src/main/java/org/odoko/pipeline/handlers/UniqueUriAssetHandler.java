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
	public void addAssets(List<Asset> assets) {
		for (Asset asset : assets) {
			addAsset(asset);
		}
	}

	@Override
	public void addAsset(Asset asset) {
		String uri = asset.getUri();
		if (isUnique(uri)) {
			writer.println(uri);
			seenUris.add(uri);
			super.addAsset(asset);
		}
	}

	private boolean isUnique(String uri) {
		return !seenUris.contains(uri);
	}
}
