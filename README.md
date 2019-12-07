# Jena DCAT vocabulary enhancement

The **Data Catalog Vocabulary (DCAT)**
[Version 1](https://www.w3.org/TR/2014/REC-vocab-dcat-20140116/)
has been extended to
[Version 2](https://www.w3.org/TR/2019/PR-vocab-dcat-2-20191119/)
on 19 November 2019 as W3C Proposed Recommendation.

In Apache Jena, there is
[org.apache.jena.vocabulary.DCAT](https://github.com/apache/jena/blob/d11b5c8ea739068abd1ab53fc1360e536d6ea471/jena-core/src/main/java/org/apache/jena/vocabulary/DCAT.java)
containing Resources (classes) and Properties of version 1.

With the code of this repository it has been checked, which identifiers are currently missing.

### Classes in DCAT 1

dcat:Catalog, dcat:CatalogRecord, dcat:Dataset, dcat:Distribution

### Properties in DCAT 1

dcat:accessURL, dcat:byteSize, dcat:contactPoint, dcat:dataset, dcat:distribution, dcat:downloadURL, dcat:keyword, dcat:landingPage, dcat:mediaType, dcat:record, dcat:theme, dcat:themeTaxonomy, dct:accrualPeriodicity, dct:description, dct:description, dct:description, dct:description, dct:format, dct:identifier, dct:issued, dct:issued, dct:issued, dct:issued, dct:language, dct:language, dct:license, dct:license, dct:modified, dct:modified, dct:modified, dct:modified, dct:publisher, dct:publisher, dct:rights, dct:rights, dct:spatial, dct:spatial, dct:temporal, dct:title, dct:title, dct:title, dct:title, foaf:homepage, foaf:primaryTopic

### Additional classes in DCAT 2 revisions

dcat:DataService, dcat:Relationship, dcat:Resource, dcat:Role, dct:Location, dct:PeriodOfTime

### Additional properties in DCAT 2 revisions

dcat:accessService, dcat:bbox, dcat:catalog, dcat:centroid, dcat:compressFormat, dcat:endDate, dcat:endpointDescription, dcat:endpointURL, dcat:hadRole, dcat:packageFormat, dcat:qualifiedRelation, dcat:servesDataset, dcat:service, dcat:spatialResolutionInMeters, dcat:startDate, dcat:temporalResolution, dct:accessRights, dct:conformsTo, dct:creator, dct:hasPart, dct:isReferencedBy, dct:relation, dct:type, locn:geometry, odrl:hasPolicy, prov:qualifiedAttribution, prov:wasGeneratedBy, time:hasBeginning, time:hasEnd

### Additional classes in namespace dcat (To be added to Apache Jena)

dcat:DataService, dcat:Relationship, dcat:Resource, dcat:Role

### Additional properties in namespace dcat (To be added to Apache Jena)

dcat:accessService, dcat:bbox, dcat:catalog, dcat:centroid, dcat:compressFormat, dcat:endDate, dcat:endpointDescription, dcat:endpointURL, dcat:hadRole, dcat:packageFormat, dcat:qualifiedRelation, dcat:servesDataset, dcat:service, dcat:spatialResolutionInMeters, dcat:startDate, dcat:temporalResolution

## Credits

[Data Science Group (DICE)](https://dice-research.org/) at [Paderborn University](https://www.uni-paderborn.de/)

This work has been supported by the German Federal Ministry of Transport and Digital Infrastructure (BMVI) in the project [Open Data Portal Germany (OPAL)](http://projekt-opal.de/) (funding code 19F2028A).
