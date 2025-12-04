package com.example.PrivateLessonsBookingSystem.Appointment;

import com.example.PrivateLessonsBookingSystem.TeacherProfile.TeacherProfile;
import com.example.PrivateLessonsBookingSystem.TeacherProfile.TeacherProfileRepository;
import com.example.PrivateLessonsBookingSystem.User.User;
import com.example.PrivateLessonsBookingSystem.User.UserRepository;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.AlgorithmParameterGenerator;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    AppointmentRepository appointmentRepository;
    TeacherProfileRepository teacherProfileRepository;
    UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, TeacherProfileRepository teacherProfileRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.userRepository = userRepository;
    }

    public String submitAppointment(Appointment appointment, BindingResult bindingResult, Long teacherId, Principal principal, Model model) {
        if(bindingResult.hasFieldErrors("date") || bindingResult.hasFieldErrors("time")) {
            System.out.println("Binding res");
            System.out.println(bindingResult.hasFieldErrors("date"));
            System.out.println(bindingResult.hasFieldErrors("time"));
            model.addAttribute("appointment", appointment);
            model.addAttribute("teacherId", teacherId);
            return "appointment/book";
        }
        TeacherProfile teacher = teacherProfileRepository.findById(teacherProfileRepository.getTeacherProfileIdByUserId(teacherId)).get();
        appointment.setTeacher(teacher);
        User student = userRepository.getUserByUsername(principal.getName());
        appointment.setStudent(student);
        appointmentRepository.save(appointment);
        return "redirect:/";
    }

    public List<LocalTime> generateTimes() {
        LocalTime startTime = LocalTime.MIN;
        LocalTime endTime = LocalTime.of(23, 0, 0);
        List<LocalTime> times = new ArrayList<>();
        LocalTime time = startTime;
        while (time.isBefore(endTime)) {
            times.add(time);
            time = time.plusHours(1);
        }
        return times;
    }

    public List<LocalTime> getFreeTimesByDate(LocalDate date, Long teacherId) {
        TeacherProfile teacherProfile = teacherProfileRepository.findById(teacherId).get();
        List<Appointment> appointmentsByDate = appointmentRepository.findAllByDate(date).stream().filter(appointment -> appointment.getTeacher().getId() == teacherId).toList();
        LocalTime localTime = teacherProfile.getWorkStart();
        List<LocalTime> dailyFreeTimes = new ArrayList<>();
        while (localTime.isBefore(teacherProfile.getWorkEnd()))
        {
            boolean isBusy = true;
            for(Appointment appointment : appointmentsByDate) {
                if(appointment.getTime().equals(localTime)) {
                    isBusy = false;
                }
            }
            if(isBusy) {
                dailyFreeTimes.add(localTime);
            }
            localTime = localTime.plusHours(1);
        }
        return dailyFreeTimes;
    }
}