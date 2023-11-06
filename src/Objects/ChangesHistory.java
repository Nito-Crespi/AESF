package Objects;

import java.util.Calendar;

import Utils.Formats;

/**
 *
 * @author Nito.Crespi
 */
public class ChangesHistory {
   
    private int id; // id BIGINT IDENTITY PRIMARY KEY,
    private int idUser; // id_users BIGINT NOT NULL,
    private int idBranch; // id_branches BIGINT NOT NULL,
    private String nameTable; // name_table VARCHAR(255) NOT NULL,
    private String nameColumn; // name_column VARCHAR(255) NOT NULL,
    private int idObject;
    private String crudMethod;
    private String previousValue; // previous_value VARCHAR(255) NOT NULL,
    private String nextValue; // next_value VARCHAR(255) NOT NULL,
    private Calendar dateStart; // date_start TIMESTAMP NOT NULL

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("User ID: " + idUser);
        System.out.println("Branch ID: " + idBranch);
        System.out.println("Table Name: " + nameTable);
        System.out.println("Column Name: " + nameColumn);
        System.out.println("ID Object: " + idObject);
        System.out.println("CRUD Method: " + crudMethod);
        System.out.println("Previous Value: " + previousValue);
        System.out.println("Next Value: " + nextValue);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
    }

    public void nullValues() {
        this.id = -1;
        this.idUser = -1;
        this.idBranch = -1;
        this.nameTable = "null";
        this.nameColumn = "null";
        this.idObject = -1;
        this.crudMethod = "null";
        this.previousValue = "null";
        this.nextValue = "null";
        this.dateStart = new Formats().getNullCalendar();
    }

    public ChangesHistory() {
        
    }

    public ChangesHistory(int id, int idUser, int idBranch, String nameTable, String nameColumn, int idObject,
        String crudMethod, String previousValue, String nextValue, Calendar dateStart) {
        this.id = id;
        this.idUser = idUser;
        this.idBranch = idBranch;
        this.nameTable = nameTable;
        this.nameColumn = nameColumn;
        this.idObject = idObject;
        this.crudMethod = crudMethod;
        this.previousValue = previousValue;
        this.nextValue = nextValue;
        this.dateStart = dateStart;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public int getIdBranch() {
        return idBranch;
    }
    public void setIdBranch(int idBranch) {
        this.idBranch = idBranch;
    }
    public String getNameTable() {
        return nameTable;
    }
    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }
    public String getNameColumn() {
        return nameColumn;
    }
    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }
    public void setIdObject(int idObject) {
        this.idObject = idObject;
    }
    public int getIdObject() {
        return idObject;
    }
    public void setCrudMethod(String crudMethod) {
        this.crudMethod = crudMethod;
    }
    public String getCrudMethod() {
        return crudMethod;
    }
    public String getPreviousValue() {
        return previousValue;
    }
    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }
    public String getNextValue() {
        return nextValue;
    }
    public void setNextValue(String nextValue) {
        this.nextValue = nextValue;
    }
    public Calendar getDateStart() {
        return dateStart;
    }
    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }
    
}
