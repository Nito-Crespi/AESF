package Objects;

import java.util.Calendar;

import Utils.Formats;

/**
 *
 * @author Nito.Crespi
 */
public class User {
   
    private int id; // id BIGINT IDENTITY PRIMARY KEY,
    private String nickname; // nickname VARCHAR(50) UNIQUE NOT NULL,
    private String password; // password VARCHAR(50) NOT NULL,
    private String role; // role VARCHAR(50) NOT NULL,
    private String state; // state VARCHAR(50) NOT NULL,
    private Calendar dateStart; // date_start TIMESTAMP NOT NULL

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Nickname: " + nickname);
        System.out.println("Password: " + password);
        System.out.println("Role: " + role);
        System.out.println("State: " + state);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
    }

    public void nullValues() {
        this.id = -1;
        this.nickname = "null";
        this.password = "null";
        this.role = "null";
        this.state = "null";
        this.dateStart = new Formats().getNullCalendar();
    }

    public User() {
        
    }

    public User(int id, String nickname, String password, String role, String state, Calendar dateStart) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.state = state;
        this.dateStart = dateStart;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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
