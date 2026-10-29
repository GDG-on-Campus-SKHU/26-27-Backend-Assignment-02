package root.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schoolclasses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String major;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Builder
    public SchoolClass(String name, String major, Course course) {
        this.name = name;
        this.major = major;
        this.course = course;
    }

    public void update(String name, String major) {
        this.name = name;
        this.major = major;
    }
}
