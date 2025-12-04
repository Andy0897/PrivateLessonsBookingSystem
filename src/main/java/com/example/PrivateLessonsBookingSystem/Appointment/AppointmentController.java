package com.example.PrivateLessonsBookingSystem.Appointment;

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

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/book/{teacherId}")
    public String getBookAppointment(@PathVariable("teacherId") Long teacherId, Model model) {
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("teacherId", teacherId);
        return "appointment/book";
    }

    @PostMapping("/submit/{teacherId}")
    public String getSubmitAppointment(@PathVariable("teacherId") Long teacherId, @Valid Appointment appointment, BindingResult bindingResult, Principal principal, Model model) {
        return appointmentService.submitAppointment(appointment, bindingResult, teacherId, principal, model);
    }

    @ResponseBody
    @GetMapping("/update-free-daily-times/{date}/{teacherId}")
    public List<LocalTime> getFreeDailyTimes(@PathVariable("date") LocalDate date, @PathVariable("teacherId") Long teacherId) {
        return appointmentService.getFreeTimesByDate(date, teacherId);
    }
}