package ir.Hw13.util;

import ir.Hw13.dto.mapper.*;
import ir.Hw13.repository.*;
import ir.Hw13.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

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

    private CourseService courseService;
    private CourseRepository courseRepository;


    private Validator validator;

    private PersonMapper personMapper;
    private CourseMapper courseMapper;

    private ExportService exportService;

    private TestService testService;
    private TestRepository testRepository;
    private TestMapper testMapper;

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

     public TeacherMapper getTeacherMapper(){
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

    public Validator getValidator(){
        if (Objects.isNull(validator)){
            try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
                validator = validatorFactory.getValidator();
            }
        }
        return validator;
    }

    public CourseMapper getCourseMapper(){
        if (Objects.isNull(courseMapper)){
            courseMapper = new CourseMapper();
        }
        return courseMapper;
    }

    public CourseService getCourseService(){
        if (Objects.isNull(courseService)){
            courseService = new CourseService();
        }
        return courseService;
    }

    public CourseRepository getCourseRepository() {
        if (Objects.isNull(courseRepository)){
            courseRepository = new CourseRepository(getEntityManager());
        }
        return courseRepository;
    }

    public ExportService getExportService(){
        if (Objects.isNull(exportService)){
            exportService = new ExportService(getTeacherService());
        }
        return exportService;
    }

    public TestRepository getTestRepository(){
        if (Objects.isNull(testRepository)){
            testRepository = new TestRepository(getEntityManager());
        }
        return testRepository;
    }

    public TestService getTestService(){
        if (Objects.isNull(testService)){
            testService = new TestService(getTestRepository(), getTestMapper());
        }
        return testService;
    }

    public TestMapper getTestMapper() {
        if (Objects.isNull(testMapper)){
            testMapper = new TestMapper();
        }
        return testMapper;
    }
}
