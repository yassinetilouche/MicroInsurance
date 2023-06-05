package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.*;
import com.example.foryou.DAO.Repositories.ContractRepository;
import com.example.foryou.DAO.Repositories.NotificationRepository;
import com.example.foryou.DAO.Repositories.UserRepository;
import com.example.foryou.Services.Interfaces.IContractService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
@AllArgsConstructor
public class ContractService implements IContractService {
    private ContractRepository contractRepository;
    UserRepository userRepository;

    /////////////////////////Crud
    @Override
    public Contracts addContract(Contracts contract) {
        String username = this.getCurrentUserName();
        User user = this.getUser(username);
        contract.setUser(user);
        return contractRepository.save(contract);
    }

    @Override
    public List<Contracts> addAllContracts(List<Contracts> contractList) {
        return contractRepository.saveAll(contractList);
    }

    @Override
    public Contracts editContract(Contracts contract) {
        return contractRepository.save(contract);
    }

    @Override
    public void deleteContract(Contracts contract) {
        contractRepository.delete(contract);
    }

    @Override
    public void deleteContractById(int contractId) {
        contractRepository.deleteById(contractId);
    }

    @Override
    public void deleteAllContracts(List<Contracts> contractList) {
        contractRepository.deleteAll(contractList);
    }

    @Override
    public void deleteAllContracts() {
        contractRepository.deleteAll();
    }

    @Override
    public List<Contracts> selectAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contracts SelectContractById(int contractId) {
        return contractRepository.findById(contractId).get();
    }

    // ******************************** Filtrage des contracts par type de sinister
    @Override
    public List<Contracts> FilterContract(Type type) {
        return contractRepository.selectContractByType(type);
    }

    // ************** Affichage des contracts renouvelable dont la date d'expiration et la date d'aujourdhui
    @Override
    public List<Contracts> selectRenewableContract() {
        return contractRepository.selectRenewableContract();
    }

    // ********** Supprimer les contracts non renouvlable dont la date d'expiration et la date d'aujourdhui
    @Override
    public void deleteNonRenewableContract() {
        contractRepository.deleteNonRenewableContract();
    }

    // ********************************************************   Notifications
    private NotificationService notificationService;
    private NotificationRepository notificationRepository;

    // ********************************************************  Envoi du mail
    @Scheduled(cron = "0 0 8-9 * * ?")
    //@Scheduled(cron = "*/10 * * * * *") //pour le test

    public void verifierContrats() throws MessagingException, javax.mail.MessagingException {
        Notification notification = new Notification();
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Contracts> contrats = contractRepository.findByExprirationDate(date);
        for (Contracts contrat : contrats) {
            String message = "Cher(e) client(e),\n\n" +
                    " Je vous informe par le biais mail que votre contrat numéro " + contrat.getContract_id() +
                    " a expiré aujourd'hui. \n Son état de renouvellement : " + contrat.isRenewable() +"\n"+
                    "Nous restons à votre entière disposition pour toute information complémentaire,\n\n " +
                    "veuillez agréer, Monsieur,Madame, l'expression de nos salutations les plus distinguées";
            notification.setNotifDescription(message);
                User receiver = contrat.getUser();
                notificationService.sendEmail(receiver.getEmail(), "Contrat expiré", message);
            notification.setTypeNotif(TypeNotif.CONTRACT);
            notification.setNotifDate(date);
            notification.setReceiver(receiver);
            notificationRepository.save(notification);
        }
    }
    // ********************************************************  Generer un contrat pdf
    public File genererContratPDF(Contracts contract) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream("contrat.pdf"));
        document.open();
        // Logo
        Image logo = Image.getInstance("image/logo1.png");
        logo.scaleAbsolute(70f, 70f);
        logo.setAlignment(Element.ALIGN_RIGHT);
        document.add(logo);
        //Titre
        Paragraph title = new Paragraph("ForYou microInsurance\n\n\n", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("1.Parties au contrat : \n\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        document.add(new Paragraph("Assureur : ForYou microInsurance "));
        document.add(new Paragraph("Souscripteur : "+ contract.getUser().getLastName() +" "+contract.getUser().getFirstName()));
        document.add(new Paragraph("\n2.Objet du contrat : \n\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        document.add(new Paragraph("Type d'assurance :"+ contract.getUser().getInsurancetype()));
        document.add(new Paragraph("Montant de la garantie :"+ contract.getCeilingAmount()));
        document.add(new Paragraph("\n3.Exclusions de garanties :\n\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        document.add(new Paragraph("Catastrophes naturelles"));
        document.add(new Paragraph("Actes de terrorisme "));
        document.add(new Paragraph("Négligence du souscripteur"));
        document.add(new Paragraph("\n4.Durée du contrat :\n\n", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        document.add(new Paragraph("Ce contrat est valide du " + contract.getStartDate()+" Jusqu'au "+ contract.getExprirationDate()));
        // Adding footer with page number and date
        /*Paragraph footer = new Paragraph("Page " + writer.getPageNumber() + " | " + new Date().toString(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);*/
        Paragraph footer = new Paragraph("\n\n\n\n\n\n\n\n\n\n\nPage " + writer.getPageNumber() + " | " + new Date().toString() +"| \nNous vous rappelons que les informations communiquées dans ce contrat sont confidentielles et " +
                "réservées à l'usage exclusif de l'assureur et du souscripteur. " +
                "Conformément à la réglementation en vigueur, vous disposez d'un droit d'accès, de rectification et d'opposition pour" +
                " toute information vous concernant. Pour exercer ces droits, vous pouvez nous contacter à l'adresse suivante [Foryoumicroinsurance@gamil.com]." +
                " Ce contrat est régi par la loi française et tout litige en découlant sera soumis à la compétence des tribunaux tunisiens.", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        // Closing the document
        document.close();
        return null;
    }


    // ******************************************************** Envoyer contrat par mail

    public void envoyerContratParEmail(User user, Contracts  contract) throws javax.mail.MessagingException {
        /*String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String smtpUsername = "lina.hermessi9@gmail.com";
        String smtpPassword = "vxuyjkgyopoliymx";
        String sender = "lina.hermessi9@gmail.com";*/
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String smtpUsername = "foryou.microinsurance@gmail.com";
        String smtpPassword = "cqpdoedhcchseumd";
        String sender = "foryou.microinsurance@gmail.com";
        String subject = "Nouveau contrat";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject(subject);

        // Créer le contenu du message
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("cher(e) client(e), "+ user.getLastName()+" "+ user.getFirstName() + ",\n\nVous trouverez ci-joint votre nouveau contrat" +
                "d'assurance. \n Merci pour votre confiance\n\n - - - - - - - - - - - - - - - - - - - - - - \nVotra assurance ForYou micoInsurance");

        // Créer la pièce jointe PDF
        MimeBodyPart pdfAttachment = new MimeBodyPart();
        DataSource source = new FileDataSource("contrat.pdf");
        pdfAttachment.setDataHandler(new DataHandler(source));
        pdfAttachment.setFileName("contrat.pdf");

        // Ajouter la pièce jointe PDF au message
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(pdfAttachment);
        message.setContent(multipart);
        // Envoyer le message
        Transport.send(message);
    }
    @Override
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
    }

