package com.sithuhantun.dao;

import com.sithuhantun.model.Department;
import org.hibernate.Session;

import java.util.List;

public interface DepartmentDAO {
    Department getDepartmentByName(String departmentName, Session session);
    List<Department> getAllDepartment(Session session);
    void addDepartment(Department department, Session session);
    void updateDepartment(Department department, Session session);
    void deleteDepartment(int departmentId, Session session);
}
