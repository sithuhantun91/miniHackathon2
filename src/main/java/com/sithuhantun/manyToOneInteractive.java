package com.sithuhantun;

import com.sithuhantun.dao.DepartmentDAO;
import com.sithuhantun.dao.DepartmentDAOImpl;
import com.sithuhantun.dao.TeacherDAO;
import com.sithuhantun.dao.TeacherDAOImpl;
import com.sithuhantun.model.Department;
import com.sithuhantun.model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class manyToOneInteractive {
    public static void main(String[] args) {
        manyToOneInteractive();
    }

    public static void manyToOneInteractive() {
        System.out.println("Welcome to ManyToOneInteractive!");
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
//        Transaction transaction = session.beginTransaction();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n0. Exit");
                System.out.println("1. Manage Departments");
                System.out.println("2. Manage Teachers");
                System.out.println("3. Assign Teacher to Department");
                System.out.println("4. List Teachers");
                System.out.println("5. List Department");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                        manageDepartments(scanner, factory);
                        break;
                    case 2:
                        manageTeachers(scanner, factory);
                        break;
                    case 3:
                        assignTeacherToDepartment(scanner, factory);
                        break;
                    case 4:
                        listTeachers(session);
                        break;
                    case 5:
                        listDepts(session);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }

    private static void manageDepartments(Scanner scanner, SessionFactory factory) {
        while (true) {
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();

            DepartmentDAO departmentDAO = new DepartmentDAOImpl();
            System.out.println("\n1. Add Departments");
            System.out.println("2. Delete Department");
            System.out.println("3. Modify Department");
            System.out.println("4. Go back to menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nEnter department's name to add: ");
                    Department newDepartment = new Department(scanner.next());
                    departmentDAO.addDepartment(newDepartment, session);
                    break;
                case 2:
                    System.out.println("\nEnter department's Id to delete: ");
                    int deptId = scanner.nextInt();
                    departmentDAO.deleteDepartment(deptId, session);
                    break;
                case 3:
                    Department updateDepartment = new Department();
                    System.out.println("\nEnter department's Id to update: ");
                    updateDepartment.setDeptId(scanner.nextInt());
                    System.out.println("\nEnter department's name to update: ");
                    updateDepartment.setDeptName(scanner.next());
                    departmentDAO.updateDepartment(updateDepartment, session);
                    break;
                case 4:
                    System.out.println("Go back to menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }

            transaction.commit();
            session.close();
        }
    }

    private static void manageTeachers(Scanner scanner, SessionFactory factory) {
        while (true) {
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();

            TeacherDAO teacherDAO = new TeacherDAOImpl();
            System.out.println("\n1. Add Teachers");
            System.out.println("2. Delete Teacher");
            System.out.println("3. Modify Teacher");
            System.out.println("4. Go back to menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\nEnter teacher's name to add: ");
                    Teacher newTeacher = new Teacher(scanner.next());
                    teacherDAO.addTeacher(newTeacher, session);
                    break;
                case 2:
                    System.out.println("\nEnter teacher's Id to delete: ");
                    int teacherId = scanner.nextInt();
                    teacherDAO.deleteTeacher(teacherId, session);
                    break;
                case 3:
                    Teacher updateTeacher = new Teacher();
                    System.out.println("\nEnter teacher's Id to update: ");
                    updateTeacher = teacherDAO.getTeacherById(scanner.nextInt(), session);
                    System.out.println("\nEnter teacher's name to update: ");
                    updateTeacher.setTeacherName(scanner.next());
                    teacherDAO.updateTeacher(updateTeacher, session);
                    break;
                case 4:
                    System.out.println("Go back to menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }

            transaction.commit();
            session.close();
        }
    }

    private static void assignTeacherToDepartment(Scanner scanner, SessionFactory factory) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        TeacherDAO teacherDAO = new TeacherDAOImpl();
        DepartmentDAO departmentDAO = new DepartmentDAOImpl();

        System.out.println("Which Teacher would you like to modify: ");
        String teacherName = scanner.next();
        Teacher teacher = teacherDAO.getTeacherByName(teacherName, session);

        System.out.println("Which department would you like to assign to Teacher: ");
        String departmentName = scanner.next();
        //set department to teacher
        Department department = departmentDAO.getDepartmentByName(departmentName, session);
        teacher.setDepartment(department);
        //add teacher to department's teacher List
        List<Teacher> teacherList = department.getTeacherList();
        boolean alreadyExist = false;
        for (Teacher teacher1 : teacherList) {
            if(teacher1.getTeacherId() == teacher.getTeacherId()) {
                alreadyExist = true;
            }
        }
        if(!alreadyExist) {
            teacherList.add(teacher);
        }

        teacherDAO.updateTeacher(teacher, session);

        transaction.commit();
        session.close();
    }

    private static void listDepts(Session session) {
        DepartmentDAO departmentDAO = new DepartmentDAOImpl();
        List<Department> departmentList = departmentDAO.getAllDepartment(session);
        for (Department department : departmentList) {
            System.out.println(department);
        }
    }

    private static void listTeachers(Session session) {
        TeacherDAO teacherDAO = new TeacherDAOImpl();
        List<Teacher> teacherList = teacherDAO.getAllTeacher(session);
        for (Teacher teacher : teacherList) {
            System.out.println(teacher);
        }
    }
}
