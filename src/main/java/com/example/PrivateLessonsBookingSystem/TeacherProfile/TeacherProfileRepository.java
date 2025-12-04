package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TeacherProfileRepository extends CrudRepository<TeacherProfile, Long> {
}
