package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import com.example.PrivateLessonsBookingSystem.Appointment.AppointmentService;
import com.example.PrivateLessonsBookingSystem.Subject.SubjectRepository;
import com.example.PrivateLessonsBookingSystem.User.User;
import com.example.PrivateLessonsBookingSystem.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;

@Service
public class TeacherProfileService {
    SubjectRepository subjectRepository;
    AppointmentService appointmentService;
    TeacherProfileRepository teacherProfileRepository;
    UserRepository userRepository;

    public TeacherProfileService(SubjectRepository subjectRepository, AppointmentService appointmentService, TeacherProfileRepository teacherProfileRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.appointmentService = appointmentService;
        this.teacherProfileRepository = teacherProfileRepository;
        this.userRepository = userRepository;
    }

    public String submitTeacherProfile(TeacherProfile teacherProfile, BindingResult bindingResult, Principal principal, Model model) {
        if(bindingResult.hasFieldErrors("introduction") || teacherProfile.getSubjects().isEmpty()) {
            model.addAttribute("teacherProfile", teacherProfile);
            model.addAttribute("subjects", subjectRepository.findAll());
            model.addAttribute("times", appointmentService.generateTimes());
            model.addAttribute("isSubjectSelected", !teacherProfile.getSubjects().isEmpty());
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