//here we have to focus on these point 
/*
 Which pattern would you use?
How will the controller choose the notification implementation?
How can Spring Boot help here?
Which SOLID principle is improved?
 */

class NotificationService {

    public void send(String type) {

        if(type.equals("EMAIL"))
            sendEmail();

        if(type.equals("SMS"))
            sendSMS();

        if(type.equals("PUSH"))
            sendPush();
    }
}
