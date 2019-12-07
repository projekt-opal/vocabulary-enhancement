package org.dice_research.opal.vocabulary_enhancement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * Imports files and resources.
 *
 * @author Adrian Wilke
 */
public abstract class TableImporter {

	public static List<List<String>> splitLines(String resourceName, int beginIndex, int endIndex) {
		try {
			return splitLines(new File(TableImporter.class.getClassLoader().getResource(resourceName).toURI()),
					beginIndex, endIndex);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<List<String>> splitLines(File file, int beginIndex, int endIndex) {
		List<List<String>> entries = new LinkedList<>();
		for (String line : importFile(file, true)) {
			entries.add(split(line, beginIndex, endIndex));
		}
		return entries;
	}

	public static List<String> split(String string, int beginIndex, int endIndex) {
		String[] chunks = string.split("\\s+");
		List<String> list = new ArrayList<>();
		for (int i = beginIndex; i <= endIndex; i++) {
			if (chunks.length > i) {
				list.add(chunks[i]);
			} else {
				list.add(new String());
			}
		}
		return list;
	}

	public static List<String> importFile(File file, boolean removeComments) {
		List<String> lines = null;
		try {
			lines = IOUtils.readLines(new FileInputStream(file), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (int i = lines.size() - 1; i >= 0; i--) {
			if (removeComments && lines.get(i).startsWith("#")) {
				lines.remove(i);
			}
		}
		return lines;
	}

	public static void printLines(Collection<String> lines) {
		lines.forEach(System.out::println);
	}

}