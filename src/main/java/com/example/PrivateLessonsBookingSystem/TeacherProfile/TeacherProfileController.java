package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import com.example.PrivateLessonsBookingSystem.Appointment.AppointmentService;
import com.example.PrivateLessonsBookingSystem.ImageEncoder;
import com.example.PrivateLessonsBookingSystem.Subject.SubjectRepository;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/teacher-profiles")
public class TeacherProfileController {
    TeacherProfileRepository teacherProfileRepository;
    TeacherProfileService teacherProfileService;
    SubjectRepository subjectRepository;
    AppointmentService appointmentService;

    public TeacherProfileController(TeacherProfileRepository teacherProfileRepository, TeacherProfileService teacherProfileService, SubjectRepository subjectRepository, AppointmentService appointmentService) {
        this.teacherProfileRepository = teacherProfileRepository;
        this.teacherProfileService = teacherProfileService;
        this.subjectRepository = subjectRepository;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String getShowTeacherProfiles(Model model) {
        model.addAttribute("teacherProfiles", teacherProfileRepository.findAll());
        model.addAttribute("encoder", new ImageEncoder());
        return "teacher-profile/show-all";
    }

    @GetMapping("/{id}")
    public String getShowSingleTeacherProfile(@PathVariable("id") Long teacherId, Model model) {
        TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherId).get();
        model.addAttribute("teacherProfile", teacherProfile);
        model.addAttribute("encoder", new ImageEncoder());
        return "teacher-profile/show-single";
    }

    @GetMapping("/create")
    public String getCreateTeacherProfile(Model model) {
        TeacherProfile teacherProfile = new TeacherProfile();
        model.addAttribute("teacherProfile", teacherProfile);
        model.addAttribute("isImageUploaded", true);
        model.addAttribute("hasUploadError", false);
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("times", appointmentService.generateTimes());
        model.addAttribute("isSubjectSelected", true);
        model.addAttribute("timesErrors", false);
        return "teacher-profile/create";
    }

    @PostMapping("/submit")
    public String getSubmitTeacherProfile(@Valid TeacherProfile teacherProfile, BindingResult bindingResult, @RequestParam("profilePicture") MultipartFile profilePicture, Principal principal, Model model) {
        return teacherProfileService.submitTeacherProfile(teacherProfile, bindingResult, profilePicture, principal, model);
    }
}