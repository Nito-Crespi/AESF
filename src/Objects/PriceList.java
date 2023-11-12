package Objects;

import java.io.Serializable;
import java.util.Calendar;

import Utils.Formats;

/**
 *
 * @author Nito.Crespi
 */
public class PriceList implements Serializable {
   
    private int id; // id BIGINT IDENTITY PRIMARY KEY,
    private String name; // name VARCHAR(50) UNIQUE NOT NULL,
    private int price; // price INT NOT NULL,
    private int numberOfClasses; // number_of_classes INT NOT NULL, 
    private String state; // state VARCHAR(50) NOT NULL, 
    private Calendar dateStart; // date_start TIMESTAMP NOT NULL

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Number of Classes: " + numberOfClasses);
        System.out.println("State: " + state);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
    }
    
    public void nullValues() {
        this.id = -1;
        this.name = "null";
        this.price = -1;
        this.numberOfClasses = -1;
        this.state = "null";
        this.dateStart = new Formats().getNullCalendar();
    }

    public PriceList() {
        
    }

    public PriceList(int id, String name, int price, int numberOfClasses, String state, Calendar dateStart) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberOfClasses = numberOfClasses;
        this.state = state;
        this.dateStart = dateStart;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getNumberOfClasses() {
        return numberOfClasses;
    }
    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Calendar getDateStart() {
        return dateStart;
    }
    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }
    
}
