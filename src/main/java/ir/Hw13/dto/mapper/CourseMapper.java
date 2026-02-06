package ir.Hw13.dto.mapper;

import ir.Hw13.dto.CourseDto;
import ir.Hw13.entity.Course;
import ir.Hw13.entity.Student;
import ir.Hw13.entity.Teacher;

import java.util.Objects;
import java.util.Set;

public class CourseMapper {

    public Course toEntity(CourseDto dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setBegin(dto.getBegin());
        course.setFinish(dto.getFinish());
        course.setStudents(null);
        course.setTeacher(null);
        return course;
    }

    public void loadCourse(Course course) {
        System.out.println("Id: " + course.getId());
        System.out.println("Title: " + course.getTitle());
        System.out.println("Starting date: " + course.getBegin());
        System.out.println("Finishing date: " + course.getFinish());
        Teacher teacher = course.getTeacher();
        if (Objects.isNull(teacher)) {
            System.out.println("Teacher: NOT ASSIGNED");
        } else {
            System.out.println("Teacher: " + teacher.getFirstName() + " " + course.getTeacher().getLastName());
        }
        Set<Student> students = course.getStudents();
        if (students.isEmpty()) {
            System.out.println("Students: NOT ASSIGNED");
        } else {
            students.forEach(s -> System.out.println(s.getFirstName() + " " + s.getLastName()));
        }
    }
}
