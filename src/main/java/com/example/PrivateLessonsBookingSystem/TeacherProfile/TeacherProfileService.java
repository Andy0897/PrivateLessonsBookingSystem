package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import com.example.PrivateLessonsBookingSystem.User.User;
import com.example.PrivateLessonsBookingSystem.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;

@Service
public class TeacherProfileService {
    UserRepository userRepository;
    TeacherProfileRepository teacherProfileRepository;

    public TeacherProfileService(UserRepository userRepository, TeacherProfileRepository teacherProfileRepository) {
        this.userRepository = userRepository;
        this.teacherProfileRepository = teacherProfileRepository;
    }

    public String submitTeacherProfile(TeacherProfile teacherProfile, BindingResult bindingResult, Principal principal, Model model) {
        if(bindingResult.hasFieldErrors("introduction")) {
            model.addAttribute("teacherProfile", teacherProfile);
            return "teacherProfile/create";
        }
        User user = userRepository.getUserByUsername(principal.getName());
        user.setRole("TEACHER");
        userRepository.save(user);
        teacherProfile.setUser(user);
        teacherProfileRepository.save(teacherProfile);
        return "redirect:/";
    }
}