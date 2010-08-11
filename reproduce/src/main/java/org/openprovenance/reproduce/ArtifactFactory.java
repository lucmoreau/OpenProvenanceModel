package org.openprovenance.reproduce;
import java.util.HashMap;
import org.openprovenance.model.Artifact;

public interface ArtifactFactory {
    Artifact newArtifact(Artifact o);
    HashMap<String,String> getArtifactMap();
}