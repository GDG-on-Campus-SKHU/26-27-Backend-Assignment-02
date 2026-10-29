package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.domain.SchoolClass;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}
