package com.example.PrivateLessonsBookingSystem.Appointment;

import com.example.PrivateLessonsBookingSystem.Subject.Subject;
import com.example.PrivateLessonsBookingSystem.TeacherProfile.TeacherProfile;
import com.example.PrivateLessonsBookingSystem.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Column(name = "appointment_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private TeacherProfile teacher;

    @OneToOne
    private User student;

    @OneToOne
    private Subject subject;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeacherProfile getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherProfile teacher) {
        this.teacher = teacher;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
