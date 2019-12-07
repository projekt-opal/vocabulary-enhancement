package org.dice_research.opal.vocabulary_enhancement;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.DCAT;
import org.dice_research.opal.common.utilities.FileHandler;

/**
 * Imports RDF resources.
 *
 * @author Adrian Wilke
 */
public class RdfImporter {

	/**
	 * Extracts and prints URIs with {@link DCAT} prefixes from
	 * {@link Resources#DCAT_2_RDF}.
	 */
	public static void main(String[] args) throws URISyntaxException {
		RdfImporter dcat2Rdf = new RdfImporter().loadTurtle(Resources.DCAT_2_RDF);
		SortedSet<String> dcatClasses = dcat2Rdf.filterUrisByPrefixAndCase(DCAT.NS, true);
		SortedSet<String> dcatProperties = dcat2Rdf.filterUrisByPrefixAndCase(DCAT.NS, false);

		System.out.println("URIs with DCAT prefix, upper-case:");
		dcatClasses.forEach(System.out::println);
		System.out.println(dcatClasses.size());
		System.out.println();

		System.out.println("URIs with DCAT prefix, lower-case:");
		dcatProperties.forEach(System.out::println);
		System.out.println(dcatProperties.size());
	}

	private Model model;

	public RdfImporter loadTurtle(String resourceName) throws URISyntaxException {
		loadTurtle(RdfImporter.class.getClassLoader().getResource(resourceName).toURI());
		return this;
	}

	public RdfImporter loadTurtle(URI uri) {
		model = FileHandler.importModel(uri.toString());
		return this;
	}

	public SortedSet<String> filterUrisByPrefixAndCase(String prefix, boolean uppercase) {
		SortedSet<String> set = new TreeSet<>();
		for (String uri : filterUrisByPrefix(prefix)) {
			if (uri.length() >= prefix.length()) {
				char character = uri.charAt(prefix.length());
				if (uppercase && Character.isUpperCase(character)) {
					set.add(uri);
				} else if (!uppercase && !Character.isUpperCase(character)) {
					set.add(uri);
				}
			}
		}
		return set;
	}

	public SortedSet<String> filterUrisByPrefix(String prefix) {
		SortedSet<String> set = new TreeSet<>();
		for (String uri : extractUris()) {
			if (uri.startsWith(prefix)) {
				set.add(uri);
			}
		}
		return set;
	}

	public SortedSet<String> extractUris() {
		SortedSet<String> set = new TreeSet<>();
		StmtIterator stmtIterator = model.listStatements();
		while (stmtIterator.hasNext()) {
			set.addAll(extractUris(stmtIterator.next()));
		}
		return set;
	}

	public SortedSet<String> extractUris(Statement statement) {
		SortedSet<String> set = new TreeSet<>();
		if (statement.getSubject().isURIResource()) {
			set.add(statement.getSubject().getURI());
		}
		if (statement.getPredicate().isURIResource()) {
			set.add(statement.getPredicate().getURI());
		}
		if (statement.getObject().isURIResource()) {
			set.add(statement.getObject().asResource().getURI());
		}
		return set;
	}
}