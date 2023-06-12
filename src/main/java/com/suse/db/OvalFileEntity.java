package com.suse.db;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "oval_file")
public class OvalFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String generatorName;
    private Instant generationTimestamp;
    private Integer schemaVersion;
    // Could be source id?
    private String source;

    public OvalFileEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public Instant getGenerationTimestamp() {
        return generationTimestamp;
    }

    public Integer getSchemaVersion() {
        return schemaVersion;
    }

    public String getSource() {
        return source;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    public void setGenerationTimestamp(Instant generationTimestamp) {
        this.generationTimestamp = generationTimestamp;
    }

    public void setSchemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
