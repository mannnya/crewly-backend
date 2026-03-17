package com.crewly.crewly.service;

import com.crewly.crewly.entity.Student;
import com.crewly.crewly.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public String register(String fullName, String email, String password,
                           String department, Integer year) {
        if (studentRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered!");
        }

        Student student = new Student();
        student.setFullName(fullName);
        student.setEmail(email);
        student.setPasswordHash(passwordEncoder.encode(password));
        student.setDepartment(department);
        student.setYear(year);
        student.setIsVerified(false);

        String otp = generateOtp();
        student.setOtp(otp);
        student.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

        studentRepository.save(student);
        sendOtpEmail(email, otp);

        return "Registration successful! Please check your email for OTP.";
    }

    public String verifyOtp(String email, String otp) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        if (student.getIsVerified()) {
            return "Account already verified!";
        }
        if (!student.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP!");
        }
        if (student.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired! Please register again.");
        }

        student.setIsVerified(true);
        student.setOtp(null);
        student.setOtpExpiry(null);
        studentRepository.save(student);

        return "Email verified successfully! You can now login.";
    }

    public Student login(String email, String password) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password!"));

        if (!student.getIsVerified()) {
            throw new RuntimeException("Please verify your email first!");
        }
        if (!passwordEncoder.matches(password, student.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password!");
        }

        return student;
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Crewly - Email Verification OTP");
        message.setText("Your OTP for Crewly is: " + otp + "\n\nThis OTP is valid for 10 minutes.");
        mailSender.send(message);
    }
}