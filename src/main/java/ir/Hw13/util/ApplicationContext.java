package ir.Hw13.util;

import ir.Hw13.dto.mapper.StudentSignUpMapper;
import ir.Hw13.dto.mapper.TeacherSignUpMapper;
import ir.Hw13.repository.StudentRepositoryImpl;
import ir.Hw13.repository.TeacherRepositoryImpl;
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
    private StudentSignUpMapper studentMapper;
    private StudentServiceImpl studentService;

    private TeacherRepositoryImpl teacherRepository;
    private TeacherSignUpMapper teacherMapper;
    private TeacherServiceImpl teacherService;

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

     public StudentSignUpMapper getStudentMapper(){
         if (Objects.isNull(studentMapper)){
             studentMapper = new StudentSignUpMapper();
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

     public TeacherSignUpMapper getSignUpTeacherMapper(){
         if (Objects.isNull(teacherMapper)){
             teacherMapper = new TeacherSignUpMapper();
         }
         return teacherMapper;
     }

    public TeacherServiceImpl getTeacherService(){
        if (Objects.isNull(teacherService)){
            teacherService = new TeacherServiceImpl();
        }
        return teacherService;
    }


}
