package Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import DataBase.IAgenda;
//import DataBase.U_IAgenda_IPaymentHistory;
import Objects.Agenda;
import Objects.Unifiers.UAgendaPH;

/**
 * Clase que contiene métodos relacionados con la gestión de agendas y
 * historiales de pagos.
 * 
 * @author Nito.Crespi
 */
public class AgendaPHService {

    private List<UAgendaPH> list = new DAO.UAgendaPHDAO(false).read();

    /**
     * Obtiene el ID de la primera agenda asociada a un grupo.
     *
     * @param idGroup el ID del grupo
     * @return el ID de la primera agenda asociada al grupo
     */
    public int getFirstAgendaByIdGroup(int idGroup) {
        for (UAgendaPH agenda : list) {
            // Comparamos el ID de grupo de cada agenda con el ID del grupo proporcionado
            if (agenda.getIdGroup() == idGroup) {
                return agenda.getIdAgenda(); // Devolvemos el ID de la primera agenda encontrada para el grupo
            }
        }
        return -1; // No se encontró ninguna agenda para el grupo
    }

    /**
     * Obtiene el ID de la última agenda asociada a un grupo.
     *
     * @param idGroup el ID del grupo
     * @return el ID de la última agenda del grupo
     */
    public int getLastAgendaByIdGroup(int idGroup) {
        int lastAgendaId = -1; // Valor inicial para indicar que no se encontró ninguna agenda
        for (UAgendaPH agenda : list) {
            // Comparamos el ID de grupo de cada agenda con el ID del grupo proporcionado
            if (agenda.getIdGroup() == idGroup) {
                lastAgendaId = agenda.getIdAgenda(); // Actualizamos el ID de la última agenda encontrada
            }
        }
        return lastAgendaId; // Devolvemos el ID de la última agenda del grupo
    }


    /**
     * Obtiene el ID del último historial de pagos asociado a un grupo.
     *
     * @param idGroup el ID del grupo
     * @return el ID del último historial de pagos del grupo
     */
    public int getLastPaymentHistory(int idGroup) {
        // Valor inicial para indicar que no se encontró ningún historial de pagos
        int lastPaymentHistoryId = -1;

        for (UAgendaPH agenda : list) {
            // Comparamos el ID de grupo de cada agenda con el ID del grupo proporcionado
            if (agenda.getIdGroup() == idGroup) {
                // Actualizamos el ID del último historial de pagos encontrado
                lastPaymentHistoryId = agenda.getIdPaymentHistory();
            }
        }
        return lastPaymentHistoryId; // Devolvemos el ID del último historial de pagos del grupo
    }


    /**
     * Obtiene el siguiente ID de grupo disponible.
     *
     * @return el siguiente ID de grupo
     */
    public int getNextIdGroup() {
        int nextId = 0;
        for (UAgendaPH agenda : list) {
            // Comparamos el ID de grupo de cada agenda con el ID actualmente más alto
            if (agenda.getIdGroup() > nextId) {
                nextId = agenda.getIdGroup(); // Actualizamos el ID más alto encontrado
            }
        }
        return nextId + 1; // Devolvemos el siguiente ID de grupo disponible sumando 1 al ID más alto encontrado
    }


    /**
     * Obtiene el primer historial de pagos de un grupo según su ID de grupo.
     *
     * @param idGroup el ID del grupo
     * @return el ID del primer historial de pagos del grupo
     */
    public int getFirstPaymentHistory(int idGroup) {
        for (UAgendaPH agenda : list) {
            // Verificamos si el ID de grupo de la agenda coincide con el ID de grupo proporcionado
            if (agenda.getIdGroup() == idGroup) {
                return agenda.getIdPaymentHistory(); // Devolvemos el ID del historial de pagos de la agenda actual
            }
        }
        
        return -1; // No se encontró ningún historial de pagos para el grupo
    }

    /**
     * Obtiene el número de una agenda dentro de un grupo según su ID de grupo.
     *
     * @param idAgenda el ID de la agenda
     * @return el número de la agenda dentro del grupo
     */
    public int getNumberAgendaByIdGroup(int idAgenda) {
        int number = -1; // Inicializamos el número en -1
        int previousValue = -1; // Valor previo de la agenda
        
        // Iteramos sobre la lista de agendas
        for (UAgendaPH agenda : list) {
            // Verificamos si el ID de grupo de la agenda coincide con el ID de grupo de la agenda proporcionado
            if (agenda.getIdGroup() != getIdGroupByIdAgenda(idAgenda)) {
                continue;
            }
            
            // Verificamos si el valor previo coincide con el ID de la agenda actual, evitando contar duplicados
            if (previousValue == agenda.getIdAgenda()) {
                continue;
            }
            
            previousValue = agenda.getIdAgenda(); // Actualizamos el valor previo con el ID de la agenda actual
            number++; // Incrementamos el número de la agenda
            
            // Verificamos si el ID de la agenda coincide con el ID de agenda proporcionado
            if (agenda.getIdAgenda() == idAgenda) {
                break; // Si coincide, terminamos el bucle
            }
        }
        
        // Si el número sigue siendo -1, se devuelve -1, de lo contrario se incrementa en 1 y se devuelve
        return number == -1 ? number : ++number;
    }

    /**
     * Obtiene el número de historiales de pagos asociados a un grupo según su ID.
     *
     * @param idGroup el ID del grupo
     * @return el número de historiales de pagos asociados al grupo
     */
    public int getCountPaymentHistoryByIdGroup(int idGroup) {
        // Creamos un conjunto para almacenar los valores únicos de ID de historiales de pagos
        Set<Integer> uniqueValues = new HashSet<>();
        
        // Iteramos sobre la lista de agendas
        for (UAgendaPH agenda : list) {
            // Verificamos si el ID de grupo de la agenda coincide con el ID de grupo proporcionado
            if (agenda.getIdGroup() == idGroup) {
                // Agregamos el ID del historial de pagos al conjunto de valores únicos
                uniqueValues.add(agenda.getIdPaymentHistory());
            }
        }
        
        // Devolvemos el tamaño del conjunto, que representa el número de historiales de pagos asociados al grupo
        return uniqueValues.size();
    }

    /**
     * Obtiene el número de agendas asociadas a un grupo según su ID.
     *
     * @param idGroup el ID del grupo
     * @return el número de agendas asociadas al grupo
     */
    public int getCountAgendaByIdGroup(int idGroup) {
        // Creamos un conjunto para almacenar los valores únicos de ID de agendas
        Set<Integer> uniqueValues = new HashSet<>();
        
        // Iteramos sobre la lista de agendas
        for (UAgendaPH agenda : list) {
            // Verificamos si el ID de grupo de la agenda coincide con el ID de grupo proporcionado
            if (agenda.getIdGroup() == idGroup) {
                // Agregamos el ID de la agenda al conjunto de valores únicos
                uniqueValues.add(agenda.getIdAgenda());
            }
        }
        
        // Devolvemos el tamaño del conjunto, que representa el número de agendas asociadas al grupo
        return uniqueValues.size();
    }

    /**
     * Obtiene el ID del grupo asociado a un historial de pagos según su ID.
     *
     * @param idPaymentHistory el ID del historial de pagos
     * @return el ID del grupo asociado al historial de pagos, o -1 si no se encuentra ningún grupo para el historial de pagos
     */
    public int getIdGroupByIdPaymentHistory(int idPaymentHistory) {
        // Iterar sobre la lista de agendas
        for (UAgendaPH agenda : list) {
            // Verificar si el ID del historial de pagos coincide
            if (agenda.getIdPaymentHistory() == idPaymentHistory) {
                // Devolver el ID del grupo asociado al historial de pagos
                return agenda.getIdGroup();
            }
        }
        // No se encontró ningún grupo para el historial de pagos, devolver -1
        return -1;
    }

    /**
     * Obtiene el ID del grupo asociado a una agenda según su ID.
     *
     * @param idAgenda el ID de la agenda
     * @return el ID del grupo asociado a la agenda, o -1 si no se encuentra ningún grupo para la agenda
     */
    public int getIdGroupByIdAgenda(int idAgenda) {
        // Iterar sobre la lista de agendas
        for (UAgendaPH agenda : list) {
            // Verificar si el ID de la agenda coincide
            if (agenda.getIdAgenda() == idAgenda) {
                // Devolver el ID del grupo asociado a la agenda
                return agenda.getIdGroup();
            }
        }
        // No se encontró ningún grupo para la agenda, devolver -1
        return -1;
    }

}
