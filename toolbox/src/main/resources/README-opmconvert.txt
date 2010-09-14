
opmconvert
--------

An executable to:
- convert OPM graphs represented as XML into XML/RDF, 
- convert OPM graphs represented as XML into N3,
- convert OPM graphs represented as XML/RDF into XML.
The program uses Sesame's elmo to create or naviguate the RDF representation of OPM graphs.

The program assumes that all XML IDs in the XML representation can be
converted into a URI by providing a namespace NS, and vice-versa that
all URIs identifying OPM entities in the RDF representation have a
common namespace.
 
USAGE:

  opmconvert -xml2rdf fileIn fileOut NS [yes]
  opmconvert -xml2n3  fileIn fileOut NS [yes]
  opmconvert -rdf2xml fileIn fileOut NS [gid]



The arguments are the following:

 fileIn:  the name of a file from which to read the input representation
          of an OPM graph
 fileOut: the name of a file in which to write an output representation
          of an OPM graph
 NS: namespace



----------------------------------------------------------------------


EXAMPLE

 bin/opmconvert -rdf2xml examples/bad-cake.rdf examples/bad-cake2.xml


