package org.dice_research.opal.vocabulary_enhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.jena.vocabulary.DCAT;

/**
 * Vocabulary Comprison of:
 * 
 * - Data Catalog Vocabulary (DCAT)
 * 
 * - DCAT Version 2 Implementation Report - Summary of proposed revisions
 * 
 * - org.apache.jena.vocabulary.DCAT, GitHub commit Jul 14, 2016
 * 
 * The used data data are stated in the comments in the resource files.
 *
 * @author Adrian Wilke
 */
public class Main {

//	private final static String DCAT_PREFIX = "http://www.w3.org/ns/dcat";

	private RdfImporter dcat2Rdf;

	public static void main(String[] args) throws Exception {

		Main main = new Main();

		// Import

		List<String> dcat1 = extractElement(TableImporter.splitLines(Resources.DCAT_1, 0, 0), 0);
		Collections.sort(dcat1);
		List<String> clsDcat1 = filterByCase(dcat1, true);
		List<String> propDcat1 = filterByCase(dcat1, false);

		List<String> clsDcat2 = removeDuplicates(extractElement(TableImporter.splitLines(Resources.DCAT_2, 1, 1), 0));
		List<String> propDcat2 = removeDuplicates(extractElement(TableImporter.splitLines(Resources.DCAT_2, 2, 2), 0));

		List<String> propFuseki = extractFusekiDcatProperties(
				extractElement(TableImporter.splitLines(Resources.FUSEKI_DCAT_PROPERTIES, 8, 8), 0));
		List<String> clsFuseki = extractFusekiDcatProperties(
				extractElement(TableImporter.splitLines(Resources.FUSEKI_DCAT_CLASSES, 8, 8), 0));

		// RDF import

		List<String> clsDcat2Rdf = Main.replacePref(main.getDcat2ClassesFromRdf(), DCAT.NS, "dcat:");
		List<String> propDcat2Rdf = Main.replacePref(main.getDcat2PropertiesFromRdf(), DCAT.NS, "dcat:");

		if (Boolean.TRUE) {
			System.out.println("Imported:");
			System.out.println("Classes in DCAT-1:      " + clsDcat1);
			System.out.println("Classes in DCAT-2-r:    " + clsDcat2);
			System.out.println("Classes in Fuseki-DCAT: " + clsFuseki);
			System.out.println("Properties in DCAT-1:      " + propDcat1);
			System.out.println("Properties in DCAT-2-r:    " + propDcat2);
			System.out.println("Properties in Fuseki-DCAT: " + propFuseki);
			System.out.println();
		}

		if (Boolean.TRUE) {
			System.out.println("Prefixes:");
			System.out.println("Prefixes in DCAT-1:   " + getPrefixes(dcat1));
			System.out.println("Prefixes in DCAT-2-r: " + getPrefixes(propDcat2));
			System.out.println();
		}

		// Comparison DCAT
		List<String> cls_Dcat2notDcat1 = new LinkedList<>(clsDcat2);
		cls_Dcat2notDcat1.removeAll(clsDcat1);
		List<String> prop_Dcat2notDcat1 = new LinkedList<>(propDcat2);
		prop_Dcat2notDcat1.removeAll(propDcat1);

		if (Boolean.TRUE) {
			System.out.println("New in DCAT-2-r: ");
			System.out.println("Classes:    " + cls_Dcat2notDcat1);
			System.out.println("Properties: " + prop_Dcat2notDcat1);
			System.out.println();
		}

		// Filter properties
		List<String> propDcat1PreDcat = removeDuplicates(filterByPrefix(propDcat1, "dcat:"));
		List<String> propDcat2PreDcat = removeDuplicates(filterByPrefix(propDcat2, "dcat:"));

		if (Boolean.TRUE) {
			System.out.println("Filtered dcat: prefix on properties: ");
			System.out.println("DCAT-1:   " + propDcat1PreDcat);
			System.out.println("DCAT-2-r: " + propDcat2PreDcat);
			System.out.println();
		}

		// Comparison properties
		List<String> propDcat_Dcat1notDcat2 = new LinkedList<>(propDcat1PreDcat);
		propDcat_Dcat1notDcat2.removeAll(propDcat2PreDcat);

		List<String> propDcat_Dcat2notDcat1 = new LinkedList<>(propDcat2PreDcat);
		propDcat_Dcat2notDcat1.removeAll(propDcat1PreDcat);

		List<String> propDcat_Dcat1tnotFuseki = new LinkedList<>(propDcat1PreDcat);
		propDcat_Dcat1tnotFuseki.removeAll(propFuseki);

		List<String> propDcat_Dcat2tnotFuseki = new LinkedList<>(propDcat2PreDcat);
		propDcat_Dcat2tnotFuseki.removeAll(propFuseki);

		if (Boolean.TRUE) {
			System.out.println("Comparison dcat: prefix on properties: ");
			System.out.println("DCAT-1   not DCAT-2-r:    " + propDcat_Dcat1notDcat2);
			System.out.println("DCAT-2-r not DCAT-1:      " + propDcat_Dcat2notDcat1);
			System.out.println("DCAT-1   not Fuseki-DCAT: " + propDcat_Dcat1tnotFuseki);
			System.out.println("DCAT-2-r not Fuseki-DCAT: " + propDcat_Dcat2tnotFuseki);
			System.out.println();
		}

		// Filter classes
		List<String> clsDcat1PreDcat = removeDuplicates(filterByPrefix(clsDcat1, "dcat:"));
		List<String> clsDcat2PreDcat = removeDuplicates(filterByPrefix(clsDcat2, "dcat:"));

		if (Boolean.TRUE) {
			System.out.println("Filtered dcat: prefix on classes: ");
			System.out.println("DCAT-1:   " + clsDcat1PreDcat);
			System.out.println("DCAT-2-r: " + clsDcat2PreDcat);
			System.out.println();
		}

		// Comparison classes
		List<String> clsDcat_Dcat1notDcat2 = new LinkedList<>(clsDcat1PreDcat);
		clsDcat_Dcat1notDcat2.removeAll(clsDcat2PreDcat);

		List<String> clsDcat_Dcat2notDcat1 = new LinkedList<>(clsDcat2PreDcat);
		clsDcat_Dcat2notDcat1.removeAll(clsDcat1PreDcat);

		List<String> clsDcat_Dcat1tnotFuseki = new LinkedList<>(clsDcat1PreDcat);
		clsDcat_Dcat1tnotFuseki.removeAll(clsFuseki);

		List<String> clsDcat_Dcat2tnotFuseki = new LinkedList<>(clsDcat2PreDcat);
		clsDcat_Dcat2tnotFuseki.removeAll(clsFuseki);

		if (Boolean.TRUE) {
			System.out.println("Comparison dcat: prefix on classes: ");
			System.out.println("DCAT-1   not DCAT-2-r:    " + clsDcat_Dcat1notDcat2);
			System.out.println("DCAT-2-r not DCAT-1:      " + clsDcat_Dcat2notDcat1);
			System.out.println("DCAT-1   not Fuseki-DCAT: " + clsDcat_Dcat1tnotFuseki);
			System.out.println("DCAT-2-r not Fuseki-DCAT: " + clsDcat_Dcat2tnotFuseki);
			System.out.println();
		}

		// Double check
		List<String> addCls = clsDcat2Rdf;
		addCls.removeAll(clsDcat_Dcat2notDcat1);
		addCls.removeAll(clsFuseki);
		List<String> addProp = propDcat2Rdf;
		addProp.removeAll(propDcat_Dcat2notDcat1);
		addProp.removeAll(propFuseki);
		List<String> checkCls = clsFuseki;
		checkCls.removeAll(clsFuseki);
		List<String> checkProp = propFuseki;
		checkProp.removeAll(propFuseki);
		if (Boolean.TRUE) {
			System.out.println("Double check");
			addCls.forEach(System.out::println);
			addProp.forEach(System.out::println);
			checkCls.forEach(System.out::println);
			checkProp.forEach(System.out::println);
			System.out.println();
		}
		// Correction
		propDcat_Dcat2tnotFuseki.addAll(addProp);
		clsDcat_Dcat2tnotFuseki.addAll(addCls);

		// Code
		System.out.println("Fuseki code:");
		Collections.sort(propDcat_Dcat2tnotFuseki);
		for (String string : propDcat_Dcat2tnotFuseki) {
			fusekiCode(string, true);
		}
		System.out.println();
		Collections.sort(clsDcat_Dcat2tnotFuseki);
		for (String string : clsDcat_Dcat2tnotFuseki) {
			fusekiCode(string, false);
		}
		System.out.println();
		for (String string : addProp) {
			fusekiCode(string, true);
		}

	}

	protected static void fusekiCode(String label, boolean isProperty) {
		int index = label.indexOf(":");
		if (index != -1) {
			label = label.substring(index + 1);
		}
		String codeClass = "\tpublic static final Resource " + label + " = m.createResource(NS + \"" + label + "\");";
		String codeProperty = "\tpublic static final Property " + label + " = m.createProperty(NS + \"" + label
				+ "\");";
		if (isProperty) {
			System.out.println(codeProperty);
		} else {
			System.out.println(codeClass);
		}
	}

	protected static List<String> replacePref(Collection<String> collection, String prefix, String replacement) {
		List<String> list = new ArrayList<>();
		for (String string : collection) {
			list.add(string.replaceFirst(prefix, replacement));
		}
		return list;
	}

	protected static List<String> filterByPrefix(List<String> list, String prefix) {
		List<String> resultList = new ArrayList<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).startsWith(prefix)) {
				resultList.add(list.get(i));
			}
		}
		return resultList;
	}

	protected static Set<String> getPrefixes(List<String> list) {
		Set<String> set = new HashSet<>();
		for (String string : list) {
			int index = string.indexOf(":");
			if (index != -1) {
				set.add(string.substring(0, index));
			}
		}
		return set;
	}

	protected static List<String> filterByCase(List<String> list, boolean uppercase) {
		List<String> results = new LinkedList<>();
		for (String string : list) {
			int index = string.indexOf(":");
			if (index == -1) {
				throw new RuntimeException(string);
			} else {
				if (Character.isUpperCase(string.charAt(index + 1))) {
					if (uppercase) {
						results.add(string);
					}
				} else if (!uppercase) {
					results.add(string);
				}
			}
		}
		return results;
	}

	protected static List<String> extractFusekiDcatProperties(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, "dcat:" + list.get(i).substring(list.get(i).indexOf("\"") + 1, list.get(i).lastIndexOf("\"")));
		}
		return list;
	}

	protected static List<String> extractElement(List<List<String>> embeddedList, int index) {
		List<String> list = new ArrayList<>(embeddedList.size());
		for (List<String> entry : embeddedList) {
			if (!entry.isEmpty() && !entry.get(0).isEmpty()) {
				list.add(entry.get(0));
			}
		}
		return list;
	}

	protected static List<String> removeDuplicates(List<String> list) {
		return new ArrayList<String>(new TreeSet<String>(list));
	}

	public SortedSet<String> getDcat2ClassesFromRdf() {
		if (dcat2Rdf == null) {
			dcat2Rdf = new RdfImporter().loadTurtle(Resources.DCAT_2_RDF);
		}
		return dcat2Rdf.filterUrisByPrefixAndCase(DCAT.NS, true);
	}

	public SortedSet<String> getDcat2PropertiesFromRdf() {
		if (dcat2Rdf == null) {
			dcat2Rdf = new RdfImporter().loadTurtle(Resources.DCAT_2_RDF);
		}
		return dcat2Rdf.filterUrisByPrefixAndCase(DCAT.NS, false);
	}
}