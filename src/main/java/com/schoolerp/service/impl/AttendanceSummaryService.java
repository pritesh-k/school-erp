package com.schoolerp.service.impl;

import com.schoolerp.dto.response.AttendanceSummaryDto;
import com.schoolerp.dto.response.ClassAttendanceSummaryDto;
import com.schoolerp.enums.AttendanceStatus;
import com.schoolerp.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceSummaryService {

    private final AttendanceRepository repo;

    public AttendanceSummaryDto getTodaySummary() {
        LocalDate today = LocalDate.now();

        long totalStudents = repo.countActiveStudents();
        List<Object[]> counts = repo.countByStatusForDate(today);

        long present = 0, absent = 0, late = 0, leave = 0;
        for (Object[] row : counts) {
            AttendanceStatus status = (AttendanceStatus) row[0];
            long count = (Long) row[1];
            switch (status) {
                case PRESENT -> present = count;
                case ABSENT -> absent = count;
                case LATE -> late = count;
                case LEAVE -> leave = count;
            }
        }

        double percentage = totalStudents > 0 ? (present * 100.0 / totalStudents) : 0;

        return AttendanceSummaryDto.builder()
                .date(today)
                .totalStudents(totalStudents)
                .presentCount(present)
                .absentCount(absent)
                .lateCount(late)
                .leaveCount(leave)
                .percentagePresent(percentage)
                .build();
    }

    public List<ClassAttendanceSummaryDto> getTodayClassWiseSummary() {
        LocalDate today = LocalDate.now();

        List<Object[]> counts = repo.countByClassAndStatusForDate(today);

        Map<Long, ClassAttendanceSummaryDto.ClassAttendanceSummaryDtoBuilder> map = new HashMap<>();

        for (Object[] row : counts) {
            Long classId = (Long) row[0];
            String className = (String) row[1];
            String sectionName = (String) row[2];
            AttendanceStatus status = (AttendanceStatus) row[3];
            long count = (Long) row[4];

            var builder = map.computeIfAbsent(classId, id -> ClassAttendanceSummaryDto.builder()
                    .classId(id)
                    .className(className)
                    .sectionName(sectionName)
                    .date(today)
                    .totalStudents(repo.countActiveStudentsInClass(id))
            );

            switch (status) {
                case PRESENT -> builder.presentCount(count);
                case ABSENT -> builder.absentCount(count);
            }
        }

        return map.values().stream()
                .map(b -> {
                    var dto = b.build();
                    dto.setPercentagePresent(dto.getTotalStudents() > 0
                            ? (dto.getPresentCount() * 100.0 / dto.getTotalStudents())
                            : 0);
                    return dto;
                })
                .toList();
    }
}

