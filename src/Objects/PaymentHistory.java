package Objects;

import java.util.Calendar;

import Utils.Formats;

public class PaymentHistory {
    
    private int id; //id BIGINT IDENTITY PRIMARY KEY,
    private String paymentMethod; // payment_method VARCHAR(50) NOT NULL,
    private int price; // price INT NOT NULL,
    private String state; // state VARCHAR(50) NOT NULL,
    private int idUser; // id_users BIGINT NOT NULL,
    private int idPeople; // id_peoples BIGINT NOT NULL,
    private int modified; // modified INT NOT NULL,
    private Calendar dateStart; // date_start TIMESTAMP NOT NULL,
    private Calendar dateEnd; // date_end TIMESTAMP NOT NULL

    private People people;
    private User user;

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Price: " + price);
        System.out.println("State: " + state);
        System.out.println("User ID: " + idUser);
        System.out.println("People ID: " + idPeople);
        System.out.println("Modified: " + modified);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
        System.out.println("Date End: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateEnd));
    }
    
    public void nullValues() {
        this.id = -1;
        this.paymentMethod = "null";
        this.price = -1;
        this.state = "null";
        this.idUser = -1;
        this.idPeople = -1;
        this.modified = -1;
        this.dateStart = new Formats().getNullCalendar();
        this.dateEnd = new Formats().getNullCalendar();
    }

    public PaymentHistory() {
        
    }

    public PaymentHistory(int id, String paymentMethod, int price, String state, int idUser, int idPeople, int modified,
            Calendar dateStart, Calendar dateEnd) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.state = state;
        this.idUser = idUser;
        this.idPeople = idPeople;
        this.modified = modified;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public int getIdPeople() {
        return idPeople;
    }
    public void setIdPeople(int idPeople) {
        this.idPeople = idPeople;
    }
    public int getModified() {
        return modified;
    }
    public void setModified(int modified) {
        this.modified = modified;
    }
    public Calendar getDateStart() {
        return dateStart;
    }
    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }
    public Calendar getDateEnd() {
        return dateEnd;
    }
    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }
    public People getPeople() {
        return people;
    }
    public void setPeople(People people) {
        this.people = people;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
