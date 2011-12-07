package org.odoko.pipeline.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.yaml.snakeyaml.Yaml;

public class YamlConfiguration extends AbstractConfiguration {

	@Override
	public void parse(String filename) throws IOException {
		Yaml yaml = new Yaml();
		yaml.parse(new InputStreamReader(new FileInputStream(filename)));
	}
}
