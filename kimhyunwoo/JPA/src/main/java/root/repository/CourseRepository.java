package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
