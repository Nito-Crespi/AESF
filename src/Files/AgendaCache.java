package Files;

import java.io.Serializable;
import java.util.Calendar;

import Objects.People;
import Objects.PriceList;

public class AgendaCache implements Serializable {
    
    private Calendar calendar;
    private People people;
    private PriceList priceList;
    
    public AgendaCache() {

    }

    public AgendaCache(Calendar calendar, People people, PriceList priceList) {
        this.calendar = calendar;
        this.people = people;
        this.priceList = priceList;
    }

    public Calendar getCalendar() {
        return calendar;
    }
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
    public People getPeople() {
        return people;
    }
    public void setPeople(People people) {
        this.people = people;
    }
    public PriceList getPriceList() {
        return priceList;
    }
    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }



}
