package org.odoko.pipeline.transformers;

import org.odoko.pipeline.config.ConfigurationException;
import org.odoko.pipeline.model.Asset;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class XmlSerialiser extends AbstractTransformer {

	LSSerializer writer;
	
	public XmlSerialiser() throws ConfigurationException {
		try {
			setIncomingClass(Document.class);
			setOutgoingClass(String.class);
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
			writer = impl.createLSSerializer();
			DOMConfiguration config = writer.getDomConfig();
			config.setParameter("xml-declaration", false);
		} catch (ClassCastException e) {
			throw new ConfigurationException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(e.getMessage());
		} catch (InstantiationException e) {
			throw new ConfigurationException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	@Override
	public void consume(Asset asset) {
		String xml = writer.writeToString((Document)asset.getValue());
		asset.setValue(xml);
		super.consume(asset);
	}

}
