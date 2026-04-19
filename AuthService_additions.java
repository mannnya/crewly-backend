// ══════════════════════════════════════════════════════════════════════════════
// ADD THESE TWO METHODS TO YOUR EXISTING AuthService.java
// (Do NOT replace the whole file — just paste these methods inside the class)
// ══════════════════════════════════════════════════════════════════════════════

    // ── Forgot Password: generates a new OTP and emails it ──
    public String forgotPassword(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No account found with that email."));

        // Generate a 6-digit OTP (reuse your existing OTP logic)
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        // Store it the same way you store registration OTPs
        // If you use a separate OtpStore / Redis / DB table, use the same approach:
        otpStore.put(email, otp);  // replace with your actual OTP storage call

        // Send email (reuse your existing email sender)
        emailService.sendOtp(email, otp);  // replace with your actual email call

        return "Reset OTP sent to " + email;
    }

    // ── Reset Password: verifies OTP, updates password ──
    public String resetPassword(String email, String otp, String newPassword) {
        String stored = otpStore.get(email);  // replace with your actual OTP lookup
        if (stored == null || !stored.equals(otp)) {
            throw new RuntimeException("Invalid or expired OTP.");
        }

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found."));

        // Hash the new password the same way you do during registration
        student.setPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);

        otpStore.remove(email);  // clean up OTP after use

        return "Password reset successfully!";
    }

// ══════════════════════════════════════════════════════════════════════════════
// NOTE: Replace otpStore / emailService / passwordEncoder / studentRepository
// with whatever variable names you already have in your AuthService.
// The logic is identical to your registration flow — you're just reusing it.
// ══════════════════════════════════════════════════════════════════════════════
