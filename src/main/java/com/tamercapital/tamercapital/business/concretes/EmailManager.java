package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.EmailServiceBusiness;
import com.tamercapital.tamercapital.business.abstracts.UserService;
import com.tamercapital.tamercapital.core.adapters.EmailService;
import com.tamercapital.tamercapital.core.utilities.ErrorResult;
import com.tamercapital.tamercapital.core.utilities.Result;
import com.tamercapital.tamercapital.core.utilities.SuccessResult;
import com.tamercapital.tamercapital.model.concretes.ConfirmationEmailToken;
import com.tamercapital.tamercapital.model.concretes.User;
import com.tamercapital.tamercapital.repository.ConfirmationEmailTokenRepository;
import com.tamercapital.tamercapital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailManager implements EmailServiceBusiness {
    @Autowired
    private ConfirmationEmailTokenRepository confirmationEmailTokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private JavaMailSender mailSender;

    @Autowired
    public EmailManager(JavaMailSender mailSender) {
        super();
        this.mailSender = mailSender;
    }

    @Override
    public Result sendEmail(User user, String email) {
        ConfirmationEmailToken confirmationEmailToken = new ConfirmationEmailToken(user);
        confirmationEmailTokenRepository.save(confirmationEmailToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Konu");
        mailMessage.setFrom(email);
        mailMessage.setText("Aktivasyon Linki : "
                + "http://localhost:8080/api/Users/confirm-account?token=" + confirmationEmailToken.getConfirmationToken());

        this.emailService.sendEmail(mailMessage);

        return new SuccessResult("Email Göderildi : " + user.getEmail());
    }

    @Override
    public boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @Override
    public Result confirmUserAccount(String confirmationToken) {
        ConfirmationEmailToken emailToken = this.confirmationEmailTokenRepository.findByConfirmationToken(confirmationToken);

        if (emailToken != null) {
            User user = this.userService.findByEmailIgnoreCase(emailToken.getUser().getEmail()).getData();
            user.setIsStatus(true);
            this.userRepository.save(user);
            return new SuccessResult("Aktif Edildi");
        }
        return new ErrorResult("Aktif Edilemedi");

    }
}
