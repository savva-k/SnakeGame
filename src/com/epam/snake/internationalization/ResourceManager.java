package com.epam.snake.internationalization;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Resource Manager is a static class that helps to get manages application
 * language.
 * 
 * @author Savva_Kodeikin
 * @version 1.02
 */
public final class ResourceManager {

	private static final String LANGUAGE_PROPERTIES_PATH = "resources/language";

	private static final Logger logger = Logger
			.getLogger(ResourceManager.class);

	private static ResourceBundle language;

	static {
		try {
			language = ResourceBundle.getBundle(LANGUAGE_PROPERTIES_PATH);
		} catch (MissingResourceException e) {
			logger.error("Property files cannot be found!");
			System.exit(0);
		}

	}

	/**
	 * Get some string from the language property file.
	 * 
	 * @param stringName
	 * @return string from a property file
	 */
	public static String getString(String stringName) {
		return language.getString(stringName);
	}
}
