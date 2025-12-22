package com.example.PrivateLessonsBookingSystem.User;

import com.example.PrivateLessonsBookingSystem.ImageEncoder;
import com.example.PrivateLessonsBookingSystem.TeacherProfile.TeacherProfile;
import com.example.PrivateLessonsBookingSystem.TeacherProfile.TeacherProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    UserService userService;
    UserRepository userRepository;
    TeacherProfileRepository teacherProfileRepository;

    public UserController(UserService userService, UserRepository userRepository, TeacherProfileRepository teacherProfileRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.teacherProfileRepository = teacherProfileRepository;
    }

    @GetMapping({"/", "/home"})
    public String getHome(Model model) {
        List<TeacherProfile> teachers = (List<TeacherProfile>) teacherProfileRepository.findAll();
        model.addAttribute("teachers", teachers.size() > 3 ? teachers.subList(0, 3) : teachers);
        model.addAttribute("encoder", new ImageEncoder());
        return "home";
    }

    @GetMapping("/sign-in")
    public String getSignIn(Principal principal) {
        if (principal != null) {
            return "redirect:/access-denied";
        }
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "sign-up";
    }

    @PostMapping("/submit")
    public String submitUser(@Valid User user, BindingResult bindingResult, Model model) {
        return userService.submitUser(user, bindingResult, model);
    }

    @GetMapping("/profile")
    public String getProfile(Principal principal, Model model) {
        User user = userRepository.getUserByUsername(principal.getName());
        if (user.getRole().equals("TEACHER")) {
            TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherProfileRepository.getIdByUserId(user.getId())).get();
            model.addAttribute("teacherProfile", teacherProfile);
            model.addAttribute("user", user);
            model.addAttribute("encoder", new ImageEncoder());
        } else if (user.getRole().equals("STUDENT")) {
            model.addAttribute("user", user);
            model.addAttribute("teacherProfile", new TeacherProfile());
        }
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "access-denied";
    }
}