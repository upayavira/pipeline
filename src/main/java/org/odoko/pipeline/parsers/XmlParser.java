package org.odoko.pipeline.parsers;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.model.Asset;
import org.odoko.pipeline.pipelines.AbstractTransformer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlParser extends AbstractTransformer {

	DocumentBuilder builder;
	
	@Override
	public void initialise(Configuration config) throws ConfigurationException {
		try {
			super.initialise(config);
			setIncomingClass(String.class);
			setOutgoingClass(Document.class);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ConfigurationException(e.getMessage());
		}
	}

	@Override
	public void consume(Asset asset) {
		try {
			String body = (String)asset.getValue();
			InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(body));
			Document doc = builder.parse(is);
			asset.setContentType("text/xml");
			asset.setValue(doc);
			next(asset);
		} catch (IOException e) {
			fail(asset, e.getMessage());
			return;
		} catch (SAXException e) {
			fail(asset, e.getMessage());
			return;
		}
	}

}
