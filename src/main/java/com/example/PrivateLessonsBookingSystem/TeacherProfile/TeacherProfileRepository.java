package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TeacherProfileRepository extends CrudRepository<TeacherProfile, Long> {
    @Query(nativeQuery = true, value = "SELECT teacher_profile_id FROM `teacher_profiles` WHERE user_user_id = :userId;")
    public Long getIdByUserId(@Param("userId") Long userId);
}