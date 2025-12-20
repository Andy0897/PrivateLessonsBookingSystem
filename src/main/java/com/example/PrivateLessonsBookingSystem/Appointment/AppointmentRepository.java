package com.example.PrivateLessonsBookingSystem.Appointment;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    public List<Appointment> findAllByDate(LocalDate localDate);
    public List<Appointment> findAllByStudentId(Long studentId);
    public List<Appointment> findAllByTeacherId(Long teacherId);
}