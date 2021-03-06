package org.odoko.pipeline.transformers;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractComponentTest;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XsltTransformerTest extends AbstractComponentTest {

	private String XML = "<xml><a foo='bar'/></xml>";
	
	@Test
	public void testXsltTransformer() throws Exception {
		XsltTransformer transformer = new XsltTransformer();
		
		transformer.setXslt("src/test/resources/config/xslt/sample1.xsl");
		MockConsumer consumer = new MockConsumer();
		transformer.wire(consumer);
		transformer.setLocation("testXsltTransformer");
		transformer.initialise(null);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(XML));
		Document doc = builder.parse(is);
		Asset assetIn = new Asset();
		assetIn.setValue(doc);
		
		transformer.consumeBase(assetIn);
		
		Asset assetOut = consumer.getAsset();
		Document xmlOut = (Document)assetOut.getValue();
		assertEquals("TRANSFORMED", xmlOut.getDocumentElement().getTagName());
	}
	

}
