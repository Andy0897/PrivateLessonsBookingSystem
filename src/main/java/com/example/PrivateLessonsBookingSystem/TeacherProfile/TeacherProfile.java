package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import com.example.PrivateLessonsBookingSystem.Subject.Subject;
import com.example.PrivateLessonsBookingSystem.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "teacher_profiles")
public class TeacherProfile {
    @Column(name = "teacher_profile_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @Size(max = 1000, message = "Представянето не трябва да надвишава 1000 символа")
    @NotEmpty(message = "Полето не може да бъде празно.")
    private String introduction;

    @OneToMany
    @NotEmpty(message = "Полето не може да бъде празно.")
    List<Subject> subjects = new ArrayList<>();

    @NotNull(message = "Полето не може да бъде празно.")
    private LocalTime workStart;

    @NotNull(message = "Полето не може да бъде празно.")
    private LocalTime workEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public LocalTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalTime workStart) {
        this.workStart = workStart;
    }

    public LocalTime getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(LocalTime workEnd) {
        this.workEnd = workEnd;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
}