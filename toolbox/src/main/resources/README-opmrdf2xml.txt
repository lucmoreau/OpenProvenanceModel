
opmrdf2xml
--------

An executable to convert OPM graphs represented as XML/RDF into OPM graphs
represented as XML. The program loads the graph in memory using Tupelo and
generates its XML representation

 
USAGE:

  opmrdf2xml fileIn fileOut


The arguments are the following:

 fileIn:  the name of a file from which to read the XML/RDF representation
          of an opm graph
 fileOut: the name of a file in which to write an XML representation (version
          ${project.version}) of an OPM graph



----------------------------------------------------------------------


EXAMPLE

 bin/opmrdf2xml examples/bad-cake.rdf examples/bad-cake2.xml


