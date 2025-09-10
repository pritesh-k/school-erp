package com.schoolerp.service.impl;

import com.schoolerp.entity.Timetable;
import com.schoolerp.enums.TimetableType;
import org.springframework.data.jpa.domain.Specification;

public class TimetableSpecifications {

    public static Specification<Timetable> hasClassId(Long classId) {
        return (root, query, cb) -> {
            if (classId == null) return null;
            return cb.equal(
                    root.join("sectionSubjectAssignment")
                            .join("section")
                            .join("schoolClass")
                            .get("id"),
                    classId
            );
        };
    }

    public static Specification<Timetable> hasSectionId(Long sectionId) {
        return (root, query, cb) -> {
            if (sectionId == null) return null;
            return cb.equal(
                    root.join("sectionSubjectAssignment")
                            .join("section")
                            .get("id"),
                    sectionId
            );
        };
    }

    public static Specification<Timetable> hasSubjectId(Long subjectId) {
        return (root, query, cb) -> {
            if (subjectId == null) return null;
            return cb.equal(
                    root.join("sectionSubjectAssignment")
                            .join("subject")
                            .get("id"),
                    subjectId
            );
        };
    }

    public static Specification<Timetable> hasType(TimetableType type) {
        return (root, query, cb) -> {
            if (type == null) return null;
            return cb.equal(root.get("type"), type);
        };
    }

    public static Specification<Timetable> belongsToAcademicSession(Long academicSessionId) {
        return (root, query, cb) -> cb.equal(root.join("academicSession").get("id"), academicSessionId);
    }
}
