package com.sithuhantun.dao;

import com.sithuhantun.model.Department;
import com.sithuhantun.model.Teacher;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TeacherDAOImpl implements TeacherDAO{
    @Override
    public Teacher getTeacherById(int teacherId, Session session) {
        TypedQuery<Teacher> query = session.createQuery("SELECT t FROM Teacher t WHERE t.teacherId = :teacherId", Teacher.class);
        query.setParameter("teacherId", teacherId);
        return query.getSingleResult();
    }

    @Override
    public Teacher getTeacherByName(String teacherName, Session session) {
        TypedQuery<Teacher> query = session.createQuery("SELECT t FROM Teacher t WHERE t.teacherName = :teacherName", Teacher.class);
        query.setParameter("teacherName", teacherName);
        return query.getSingleResult();
    }

    @Override
    public List<Teacher> getAllTeacher(Session session) {
        TypedQuery<Teacher> query = session.createQuery("SELECT t FROM Teacher t", Teacher.class);
        List<Teacher> teacherList = query.getResultList();
        for(Teacher teacher : teacherList) {
            TypedQuery<String> query2 = session.createQuery("SELECT t.department.deptId FROM Teacher t WHERE t.teacherId = :teacherId");
            query2.setParameter("teacherId", teacher.getTeacherId());
            TypedQuery<Department> query3 = session.createQuery("SELECT d FROM Department d WHERE d.deptId = :deptId", Department.class);
            query3.setParameter("deptId", query2.getSingleResult());
            teacher.setDepartment(query3.getSingleResult());
        }
        return teacherList;
    }

    @Override
    public void addTeacher(Teacher teacher, Session session) {
        session.merge(teacher);
    }

    @Override
    public void updateTeacher(Teacher teacher, Session session) {
        Query<Department> query = session.createQuery("UPDATE Teacher t SET t.teacherName = :teacherName WHERE t.teacherId = :teacherId");
        query.setParameter("teacherId", teacher.getTeacherId());
        query.setParameter("teacherName", teacher.getTeacherName());
        query.executeUpdate();
    }

    @Override
    public void deleteTeacher(int teacherId, Session session) {
        Query<Department> query = session.createQuery("DELETE Teacher t WHERE t.teacherId = :teacherId");
        query.setParameter("teacherId", teacherId);
        query.executeUpdate();
    }
}
