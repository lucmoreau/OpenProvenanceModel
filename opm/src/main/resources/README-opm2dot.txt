
opm2dot
--------

An executable to convert OPM graphs into dot files.

 
USAGE:
  opm2dot opmFile.xml out.dot out.pdf [configuration.xml]

The arguments are the following:

 opmFile.xml: an XML representation (version ${project.version}) of an
              OPM graph

 out.dot: the name of the dot file to be produced by this utility

 out.pdf: the name of the pdf file to be produced by this utility
          (Note, to successfully be produced, the dot executable should
           be in the class path)

 configuration.xml: the optional configuration file to parameterize
                    the conversion process

----------------------------------------------------------------------


EXAMPLES

 bin/opm2dot examples/bad-cake.xml bad-cake.dot bad-cake.pdf
 bin/opm2dot examples/bad-cake.xml bad-cake-red.dot bad-cake-red.pdf examples/redConfig.xml
 bin/opm2dot examples/bad-cake.xml bad-cake-black.dot bad-cake-black.pdf examples/blackConfig.xml



----------------------------------------------------------------------

Third party software licenses:

APACHE LICENSE V2
- commons-jxpath/commons-jxpath/1.2/commons-jxpath-1.2.jar
- commons-beanutils/commons-beanutils/1.4/commons-beanutils-1.4.jar
- commons-lang/commons-lang/2.2/commons-lang-2.2.jar
- commons-logging/commons-logging/1.0/commons-logging-1.0.jar
- commons-collections/commons-collections/2.0/commons-collections-2.0.jar

JDOM LICENSE
- jdom/jdom/b9/jdom-b9.jar

COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0

- xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar
- javax/xml/stream/stax-api/1.0-2/stax-api-1.0-2.jar
- javax/xml/bind/jaxb-api/2.1/jaxb-api-2.1.jar
- com/sun/xml/bind/jaxb-impl/2.1.3/jaxb-impl-2.1.3.jar

DUAL LICENSE CDDL V1.0 AND GPL V2.
- org/jvnet/jaxb2_commons/runtime/0.2.RC1/runtime-0.2.RC1.jar

ACTIVATION LICENSE
- javax/activation/activation/1.1/activation-1.1.jar
   
   




