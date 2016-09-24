/**
 * 
 */
package com.epam.snake;

import java.util.HashMap;
import java.util.Map;

/**
 * This class parses the arguments from the main method
 * 
 * @author Savva_Kodeikin
 *
 */
public class ArgumentParser {

	/**
	 * Parsing the array of arguments. When this method find an argument from
	 * needArgs array, it takes the next element from the args array and puts
	 * this pair into the result map. If an argument name is "-h" or "--help",
	 * it returns "null", so you should catch it and show the help text to the
	 * user.
	 * 
	 * @param args arguments from the user
	 * @param needArgs arguments to parse (for example new String[] { "-s",
	 *            "--time" })
	 * @return the map, which contains required arguments as the keys and their
	 *         values
	 */
	public static Map<String, String> parseArguments(String[] args,
			String[] needArgs) {
		Map<String, String> result = new HashMap<String, String>();

		for (int i = 0; i < args.length; i++) {

			for (int j = 0; j < needArgs.length; j++) {
				if (args[i].equals(needArgs[j])) {
					String value = (args.length > i + 1) ? args[i + 1] : null;
					result.put(needArgs[j], value);
				}
			}

		}

		return result;
	}

}
