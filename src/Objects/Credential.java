package Objects;

/**
 * 
 * @author Nito.Crespi
 */
public class Credential {

    private int id; // id BIGINT IDENTITY PRIMARY KEY,
    private String username; // username VARCHAR(50) NOT NULL,
    private String osName; // osName VARCHAR(50) NOT NULL,
    private String macAddress; // macAddress VARCHAR(50) NOT NULL,
    private String osArch; // osArch VARCHAR(50) NOT NULL
    private int idBranch = 0;

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("OS Name: " + osName);
        System.out.println("MAC Address: " + macAddress);
        System.out.println("OS Architecture: " + osArch);
        System.out.println("ID Branch: " + idBranch);
    }

    public void nullValues() {
        this.id = -1;
        this.username = "null";
        this.osName = "null";
        this.macAddress = "null";
        this.osArch = "null";
        this.idBranch = -1;
    }

    public Credential() {
        
    }

    public Credential(int id, String username, String osName, String macAddress, String osArch, int idBranch) {
        this.id = id;
        this.username = username;
        this.osName = osName;
        this.macAddress = macAddress;
        this.osArch = osArch;
        this.idBranch = idBranch;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getOsName() {
        return osName;
    }
    public void setOsName(String osName) {
        this.osName = osName;
    }
    public String getMacAddress() {
        return macAddress;
    }
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public String getOsArch() {
        return osArch;
    }
    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }
    public int getIdBranch() {
        return idBranch;
    }
    public void setIdBranch(int idBranch) {
        this.idBranch = idBranch;
    }

}
