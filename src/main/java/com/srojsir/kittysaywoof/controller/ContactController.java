package com.srojsir.kittysaywoof.controller;

import com.srojsir.kittysaywoof.model.ContactMessage;
import com.srojsir.kittysaywoof.repository.ContactRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${app.mail.to:}")
    private String mailTo;

    @GetMapping("/contact")
    public String contactForm(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/submitContact")
    public String submitContact(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("message") String message,
                                Model model) {

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name);
        contactMessage.setEmail(email);
        contactMessage.setMessage(message);
        contactRepository.save(contactMessage);

        sendMailIfConfigured(email,
                "New Contact Message from: " + name,
                "Email: " + email + "\n\nMessage:\n" + message,
                model,
                "Email sending failed.");

        return "redirect:/contact?success";
    }

    @PostMapping("/checkoutform")
    public String checkoutEmail(@RequestParam("cartData") String cartData,
                                @RequestParam("total") String total,
                                Model model) {
        sendMailIfConfigured("noreply@kittysaywoof.local",
                "New Order Received",
                "Customer purchased:\n\n" + cartData + "\n\nTotal: " + total,
                model,
                "Failed to send order email.");

        return "redirect:/cart?success";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/products")
    public String products() {
        return "product_page";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    private void sendMailIfConfigured(String from,
                                      String subject,
                                      String body,
                                      Model model,
                                      String errorMessage) {
        if (!mailEnabled || mailTo == null || mailTo.isBlank()) {
            return;
        }

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(from);
            helper.setTo(mailTo);
            helper.setSubject(subject);
            helper.setText(body);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            model.addAttribute("error", errorMessage);
        }
    }
}
