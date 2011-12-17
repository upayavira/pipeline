package org.odoko.pipeline.parsers;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.ComponentTest;
import org.odoko.pipeline.transformers.Transformer;
import org.w3c.dom.Document;

public class XmlParserTest extends ComponentTest {

	private String XML = "<xml><a foo='bar'/></xml>";
	
	@Test
	public void testXmlParser() throws Exception {
		Transformer parser = new XmlParser();
		
		MockConsumer consumer = new MockConsumer();
		parser.wire(consumer);
		parser.initialise(null);
		Asset assetIn = new Asset();
		assetIn.setValue(XML);
		
		parser.consumeBase(assetIn);
		
		Asset assetOut = consumer.getAsset();
		Document xmlOut = (Document)assetOut.getValue();
		assertEquals("xml", xmlOut.getDocumentElement().getTagName());
	}
}
