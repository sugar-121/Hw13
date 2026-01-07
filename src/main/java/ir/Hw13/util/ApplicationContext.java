package ir.Hw13.util;

import ir.Hw13.dto.mapper.PersonMapper;
import ir.Hw13.dto.mapper.StudentMapper;
import ir.Hw13.dto.mapper.TeacherMapper;
import ir.Hw13.repository.ManagerRepository;
import ir.Hw13.repository.StudentRepositoryImpl;
import ir.Hw13.repository.TeacherRepositoryImpl;
import ir.Hw13.service.ManagerService;
import ir.Hw13.service.StudentServiceImpl;
import ir.Hw13.service.TeacherServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Objects;

public class ApplicationContext {
    private static ApplicationContext context;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private StudentRepositoryImpl studentRepository;
    private StudentMapper studentMapper;
    private StudentServiceImpl studentService;

    private TeacherRepositoryImpl teacherRepository;
    private TeacherMapper teacherMapper;
    private TeacherServiceImpl teacherService;

    private ManagerService managerService;
    private ManagerRepository managerRepository;

    private PersonMapper personMapper;

    private ApplicationContext(){}

    public static ApplicationContext getInstance(){
        if (Objects.isNull(context)){
            context = new ApplicationContext();
        }
        return context;
    }

    private EntityManagerFactory getEntityManagerFactory(){
        if (Objects.isNull(entityManagerFactory)){
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }
        return entityManagerFactory;
    }

    public EntityManager getEntityManager(){
        if (Objects.isNull(entityManager)){
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }
     public StudentRepositoryImpl getStudentRepository(){
        if (Objects.isNull(studentRepository)){
            studentRepository = new StudentRepositoryImpl(getEntityManager());
        }
        return studentRepository;
     }

     public StudentMapper getStudentMapper(){
         if (Objects.isNull(studentMapper)){
             studentMapper = new StudentMapper();
         }
         return studentMapper;
     }

     public StudentServiceImpl getStudentService(){
         if (Objects.isNull(studentService)){
             studentService = new StudentServiceImpl();
         }
         return studentService;
     }

     public TeacherRepositoryImpl getTeacherRepository(){
         if (Objects.isNull(teacherRepository)){
             teacherRepository = new TeacherRepositoryImpl(getEntityManager());
         }
         return teacherRepository;
     }

     public TeacherMapper getSignUpTeacherMapper(){
         if (Objects.isNull(teacherMapper)){
             teacherMapper = new TeacherMapper();
         }
         return teacherMapper;
     }

    public TeacherServiceImpl getTeacherService(){
        if (Objects.isNull(teacherService)){
            teacherService = new TeacherServiceImpl();
        }
        return teacherService;
    }

    public ManagerService getManagerService(){
        if (Objects.isNull(managerService)){
            managerService = new ManagerService();
        }
        return managerService;
    }

    public ManagerRepository getManagerRepository(){
        if (Objects.isNull(managerRepository)){
            managerRepository = new ManagerRepository(getEntityManager());
        }
        return managerRepository;
    }

    public PersonMapper getPersonMapper(){
        if (Objects.isNull(personMapper)){
            personMapper = new PersonMapper();
        }
        return personMapper;
    }
}
