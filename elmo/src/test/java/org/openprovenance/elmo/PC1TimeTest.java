package org.openprovenance.elmo;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;

import org.openrdf.rio.RDFFormat;
import javax.xml.bind.JAXBException;

public class PC1TimeTest extends org.openprovenance.model.PC1TimeTest {

    static String TEST_NS="http://example.com/";

    static ElmoManager manager;
    static ElmoManagerFactory factory;
    static RepositoryHelper rHelper;


    public PC1TimeTest( String testName )
    {
        super( testName );

        ElmoModule module = new ElmoModule();

        rHelper=new RepositoryHelper();
        rHelper.registerConcepts(module);
        factory = new SesameManagerFactory(module);
        manager = factory.createElmoManager();


        oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    public void testPC1Time() throws JAXBException, InterruptedException {
        super.testPC1Time();
        File file = new File("target/pc1-time.n3");
        assert manager!=null;
        try {
            rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ex",e);
        }
    }


}