package Objects;

public class PaymentMethod {
    
    private int id; // id BIGINT /* UNSIGNED AUTO_INCREMENT */ IDENTITY PRIMARY KEY,
    private String name; // name VARCHAR(255) NOT NULL,
    private String state; // state VARCHAR(255) NOT NULL

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("State: " + state);
    }

    public void nullValues() {
        this.id = -1;
        this.name = "null";
        this.state = "null";
    }

    public PaymentMethod() {
        
    }
    
    public PaymentMethod(int id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
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
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

}
