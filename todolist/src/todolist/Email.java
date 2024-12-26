package todolist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Email {
    private String to, from, host;
    
    public Email(String to) {
        this.to = to;
        this.from = "cartwoyouofficial@gmail.com";
        this.host = "smtp.gmail.com";
    }

    public void setTo(String to){
        this.to = to;
    }

    public String getTo() {
        return this.to;
    }

    public String getFrom(){
        return this.from;
    }

    public String getHost(){
        return this.host;
    }
    public void send (ResultSet rs) throws SQLException {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", this.host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cartwoyouofficial@gmail.com", "ekpybxtrllffsixj");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(this.from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.to));
            message.setSubject("List of task that is going to due in 24 hours");

            String text = "List of task that is going to due in 24 hours:\n";
            String name, dueDate;
            int i = 1;
            while (rs.next()){
                name = rs.getString("task_name");
                dueDate = rs.getString("task_due_date");
                text += Integer.toString(i) + ". " + name +" is going to due on " + dueDate +". \n";
            }

            message.setText(text);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        String to = "nicolhengsiyi@gmail.com";
        Email test = new Email(to);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() { 
                String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                try (Connection conn = Database.getConnection();
                    var stmt = conn.prepareStatement("SELECT task_name, task_due_date FROM tasks WHERE task_due_date = ? ORDER BY task_due_date ASC;"); ) {
                    stmt.setString(1, currentDate);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.isBeforeFirst()){
                        test.send(rs);
                    } 
                } catch (SQLException e) {
                    System.out.println("Error occurs: " + e.getMessage());
                }
            }
        }, 1, 1, TimeUnit.MINUTES);

        
        
        
    }
}
