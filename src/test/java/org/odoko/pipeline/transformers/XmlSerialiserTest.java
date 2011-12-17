package org.odoko.pipeline.transformers;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractComponentTest;
import org.odoko.pipeline.transformers.Transformer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XmlSerialiserTest extends AbstractComponentTest {

	private String XML = "<xml><a foo=\"bar\"/></xml>";
	
	@Test
	public void testXmlParser() throws Exception {
		Transformer serialiser = new XmlSerialiser();
		
		MockConsumer consumer = new MockConsumer();
		serialiser.wire(consumer);
		serialiser.initialise(null);
		Asset assetIn = new Asset();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(XML));
		Document doc = builder.parse(is);
		assetIn.setValue(doc);
		
		serialiser.consumeBase(assetIn);
		
		Asset assetOut = consumer.getAsset();
		String xmlOut = (String)assetOut.getValue();
		assertEquals(XML, xmlOut);
	}
}
