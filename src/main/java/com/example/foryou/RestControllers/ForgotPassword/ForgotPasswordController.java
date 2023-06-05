package com.example.foryou.RestControllers.ForgotPassword;

import com.example.foryou.DAO.Entities.User;
import com.example.foryou.Services.Classes.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ForgotPasswordController {

    JavaMailSender mailSender;

     UserService userService;



//    @GetMapping("/forgot_password")
//    public String showForgotPasswordForm() {
//        return "forgot_password_form";
//    }

    @PostMapping("/forgot_password/{email}")
    public String processForgotPassword(HttpServletRequest request, Model model,@PathVariable String email) {
        //String email = request.getParameter("email");
        String token = RandomString.make(30);
        System.out.printf(email);
        try {
            System.out.println("test");
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println("sssss");

            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (MessagingException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot_password_form";
    }



    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        System.out.println("test");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(recipientEmail);

        String subject = "Subject : Forgot Password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }




//    @GetMapping("/reset_password")
//    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
//        User user = userService.getByResetPasswordToken(token);
//        model.addAttribute("token", token);
//
//        if (user == null) {
//            model.addAttribute("message", "Invalid Token");
//            return "message";
//        }
//
//        return "reset_password_form";
//    }

    @PostMapping("/reset_password/{token}/{password}")
    public String processResetPassword(HttpServletRequest request, Model model,@PathVariable String token,@PathVariable String password) {
//        String token = request.getParameter("token");
//        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        System.out.println(user.getEmail());
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }
}
