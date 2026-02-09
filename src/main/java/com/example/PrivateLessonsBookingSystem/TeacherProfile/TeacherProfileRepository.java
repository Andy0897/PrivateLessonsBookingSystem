package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherProfileRepository extends CrudRepository<TeacherProfile, Long> {
    @Query(nativeQuery = true, value = "SELECT teacher_profile_id FROM `teacher_profiles` WHERE user_user_id = :userId;")
    public Long getIdByUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "SELECT teacher_profile_id, introduction, price_per_hour, profile_picture, work_end, work_start, user_user_id FROM teacher_profiles AS tp " +
            "JOIN teacher_profiles_subjects AS tps ON tps.teacher_profile_teacher_profile_id = tp.teacher_profile_id " +
            "WHERE tps.subjects_subject_id = :subjectId")
    public List<TeacherProfile> findAllTeacherProfilesBySubjectId(@Param("subjectId") Long subjectId);
}