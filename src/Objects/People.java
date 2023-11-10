package Objects;

import java.io.Serializable;
import java.util.Calendar;

import Utils.Formats;

/**
 *
 * @author Nito.Crespi
 */
public class People implements Serializable {
   
    private int id; // id BIGINT IDENTITY PRIMARY KEY,
    private String name; // name VARCHAR(50) NOT NULL,
    private String surname; // surname VARCHAR(50) NOT NULL,
    private String dni; // dni VARCHAR(50) UNIQUE NOT NULL,
    private String type; // type VARCHAR(50) NOT NULL,
    private String state; // state VARCHAR(50) NOT NULL,
    private String country; // country VARCHAR(50),
    private String province; // province VARCHAR(50),
    private String city; // city VARCHAR(50),
    private String address; // address VARCHAR(50),
    private Calendar birthday; // birthday TIMESTAMP,
    private String email; // email VARCHAR(50),
    private String cellphone; // cellphone VARCHAR(50),
    private String phone; // phone VARCHAR(50),
    private Calendar dateStart; // date_start TIMESTAMP NOT NULL

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("DNI: " + dni);
        System.out.println("Type: " + type);
        System.out.println("State: " + state);
        System.out.println("Country: " + country);
        System.out.println("Province: " + province);
        System.out.println("City: " + city);
        System.out.println("Address: " + address);
        System.out.println("Birthday: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", birthday));
        System.out.println("Email: " + email);
        System.out.println("Cellphone: " + cellphone);
        System.out.println("Phone: " + phone);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
    }

    public void nullValues() {
        this.id = -1;
        this.name = "null";
        this.surname = "null";
        this.dni = "null";
        this.type = "null";
        this.state = "null";
        this.country = "null";
        this.province = "null";
        this.city = "null";
        this.address = "null";
        this.birthday = new Formats().getNullCalendar();
        this.email = "null";
        this.cellphone = "null";
        this.phone = "null";
        this.dateStart = new Formats().getNullCalendar();
    }

    public People() {
        
    }
    
    public People(int id, String name, String surname, String dni, String type, String state, String country,
            String province, String city, String address, Calendar birthday, String email, String cellphone,
            String phone, Calendar dateStart) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.type = type;
        this.state = state;
        this.country = country;
        this.province = province;
        this.city = city;
        this.address = address;
        this.birthday = birthday;
        this.email = email;
        this.cellphone = cellphone;
        this.phone = phone;
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
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
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
    public Calendar getBirthday() {
        return birthday;
    }
    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public Calendar getDateStart() {
        return dateStart;
    }
    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

}
