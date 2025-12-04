package com.example.PrivateLessonsBookingSystem.Appointment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    public List<Appointment> findAllByDate(LocalDate localDate);
}