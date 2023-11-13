package Objects.Unifiers;

public class UAgendaPH {
    
    private int id;
    private int idGroup;
    private int idAgenda;
    private int idPaymentHistory;

    public void showData() {
        System.out.println("UAgendaPH Data:");
        System.out.println("ID: " + id);
        System.out.println("ID Group: " + idGroup);
        System.out.println("ID Agenda: " + idAgenda);
        System.out.println("ID Payment History: " + idPaymentHistory);
        System.out.println();
    }

    public void nullValues() {
        this.id = -1;
        this.idGroup = -1;
        this.idAgenda = -1;
        this.idPaymentHistory = -1;
    }

    public UAgendaPH() {

    }
    
    public UAgendaPH(int id, int idGroup, int idAgenda, int idPaymentHistory) {
        this.id = id;
        this.idGroup = idGroup;
        this.idAgenda = idAgenda;
        this.idPaymentHistory = idPaymentHistory;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdGroup() {
        return idGroup;
    }
    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }
    public int getIdAgenda() {
        return idAgenda;
    }
    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }
    public int getIdPaymentHistory() {
        return idPaymentHistory;
    }
    public void setIdPaymentHistory(int idPaymentHistory) {
        this.idPaymentHistory = idPaymentHistory;
    }

}
