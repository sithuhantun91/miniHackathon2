package com.sithuhantun.dao;

import com.sithuhantun.model.Department;
import com.sithuhantun.model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public interface TeacherDAO {
    Teacher getTeacherById(int teacherId, Session session);
    Teacher getTeacherByName(String teacherName, Session session);
    List<Teacher> getAllTeacher(Session session);
    void addTeacher(Teacher teacher, Session session);
    void updateTeacher(Teacher teacher, Session session);
    void deleteTeacher(int teacherId, Session session);
}
