package com.schoolerp.specifications;

import com.schoolerp.entity.Student;
import com.schoolerp.entity.StudentEnrollment;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + name.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), likePattern),
                    cb.like(cb.lower(root.get("lastName")), likePattern),
                    cb.like(cb.lower(root.get("admissionNumber")), likePattern),
                    cb.like(cb.lower(root.get("rollNumber")), likePattern)
            );
        };
    }

    public static Specification<Student> notEnrolledIn(Long academicSessionId) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<StudentEnrollment> se = subquery.from(StudentEnrollment.class);
            subquery.select(se.get("student").get("id"))
                    .where(
                            cb.equal(se.get("academicSession").get("id"), academicSessionId),
                            cb.isTrue(se.get("active"))
                    );
            return cb.not(root.get("id").in(subquery));
        };
    }
}
