package Objects;

import java.util.Calendar;

import Utils.Formats;

/**
 *
 * @author Nito.Crespi
 */
public class Branch {

    private int id; // id BIGINT IDENTITY PRIMARY KEY,
    private String name; // name VARCHAR(50),
    private String country; // country VARCHAR(50),
    private String province; // province VARCHAR(50),
    private String city; // city VARCHAR(50),
    private String address; // address VARCHAR(50) UNIQUE NOT NULL,
    private String cellphone; // cellphone VARCHAR(50),
    private String phone; // phone VARCHAR(50),
    private String state; // state VARCHAR(50) NOT NULL,
    private Calendar dateStart; // date_start TIMESTAMP NOT NULL

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Country: " + country);
        System.out.println("Province: " + province);
        System.out.println("City: " + city);
        System.out.println("Address: " + address);
        System.out.println("Cellphone: " + cellphone);
        System.out.println("Phone: " + phone);
        System.out.println("State: " + state);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
    }
    
    public void nullValues() {
        this.id = -1;
        this.name = "null";
        this.country =  "null";
        this.province =  "null";
        this.city =  "null";
        this.address =  "null";
        this.cellphone =  "null";
        this.phone =  "null";
        this.state =  "null";
        this.dateStart =  new Formats().getNullCalendar();
    }

    public Branch() {
        
    }

    public Branch(int id, String name, String country, String province, String city, String address, String cellphone,
            String phone, String state, Calendar dateStart) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.province = province;
        this.city = city;
        this.address = address;
        this.cellphone = cellphone;
        this.phone = phone;
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
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCellphone() {
        return cellphone;
    }
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
