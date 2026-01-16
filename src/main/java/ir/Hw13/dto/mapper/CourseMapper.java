package ir.Hw13.dto.mapper;

import ir.Hw13.dto.CourseDto;
import ir.Hw13.entity.Course;

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
}
