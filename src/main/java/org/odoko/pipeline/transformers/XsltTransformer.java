package org.odoko.pipeline.transformers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.odoko.pipeline.config.Configuration;
import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.model.Asset;
import org.w3c.dom.Document;

public class XsltTransformer extends AbstractTransformer {

	private Transformer transformer;
	private String xslt = null;
	public void setXslt(String xslt) {
		this.xslt = xslt;
	}

	@Override
	public void initialise(Configuration config) throws ConfigurationException {
		super.initialise(config);
		setIncomingClass(Document.class);
		setOutgoingClass(Document.class);
		
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance(); 
			dFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document xslDoc = dBuilder.parse(xslt);
			DOMSource xslDomSource = new DOMSource(xslDoc);
			xslDomSource.setSystemId(xslt);
			transformer = tFactory.newTransformer(xslDomSource);
		} catch (Exception e) {
			throw new ConfigurationException(e.getMessage());
		}
	}

	@Override
	public void consume(Asset asset) {
		Document doc = (Document)asset.getValue();
		DOMSource xmlDomSource = new DOMSource(doc);
		xmlDomSource.setSystemId(getLocation());
		DOMResult domResult = new DOMResult();
		try {
			transformer.transform(xmlDomSource, domResult);
			asset.setValue(domResult.getNode());
			next(asset);
		} catch (TransformerException e) {
			fail(asset, e.getMessage());
		}
	}
}
