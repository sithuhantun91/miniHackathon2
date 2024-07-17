package com.sithuhantun.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Department implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deptId;
    private String deptName;
//        @OneToMany(targetEntity= Teacher.class, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Teacher> teacherList = new ArrayList<>();

    public Department() {
    }

    public Department(String deptName) {
        this.deptName = deptName;
    }

    public Department(int deptId, String deptName) {
        super();
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        String result = "Department{"
                + "deptId=" + deptId
                + ", deptName='"
                + deptName + '\''
                + ", teacherList= ";
        for (Teacher teacher : teacherList) {
            result += teacher.getTeacherName() + ", ";
        }
        result += '}';
        return result;
    }
}
