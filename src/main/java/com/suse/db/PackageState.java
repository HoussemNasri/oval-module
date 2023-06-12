package com.suse.db;

import com.suse.ovaltypes.LogicOperatorType;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "package_state")
public class PackageState {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private LogicOperatorType operator;
    private String comment;
    @OneToOne
    @JoinColumn(name = "arch_state")
    private PackageArchStateEntity packageArchState;
    @OneToOne
    @JoinColumn(name = "version_state")
    private PackageVersionStateEntity packageVersionState;
    @OneToOne
    @JoinColumn(name = "evr_state")
    private PackageEvrStateEntity packageEvrState;
    private boolean isRpm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LogicOperatorType getOperator() {
        return operator;
    }

    public void setOperator(LogicOperatorType operator) {
        this.operator = operator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Optional<PackageArchStateEntity> getPackageArchState() {
        return Optional.ofNullable(packageArchState);
    }

    public void setPackageArchState(PackageArchStateEntity packageArchState) {
        this.packageArchState = packageArchState;
    }

    public Optional<PackageVersionStateEntity> getPackageVersionState() {
        return Optional.ofNullable(packageVersionState);
    }

    public void setPackageVersionState(PackageVersionStateEntity packageVersionState) {
        this.packageVersionState = packageVersionState;
    }

    public Optional<PackageEvrStateEntity> getPackageEvrState() {
        return Optional.ofNullable(packageEvrState);
    }

    public void setPackageEvrState(PackageEvrStateEntity packageEvrState) {
        this.packageEvrState = packageEvrState;
    }

    public boolean isRpm() {
        return isRpm;
    }

    public void setRpm(boolean rpm) {
        isRpm = rpm;
    }
}
