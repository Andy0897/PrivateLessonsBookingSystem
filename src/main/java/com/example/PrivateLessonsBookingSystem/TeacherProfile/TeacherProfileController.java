package com.example.PrivateLessonsBookingSystem.TeacherProfile;

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

    @GetMapping("/create")
    public String getCreateTeacherProfile(Model model) {
        TeacherProfile teacherProfile = new TeacherProfile();
        model.addAttribute("teacherProfile", teacherProfile);
        return "teacher-profile/create";
    }

    @PostMapping("/submit")
    public String getSubmitTeacherPrfile(@Valid TeacherProfile teacherProfile, BindingResult bindingResult, Principal principal, Model model) {
        return teacherProfileService.submitTeacherProfile(teacherProfile, bindingResult, principal, model);
    }
}