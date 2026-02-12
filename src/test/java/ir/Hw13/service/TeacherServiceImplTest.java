//package ir.Hw13.service;
//
//import ir.Hw13.dto.PersonSignUpDto;
//import ir.Hw13.entity.Status;
//import ir.Hw13.entity.Teacher;
//import ir.Hw13.repository.TeacherRepositoryImpl;
//import jakarta.validation.Validator;
//import lombok.experimental.ExtensionMethod;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class TeacherServiceImplTest {
//
//    static TeacherServiceImpl teacherService;
//    static TeacherRepositoryImpl teacherRepository;
//    static Validator validator;
//
//    @BeforeAll
//    static void setUp() {
//        teacherRepository =
//                Mockito.mock(TeacherRepositoryImpl.class);
//        validator =
//                Mockito.mock(Validator.class);
//        teacherService = new TeacherServiceImpl(teacherRepository,validator);
//
//    }
////
////    @Mock
////    TeacherRepositoryImpl teacherRepository;
////    @Mock
////    Validator validator;
////
////    @InjectMocks
////    TeacherServiceImpl teacherService;
//
//
////    @Test
////    void signUp() {
////        PersonSignUpDto dto =
////                new PersonSignUpDto
////                        ("ali", "alii", "1234566789", Status.WAITING_FOR_SUBMIT);
////        teacherService.signUp(dto);
////
////        verify(teacherRepository,times(1)).save(any(Teacher.class));
////
////    }
//
//    @Test
//    void ifViolationFailedNotCallSave() {
//        PersonSignUpDto dto =
//                new PersonSignUpDto
//                        ("ali", "alii", "12345", Status.WAITING_FOR_SUBMIT);
//        when(validator.validate(dto)).thenReturn(Set.of())
//
//        teacherService.signUp(dto);
//
//        verify(teacherRepository,times(1)).save(any(Teacher.class));
//
//    }
//}