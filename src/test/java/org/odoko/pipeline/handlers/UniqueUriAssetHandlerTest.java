package org.odoko.pipeline.handlers;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.odoko.pipeline.handlers.AssetHandler;
import org.odoko.pipeline.handlers.UniqueUriAssetHandler;
import org.odoko.pipeline.model.Asset;

public class UniqueUriAssetHandlerTest {

	private static final String DB_FILE = "target/database.tmp";
	@Test
	public void testUniqueAssetsNotRepeated() throws IOException {
		File db = new File(DB_FILE);
		if (db.exists()) {
			db.delete();
		}
		AssetHandler handler = new UniqueUriAssetHandler(DB_FILE);
		Asset asset1 = new Asset();
		Asset asset2 = new Asset();
		asset1.setValue("ASSET1");
		asset2.setValue("ASSET2");
		asset1.setUri("http://app.odoko.co.uk/unique");
		asset2.setUri("http://app.odoko.co.uk/unique");
		handler.addAsset(asset1);
		handler.addAsset(asset2);
		
		int i=0;
		while (handler.hasNext()) {
			i++;
			handler.nextAsset();
		}
		assertEquals("Handler should only have one asset, as the two URIs are the same", 1, i);
	}
}
