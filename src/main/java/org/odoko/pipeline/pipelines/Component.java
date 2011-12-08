package org.odoko.pipeline.pipelines;

public interface Component {
	public void setProperty(String name, String value);
	public Object getProperty(String string);
}
