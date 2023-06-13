package com.suse.db;

import com.suse.ovaltypes.CheckEnum;
import com.suse.ovaltypes.ExistenceEnum;
import com.suse.ovaltypes.LogicOperatorType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "package_test")
public class PackageTest {
    @Id
    private String id;
    private String comment;
    @Enumerated(EnumType.STRING)
    private ExistenceEnum checkExistence;
    @Enumerated(EnumType.STRING)
    @Column(name = "test_check") // Check is reserved in PostgresSQL
    private CheckEnum check;
    @Enumerated(EnumType.STRING)
    private LogicOperatorType stateOperator;
    private boolean isRpm;
    @ManyToOne(optional = false)
    private PackageObject packageObject;
    @OneToMany(mappedBy = "id")
    private List<PackageState> packageStates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ExistenceEnum getCheckExistence() {
        return checkExistence;
    }

    public void setCheckExistence(ExistenceEnum checkExistence) {
        this.checkExistence = checkExistence;
    }

    public CheckEnum getCheck() {
        return check;
    }

    public void setCheck(CheckEnum check) {
        this.check = check;
    }

    public LogicOperatorType getStateOperator() {
        return stateOperator;
    }

    public void setStateOperator(LogicOperatorType stateOperator) {
        this.stateOperator = stateOperator;
    }

    public PackageObject getPackageObject() {
        return packageObject;
    }

    public void setPackageObject(PackageObject packageObject) {
        this.packageObject = packageObject;
    }

    public List<PackageState> getPackageStates() {
        return packageStates;
    }

    public void setPackageStates(List<PackageState> packageStates) {
        this.packageStates = packageStates;
    }

    public boolean isRpm() {
        return isRpm;
    }

    public void setRpm(boolean rpm) {
        isRpm = rpm;
    }


}
