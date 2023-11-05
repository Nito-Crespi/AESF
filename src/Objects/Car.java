package Objects;

import java.util.Calendar;

import Utils.Formats;

/**
 *
 * @author Nito.Crespi
 */
public class Car {

    private int id; // id BIGINT IDENTITY PRIMARY KEY,
    private String brand; // brand VARCHAR(50) NOT NULL,
    private String model; // model VARCHAR(50) NOT NULL,
    private String patent; // patent VARCHAR(50) UNIQUE NOT NULL,
    private String color; // color VARCHAR(50),
    private String state; // state VARCHAR(50) NOT NULL,
    private Calendar dateStart; // date_start TIMESTAMP NOT NULL
    
    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Patent: " + patent);
        System.out.println("Color: " + color);
        System.out.println("State: " + state);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
    }
    
    public void nullValues() {
        this.id = -1;
        this.brand = "null";
        this.model = "null";
        this.patent = "null";
        this.color = "null";
        this.state = "null";
        this.dateStart = new Formats().getNullCalendar();
    }

    public Car() {
        
    }

    public Car(int id, String brand, String model, String patent, String color, String state, Calendar dateStart) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.patent = patent;
        this.color = color;
        this.state = state;
        this.dateStart = dateStart;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getPatent() {
        return patent;
    }
    public void setPatent(String patent) {
        this.patent = patent;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
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
