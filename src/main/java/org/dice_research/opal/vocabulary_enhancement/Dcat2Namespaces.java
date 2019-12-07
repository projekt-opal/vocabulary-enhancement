package org.dice_research.opal.vocabulary_enhancement;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCAT;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;

/**
 * Gets all URIs used in DCAT 2 and extracts namespaces defined in Jena.
 *
 * @author Adrian Wilke
 */
public class Dcat2Namespaces {

	protected RdfImporter dcat2Rdf;
	protected SortedSet<String> urisAll;
	protected SortedSet<String> urisUnknown;

	Map<String, SortedSet<String>> namespaces = new HashMap<>();

	public static void main(String[] args) {
		new Dcat2Namespaces()

				.initialize()

				.sortNamespaces()

				.check()

				.print();
	}

	protected Dcat2Namespaces initialize() {
		if (dcat2Rdf == null) {
			dcat2Rdf = new RdfImporter().loadTurtle(Resources.DCAT_2_RDF);
			urisAll = dcat2Rdf.extractUris();
		}
		urisUnknown = new TreeSet<>(urisAll);
		return this;
	}

	protected Dcat2Namespaces sortNamespaces() {
		initialize();
		for (String uri : urisAll) {
			sortUri(uri, DCAT.NS);
			sortUri(uri, OWL.NS);
			sortUri(uri, DCTerms.NS);
			sortUri(uri, RDF.getURI());
			sortUri(uri, RDFS.getURI());
			sortUri(uri, SKOS.getURI());
			sortUri(uri, FOAF.getURI());
		}
		return this;
	}

	protected Dcat2Namespaces sortUri(String uri, String prefix) {
		if (!namespaces.containsKey(prefix)) {
			namespaces.put(prefix, new TreeSet<>());
		}
		if (uri.startsWith(prefix)) {
			namespaces.get(prefix).add(uri);
			urisUnknown.remove(uri);
		}
		return this;
	}

	protected Dcat2Namespaces check() {
		int counter = urisUnknown.size();
		for (SortedSet<String> set : namespaces.values()) {
			counter += set.size();
		}
		if (counter != urisAll.size()) {
			throw new RuntimeException(urisAll.size() + " " + counter + Dcat2Namespaces.class.getName());
		}
		return this;
	}

	protected Dcat2Namespaces print() {
		System.out.println(urisAll.size());
		System.out.println();

		for (Entry<String, SortedSet<String>> namespace : namespaces.entrySet()) {
			namespace.getValue().forEach(System.out::println);
			System.out.println(namespace.getValue().size());
			System.out.println();
		}

		urisUnknown.forEach(System.out::println);
		System.out.println(urisUnknown.size());
		System.out.println();
		return this;
	}

}