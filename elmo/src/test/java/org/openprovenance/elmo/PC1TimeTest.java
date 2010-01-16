package org.openprovenance.elmo;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.openprovenance.model.OPMGraph;

import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;

import org.openrdf.rio.RDFFormat;
import javax.xml.bind.JAXBException;

public class PC1TimeTest extends org.openprovenance.model.PC1TimeTest {

    static String TEST_NS="http://example.com/";

    static ElmoManager        manager=null;
    static ElmoManagerFactory factory=null;
    static RepositoryHelper   rHelper=null;
    static ElmoModule         module =null;

    static boolean initialized=false;

    static void initializeElmo() {
        module = new ElmoModule();
        rHelper=new RepositoryHelper();
        rHelper.registerConcepts(module);
        factory = new SesameManagerFactory(module);
        manager = factory.createElmoManager();
        oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

    }

    public PC1TimeTest( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;

    public void testPC1TimeSaveToN3() throws Exception {
        // Note, I am reconstructing the graph, to be sure that its
        // facade conrresponds to the current manager (the maven
        // tester plugin seem to create a new instance of this tester
        // object for each test)
        super.testPC1Time();

        //graph1=makePC1TimeGraph(oFactory);
        
        File file = new File("target/pc1-time.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


}