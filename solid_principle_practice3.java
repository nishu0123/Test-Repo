
//this is the given code here we have to indentify 
/*
 Which SOLID principle is violated?
How would you redesign it?
Which design pattern would you use?
Tomorrow if the business asks for:
Apple Pay
Google Pay
what code changes are required?
*/
class PaymentService {

    public void pay(String type, double amount) {

        if(type.equals("UPI")) {
            // UPI logic
        }
        else if(type.equals("CARD")) {
            // Card logic
        }
        else if(type.equals("PAYPAL")) {
            // Paypal logic
        }
    }
}
