package org.odoko.pipeline.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableResolver {

	private static Pattern VARIABLE_PATTERN = Pattern.compile(".*?\\$\\{([^\\}]*)\\}.*", Pattern.DOTALL);
	public static String resolve(Configuration config, String property) {
		Matcher matcher = VARIABLE_PATTERN.matcher(property);
		while (matcher.matches()) {
			String variableName = matcher.group(1);
			String variableValue = config.getProperty(variableName);
			if (variableValue == null) {
				variableValue = "";
			}
			property = property.replace("${" + matcher.group(1) + "}", variableValue);
			matcher = VARIABLE_PATTERN.matcher(property);
		}
		return property;
	}
}
