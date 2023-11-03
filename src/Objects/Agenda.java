package Objects;

import java.io.Serializable;
import java.util.Calendar;

import Utils.Formats;

/**
 * Clase que representa una agenda de clases.
 * Una agenda contiene información sobre la clase, el usuario, el instructor, el pago y el automóvil asociados a ella.
 * También contiene información sobre el estado y las fechas de inicio y fin de la clase.
 * 
 * @author Nito.Crespi
 */
public class Agenda implements Serializable {

    private int id;
    private int idUser;
    private int idPeople;
    private int idInstructor;
    private int idCar;
    private String nameClass;
    private int modified;
    private String state;
    private Calendar dateStart;
    private Calendar dateEnd;
    
    private String comment;

    private User user;
    private People people;
    private People instructor;
    private Car car;

    public void showData() {
        System.out.println("ID: " + id);
        System.out.println("User ID: " + idUser);
        System.out.println("People ID: " + idPeople);
        System.out.println("Instructor ID: " + idInstructor);
        System.out.println("Car ID: " + idCar);
        System.out.println("Class Name: " + nameClass);
        System.out.println("Modified: " + modified);
        System.out.println("State: " + state);
        System.out.println("Date Start: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateStart));
        System.out.println("Date End: " + new Formats().getCalendarToString("HH:mm - dd:MM:yyyy", dateEnd));
        System.out.println("Comment: " + comment);
    }

    /**
     * Inicializa los campos de la agenda con valores nulos o predeterminados.
     */
    public void nullValues() {
        this.id = -1;
        this.idUser = -1;
        this.idPeople = -1;
        this.idInstructor = -1;
        this.idCar = -1;
        this.nameClass = "null";
        this.modified = -1;
        this.state = "null";
        this.dateStart = new Formats().getNullCalendar();
        this.dateEnd = new Formats().getNullCalendar();
    }

    /**
     * Constructor sin parámetros de la clase Agenda.
     * Inicializa una nueva instancia de Agenda con valores predeterminados.
     */
    public Agenda() {

    }

    /**
     * Constructor de la clase Agenda con parámetros.
     * @param id         Identificador de la agenda.
     * @param idUser     Identificador del usuario asociado a la clase.
     * @param idPeople   Identificador de la persona asociada a la clase.
     * @param idInstructor Identificador del instructor asociado a la clase.
     * @param idCar      Identificador del automóvil asociado a la clase.
     * @param nameClass  Nombre de la clase.
     * @param modified   Valor de modificación de la clase.
     * @param state      Estado de la clase.
     * @param dateStart  Fecha de inicio de la clase.
     * @param dateEnd    Fecha de fin de la clase.
     */
    public Agenda(int id, int idUser, int idPeople, int idInstructor, int idCar, String nameClass, int modified, String state,
                  Calendar dateStart, Calendar dateEnd) {
        this.id = id;
        this.idUser = idUser;
        this.idPeople = idPeople;
        this.idInstructor = idInstructor;
        this.idCar = idCar;
        this.nameClass = nameClass;
        this.modified = modified;
        this.state = state;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Obtiene el identificador de la agenda.
     * @return El identificador de la agenda.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la agenda.
     * @param id El identificador de la agenda.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del usuario asociado a la clase.
     * @return El identificador del usuario asociado a la clase.
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Establece el identificador del usuario asociado a la clase.
     * @param idUser El identificador del usuario asociado a la clase.
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Obtiene el identificador de la persona asociada a la clase.
     * @return El identificador de la persona asociada a la clase.
     */
    public int getIdPeople() {
        return idPeople;
    }

    /**
     * Establece el identificador de la persona asociada a la clase.
     * @param idPeople El identificador de la persona asociada a la clase.
     */
    public void setIdPeople(int idPeople) {
        this.idPeople = idPeople;
    }

    /**
     * Obtiene el identificador del instructor asociado a la clase.
     * @return El identificador del instructor asociado a la clase.
     */
    public int getIdInstructor() {
        return idInstructor;
    }

    /**
     * Establece el identificador del instructor asociado a la clase.
     * @param idInstructor El identificador del instructor asociado a la clase.
     */
    public void setIdInstructor(int idInstructor) {
        this.idInstructor = idInstructor;
    }

    /**
     * Obtiene el identificador del automóvil asociado a la clase.
     * @return El identificador del automóvil asociado a la clase.
     */
    public int getIdCar() {
        return idCar;
    }

    /**
     * Establece el identificador del automóvil asociado a la clase.
     * @param idCar El identificador del automóvil asociado a la clase.
     */
    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    /**
     * Obtiene el nombre de la clase.
     * @return El nombre de la clase.
     */
    public String getNameClass() {
        return nameClass;
    }

    /**
     * Establece el nombre de la clase.
     * @param nameClass El nombre de la clase.
     */
    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    /**
     * Obtiene el valor de modificación de la clase.
     * @return El valor de modificación de la clase.
     */
    public int getModified() {
        return modified;
    }

    /**
     * Establece el valor de modificación de la clase.
     * @param modified El valor de modificación de la clase.
     */
    public void setModified(int modified) {
        this.modified = modified;
    }

    /**
     * Obtiene el estado de la clase.
     * @return El estado de la clase.
     */
    public String getState() {
        return state;
    }

    /**
     * Establece el estado de la clase.
     * @param state El estado de la clase.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Obtiene la fecha de inicio de la clase.
     * @return La fecha de inicio de la clase.
     */
    public Calendar getDateStart() {
        return dateStart;
    }

    /**
     * Establece la fecha de inicio de la clase.
     * @param dateStart La fecha de inicio de la clase.
     */
    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * Obtiene la fecha de fin de la clase.
     * @return La fecha de fin de la clase.
     */
    public Calendar getDateEnd() {
        return dateEnd;
    }

    /**
     * Establece la fecha de fin de la clase.
     * @param dateEnd La fecha de fin de la clase.
     */
    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * Obtiene el usuario asociado a la clase.
     * @return El usuario asociado a la clase.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario asociado a la clase.
     * @param user El usuario asociado a la clase.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene la persona asociada a la clase.
     * @return La persona asociada a la clase.
     */
    public People getPeople() {
        return people;
    }

    /**
     * Establece la persona asociada a la clase.
     * @param people La persona asociada a la clase.
     */
    public void setPeople(People people) {
        this.people = people;
    }

    /**
     * Obtiene el instructor asociado a la clase.
     * @return El instructor asociado a la clase.
     */
    public People getInstructor() {
        return instructor;
    }

    /**
     * Establece el instructor asociado a la clase.
     * @param instructor El instructor asociado a la clase.
     */
    public void setInstructor(People instructor) {
        this.instructor = instructor;
    }

    /**
     * Obtiene el historial de pagos asociado a la clase.
     * @return El historial de pagos asociado a la clase.
     */
    /*public PaymentHistory getPaymentHistory() {
        return paymentHistory;
    }*/

    /**
     * Establece el historial de pagos asociado a la clase.
     * @param paymentHistory El historial de pagos asociado a la clase.
     */
    /*public void setPaymentHistory(PaymentHistory paymentHistory) {
        this.paymentHistory = paymentHistory;
    }*/

    /**
     * Obtiene el automóvil asociado a la clase.
     * @return El automóvil asociado a la clase.
     */
    public Car getCar() {
        return car;
    }

    /**
     * Establece el automóvil asociado a la clase.
     * @param car El automóvil asociado a la clase.
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Obtiene el comentario asociado a la clase.
     * @return El comentario asociado a la clase.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Establece el comentario asociado a la clase.
     * @param comment El comentario asociado a la clase.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
