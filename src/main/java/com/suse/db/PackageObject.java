package com.suse.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "package_object")
public class PackageObject {
    @Id
    private String id;
    private String packageName;
    private String comment;
    private boolean isRpm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRpm() {
        return isRpm;
    }

    public void setRpm(boolean rpm) {
        isRpm = rpm;
    }
}
