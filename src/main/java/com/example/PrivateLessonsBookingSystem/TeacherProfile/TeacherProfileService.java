package com.example.PrivateLessonsBookingSystem.TeacherProfile;

import com.example.PrivateLessonsBookingSystem.Appointment.AppointmentService;
import com.example.PrivateLessonsBookingSystem.Subject.SubjectRepository;
import com.example.PrivateLessonsBookingSystem.User.User;
import com.example.PrivateLessonsBookingSystem.User.UserDetailsServiceImpl;
import com.example.PrivateLessonsBookingSystem.User.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.Subject;
import java.security.Principal;
import java.time.LocalTime;

@Service
public class TeacherProfileService {
    SubjectRepository subjectRepository;
    AppointmentService appointmentService;
    TeacherProfileRepository teacherProfileRepository;
    UserRepository userRepository;
    UserDetails userDetails;

    public TeacherProfileService(SubjectRepository subjectRepository, AppointmentService appointmentService, TeacherProfileRepository teacherProfileRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.appointmentService = appointmentService;
        this.teacherProfileRepository = teacherProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String submitTeacherProfile(TeacherProfile teacherProfile, BindingResult bindingResult, MultipartFile profilePicture, Principal principal, Model model) {
        boolean timesErrors = checkForTimesErrors(teacherProfile.getWorkStart(), teacherProfile.getWorkEnd());
        boolean imageError = false;
        boolean nullImage = false;
        try {
            if (!profilePicture.isEmpty()) {
                teacherProfile.setProfilePicture(profilePicture.getBytes());
            }
            else {
                nullImage = true;
            }
        } catch(Exception e) {
            imageError = true;
        }

        if(bindingResult.hasFieldErrors("introduction") || bindingResult.hasFieldErrors("subjects") || bindingResult.hasFieldErrors("pricePerHour") || nullImage || imageError || timesErrors) {
            model.addAttribute("teacherProfile", teacherProfile);
            model.addAttribute("isImageUploaded", teacherProfile.getProfilePicture() != null);
            model.addAttribute("hasUploadError", imageError);
            model.addAttribute("subjects", subjectRepository.findAll());
            model.addAttribute("times", appointmentService.generateTimes());
            model.addAttribute("isSubjectSelected", !teacherProfile.getSubjects().isEmpty());
            model.addAttribute("timesErrors", timesErrors);
            return "teacher-profile/create";
        }
        User user = userRepository.getUserByUsername(principal.getName());
        user.setRole("TEACHER");
        userRepository.save(user);
        teacherProfile.setUser(user);
        teacherProfileRepository.save(teacherProfile);

        return "redirect:/logout";
    }

    private boolean checkForTimesErrors(LocalTime startTime, LocalTime endTime) {
        return startTime.isAfter(endTime) || startTime.getHour() == endTime.getHour();
    }
}