package com.sithuhantun.dao;

import com.sithuhantun.model.Department;
import com.sithuhantun.model.Teacher;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO{
    @Override
    public Department getDepartmentByName(String deptName, Session session) {
        TypedQuery<Department> query = session.createQuery("SELECT d FROM Department d WHERE d.deptName = :deptName", Department.class);
        query.setParameter("deptName", deptName);
        return query.getSingleResult();
    }

    @Override
    public List<Department> getAllDepartment(Session session) {
        TypedQuery<Department> query = session.createQuery("SELECT d FROM Department d", Department.class);
        List<Department> departments = query.getResultList();
        for(Department department : departments) {
            TypedQuery<Teacher> query2 = session.createQuery("SELECT t FROM Teacher t WHERE t.department.deptId = :deptId", Teacher.class);
            query2.setParameter("deptId", department.getDeptId());
            department.setTeacherList(query2.getResultList());
        }
        return departments;
    }

    @Override
    public void addDepartment(Department department, Session session) {
        session.merge(department);
    }

    @Override
    public void updateDepartment(Department department, Session session) {
        Query<Department> query = session.createQuery("UPDATE Department d SET d.deptName = :deptName WHERE d.deptId = :deptId");
        query.setParameter("deptId", department.getDeptId());
        query.setParameter("deptName", department.getDeptName());
        query.executeUpdate();
    }

    @Override
    public void deleteDepartment(int departmentId, Session session) {
        Query<Department> query = session.createQuery("DELETE Department d WHERE d.deptId = :deptId");
        query.setParameter("deptId", departmentId);
        query.executeUpdate();
    }
}
