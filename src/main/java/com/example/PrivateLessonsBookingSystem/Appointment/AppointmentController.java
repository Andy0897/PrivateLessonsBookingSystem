package com.example.PrivateLessonsBookingSystem.Appointment;

import com.example.PrivateLessonsBookingSystem.Subject.SubjectRepository;
import com.example.PrivateLessonsBookingSystem.TeacherProfile.TeacherProfile;
import com.example.PrivateLessonsBookingSystem.TeacherProfile.TeacherProfileRepository;
import com.example.PrivateLessonsBookingSystem.User.User;
import com.example.PrivateLessonsBookingSystem.User.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    AppointmentService appointmentService;
    AppointmentRepository appointmentRepository;
    TeacherProfileRepository teacherProfileRepository;
    SubjectRepository subjectRepository;
    UserRepository userRepository;

    public AppointmentController(AppointmentService appointmentService, AppointmentRepository appointmentRepository, TeacherProfileRepository teacherProfileRepository, SubjectRepository subjectRepository, UserRepository userRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/book/{teacherId}")
    public String getBookAppointment(@PathVariable("teacherId") Long teacherId, Model model) {
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("subjects", teacherProfileRepository.findById(teacherId).get().getSubjects());
        return "appointment/book";
    }

    @PostMapping("/submit/{teacherId}")
    public String getSubmitAppointment(@PathVariable("teacherId") Long teacherId, @Valid Appointment appointment, BindingResult bindingResult, Principal principal, Model model) {
        return appointmentService.submitAppointment(appointment, bindingResult, teacherId, principal, model);
    }

    @GetMapping
    public String getShowAppointments(Principal principal, Model model) {
        User user = userRepository.getUserByUsername(principal.getName());
        if (user.getRole().equals("TEACHER")) {
            TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherProfileRepository.getIdByUserId(user.getId())).get();
            model.addAttribute("teacher", teacherProfile);
            model.addAttribute("student", user);
        } else {
            model.addAttribute("student", user);
            model.addAttribute("teacher", new TeacherProfile());
        }
        return "appointment/show";
    }

    @ResponseBody
    @GetMapping("/update-free-daily-times/{date}/{teacherId}")
    public List<LocalTime> getFreeDailyTimes(@PathVariable("date") LocalDate date, @PathVariable("teacherId") Long teacherId) {
        return appointmentService.getFreeTimesByDate(date, teacherId);
    }

    @ResponseBody
    @GetMapping("/update-daily-teacher-appointments/{date}/{teacherId}")
    public List<Appointment> getTeacherAppointmentsByDate(@PathVariable("date") LocalDate date, @PathVariable("teacherId") Long teacherId) {
        return appointmentService.getTeacherAppointmentsByDate(date, teacherId);
    }

    @ResponseBody
    @GetMapping("/update-daily-student-appointments/{date}/{studentId}")
    public List<Appointment> getStudentAppointmentsByDate(@PathVariable("date") LocalDate date, @PathVariable("studentId") Long studentId) {
        return appointmentService.getStudentAppointmentsByDate(date, studentId);
    }

    @ResponseBody
    @GetMapping("/all-teacher-appointments/{teacherId}")
    public List<Appointment> getAllTeacherAppointments(@PathVariable("teacherId") Long teacherId) {
        return appointmentService.getAllTeacherAppointments(teacherId);
    }

    @ResponseBody
    @GetMapping("/all-student-appointments/{studentId}")
    public List<Appointment> getAllStudentAppointments(@PathVariable("studentId") Long studentId) {
        return appointmentService.getAllStudentAppointments(studentId);
    }
}