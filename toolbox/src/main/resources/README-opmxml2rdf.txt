
opmxml2rdf
--------

An executable to convert OPM graphs represented in XML into OPM graphs
represented as XML/RDF. The program loads the graph in memory and
generates its RDF representation using Tupelo.

 
USAGE:

  opmxml2rdf fileIn fileOut


The arguments are the following:

 fileIn: the name of a file containing an XML representation (version
          ${project.version}) of an OPM graph

 fileOut: the name of a file in which to write the XML/RDF representation
          on an opm graph


----------------------------------------------------------------------


EXAMPLE

 bin/opmxml2rdf examples/bad-cake.xml examples/bad-cake2.rdf


