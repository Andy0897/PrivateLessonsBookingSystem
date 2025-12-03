package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import com.example.PrivateLessonsBookingSystem.Appointment.AppointmentService;
import com.example.PrivateLessonsBookingSystem.Subject.SubjectRepository;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/teacher-profiles")
public class TeacherProfileController {
    TeacherProfileService teacherProfileService;
    SubjectRepository subjectRepository;
    AppointmentService appointmentService;

    public TeacherProfileController(TeacherProfileService teacherProfileService, SubjectRepository subjectRepository, AppointmentService appointmentService) {
        this.teacherProfileService = teacherProfileService;
        this.subjectRepository = subjectRepository;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/create")
    public String getCreateTeacherProfile(Model model) {
        TeacherProfile teacherProfile = new TeacherProfile();
        model.addAttribute("teacherProfile", teacherProfile);
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("times", appointmentService.generateTimes());
        model.addAttribute("isSubjectSelected", true);
        return "teacher-profile/create";
    }

    @PostMapping("/submit")
    public String getSubmitTeacherProfile(@Valid TeacherProfile teacherProfile, BindingResult bindingResult, Principal principal, Model model) {
        return teacherProfileService.submitTeacherProfile(teacherProfile, bindingResult, principal, model);
    }
}