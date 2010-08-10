package org.openprovenance.reproduce;

import org.openprovenance.model.Artifact;

public interface ArtifactFactory {
    Artifact newArtifact(Artifact o);
}