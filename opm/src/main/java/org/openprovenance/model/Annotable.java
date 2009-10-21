package org.openprovenance.model;
import java.util.List;
import javax.xml.bind.JAXBElement;

public interface Annotable extends Identifiable {
    public List<JAXBElement<? extends EmbeddedAnnotation>>  getAnnotation();
} 