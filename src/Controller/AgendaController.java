package Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Files.AgendaCache;
import Files.AgendaFileManager;
import Objects.Agenda;
import Objects.PaymentHistory;
import Objects.People;
import Objects.PriceList;
import Objects.Unifiers.UAgendaPH;
import Resources.StateConstants;
import Service.AgendaService;
import Utils.Formats;
import Utils.PopUp;

/**
 *
 * @author nito
 */
public class AgendaController {

    private AgendaService agendaService;

    public AgendaController() {
        this.agendaService = new AgendaService();
    }

    public boolean newAgenda(Calendar c, People p, PriceList pl) {
        if (p == null) {
            new PopUp().ErrorMessage(
                "ERROR",
                "Debe seleccionar una persona."
            );
            return false;
        }
        if (pl == null) {
            new PopUp().ErrorMessage(
                "ERROR",
                "Debe seleccionar una clase."
            );
            return false;
        }

        int hour = c.get(Calendar.HOUR_OF_DAY);

        if (!(hour >= 7 && hour <= 23)) {
            new PopUp().ErrorMessage(
                "ERROR",
                "Debe seleccionar una hora entre las 7hs y las 23hs"
            );
            return false;
        }

        ArrayList<Agenda> agendas = new DAO.AgendaDAO(false).getAgendasDay(c);
        int carsAvailables = new DAO.CarDAO(false).getCarsAvailables().size();

        if (!(agendas == null || agendas.size() < carsAvailables)) {
            new PopUp().ErrorMessage(
                "ERROR",
                "Ya existe una clase en este horario o la cantidad de clases en esta hora ya es igual a la cantidad de autos disponibles"
            );
            return false;
        }

        ArrayList<UAgendaPH> uAgendaPHs = new DAO.UAgendaPHDAO(false).getUAgendaPHByNameClassAndIdPeople(pl.getName(), p.getId());
        Map<Integer, List<UAgendaPH>> mapGroup = new HashMap<>();

        for (UAgendaPH uaph : uAgendaPHs) {
            int idGroup = uaph.getIdGroup();
            if (!mapGroup.containsKey(idGroup)) {
                mapGroup.put(idGroup, new ArrayList<>());
            }
            mapGroup.get(idGroup).add(uaph);
        }

        boolean addInGroup = false;
        int idGroup = -1;
        int lastPaymentHistoryId = -1;

        for (Map.Entry<Integer, List<UAgendaPH>> entry : mapGroup.entrySet()) {
            int idg = entry.getKey();
            List<UAgendaPH> uaph = entry.getValue();
            if (uaph.size() < pl.getNumberOfClasses()) {
                addInGroup = true;
                idGroup = idg;
                for (UAgendaPH suaph : uaph) {
                    lastPaymentHistoryId = suaph.getIdPaymentHistory();
                }
                break;
            }
        }

        new StateConstants();

        Agenda agenda = new Agenda(
            0,
            1,
            p.getId(),
            1,
            1,
            pl.getName(),
            0,
            StateConstants.PENDIENTE,
            c,
            new Formats().getNullCalendar()
        );

        PaymentHistory paymentHistory = new PaymentHistory(
            0,
            StateConstants.UNKNOWN,
            pl.getPrice(),
            StateConstants.PENDIENTE,
            1,
            p.getId(),
            0,
            Calendar.getInstance(),
            new Formats().getNullCalendar()
        );

        int nextGroup = new DAO.UAgendaPHDAO(false).nextGroup() + 1;
        int countAgendas = new DAO.AgendaDAO(false).countAll();
        int newIdAgenda = new DAO.AgendaDAO(false).read().get(countAgendas-1).getId() + 1;
        int countPaymentHistory = new DAO.PaymentHistoryDAO(false).countAll();
        int newIdPaymentHistory = new DAO.PaymentHistoryDAO(false).read().get(countPaymentHistory-1).getId()+1;

        UAgendaPH uAgendaPH = new UAgendaPH(
            0,
            (addInGroup ? idGroup : nextGroup),
            (countAgendas > 0 ? newIdAgenda : 1),
            (addInGroup ? lastPaymentHistoryId : newIdPaymentHistory)
        );

        boolean success = agendaService.save(agenda, addInGroup ? null : paymentHistory, uAgendaPH);

        if (success) {
            new PopUp().ShowMessage("Aviso", "Se ha añadido la clase a su agenda.");
        } else {
            new PopUp().ErrorMessage("ERROR", "No se ha podido añadir la clase a su agenda.");
            new AgendaFileManager().add(new AgendaCache(c, p, pl));
            new PopUp().ShowMessage("Aviso", "La agenda se ha guardado en 'Configuración > Agendas pendientes'.");
        }

        return success;
    }
    
}
