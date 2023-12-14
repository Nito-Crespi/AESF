package Service;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import DAO.AgendaDAO;
import DAO.PaymentHistoryDAO;
import DAO.UAgendaPHDAO;
import Files.AgendaFileManager;
import Files.PaymentHistoryFileManager;
import Objects.*;
import Objects.Unifiers.UAgendaPH;
import Resources.*;
import Utils.*;

public class AgendaService {

    private AgendaDAO agendaDAO;

    public AgendaService() {
        this.agendaDAO = new AgendaDAO(false);
    }

    public boolean save(Agenda agenda, PaymentHistory paymentHistory, UAgendaPH uAgendaPH) {
        // return (new UAgendaPHDAO(false).insertGroup(agenda, paymentHistory,
        // uAgendaPH));
        // TODO: test
        new AgendaDAO(false).insert(agenda);
        if (paymentHistory != null) {
            new PaymentHistoryDAO(false).insert(paymentHistory);
        }
        new UAgendaPHDAO(false).insert(uAgendaPH);
        return true;
    }

    public void forceUpdateState() {
        ArrayList<Agenda> agendas = new DAO.AgendaDAO(false).getAgendasByState(StateConstants.PENDIENTE);
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < agendas.size(); i++) {
            if (c.after(agendas.get(i).getDateStart())) {
                forceUpdateState(agendas.get(i));
            }
        }
    }

    private void forceUpdateState(Agenda agenda) {
        new StateConstants();

        String[] options = new String[] { StateConstants.PENDIENTE, StateConstants.REALIZADA,
                StateConstants.CANCELADA };

        JLabel label1 = new JLabel("La fecha y hora de esta clase son anteriores a la actual");
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(" ");

        // Agenda agenda = new DAO.AgendaDAO(false).read(false,idAgenda);

        JLabel label2 = new JLabel(String.join(
                " ",
                "Persona:",
                agenda.getPeople().getName(),
                agenda.getPeople().getSurname(),
                "|",
                agenda.getPeople().getDni()));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label3 = new JLabel(String.join(
                " ",
                "Fecha:",
                new Utils.Formats().getCalendarToString("HH:mm | dd-MM-yyyy", agenda.getDateStart())));
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JLabel "Seleccione método de pago"
        JLabel labelMetodoPago = new JLabel("Seleccione el nuevo estado para esta agenda:");
        labelMetodoPago.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JComboBox con los elementos
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(1);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JPanel con BoxLayout en el eje Y
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(label1);
        panel.add(Box.createVerticalGlue());
        panel.add(label);
        panel.add(Box.createVerticalGlue());
        panel.add(label2);
        panel.add(Box.createVerticalGlue());
        panel.add(label3);
        panel.add(Box.createVerticalGlue());
        panel.add(labelMetodoPago);
        panel.add(Box.createVerticalGlue());
        panel.add(comboBox);
        panel.add(Box.createVerticalGlue());

        int result = JOptionPane.showOptionDialog(null, panel, "Ingresar nuevo estado", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new String[] { "Aceptar", "Cancelar" }, "Aceptar");

        if (result == JOptionPane.OK_OPTION) {
            // Se presionó "Aceptar"
            // Agenda agenda = new IAgenda().load(idAgenda);

            agenda.setState(String.valueOf(comboBox.getSelectedItem()));

            agenda.setDateEnd(Calendar.getInstance());

            new DAO.AgendaDAO(false).update(agenda);
        } else {
            // Se presionó "Cancelar" o se cerró el diálogo
            // TODO:
        }

    }

    public void addComment(Agenda agenda) {

        // Crear los elementos del diálogo

        JTextArea txt = new JTextArea(10, 20);
        String comment = String.valueOf(agenda.getComment()).equals("null") ? "" : agenda.getComment() + "\n";
        txt.setText(comment);

        // Crear el panel con los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        txt.setAlignmentX(SwingConstants.CENTER);
        panel.add(txt);

        // Mostrar el diálogo
        int option = JOptionPane.showOptionDialog(null, panel, "Nuevo comentario",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                new String[] { "Aceptar", "Cancelar" }, null);
        if (option == JOptionPane.OK_OPTION) {
            // Se presionó el botón "Aceptar"

            // agenda.setInstructor(peoples.get(comboBox2.getSelectedIndex()));
            agenda.setComment(txt.getText());

            new DAO.AgendaDAO(false).update(agenda);

            new Utils.PopUp().ShowMessage("Aviso", "Se ha añadido un comentario para esta clase.");
        } else {
            new Utils.PopUp().ShowMessage("Aviso", "No se ha actualizado esta clase.");
        }
    }

    public void setInstructor(Agenda agenda) {
        ArrayList<People> peoples = new DAO.PeopleDAO(false).read();
        new Service.PeopleService().showInstructorPeople(peoples);

        ArrayList<String> peoplesArrayList = new ArrayList<String>();
        for (int i = 0; i < peoples.size(); i++) {
            peoplesArrayList.add(
                    peoples.get(i).getName() + " " + peoples.get(i).getSurname() + " | " + peoples.get(i).getDni());
        }

        // Crear los elementos del diálogo

        JLabel label2 = new JLabel("Seleccione el instructor:");
        label2.setHorizontalAlignment(SwingConstants.LEFT);
        String[] peoplesArray = peoplesArrayList.toArray(new String[0]);
        JComboBox<String> comboBox2 = new JComboBox<>(peoplesArray);

        // Crear el panel con los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label2);
        comboBox2.setAlignmentX(SwingConstants.CENTER);
        panel.add(comboBox2);

        // Mostrar el diálogo
        int option = JOptionPane.showOptionDialog(null, panel, "Instructor asignado",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                new String[] { "Aceptar", "Cancelar" }, null);

        // Procesar la opción seleccionada
        if (option == JOptionPane.OK_OPTION) {
            // Se presionó el botón "Aceptar"
            peoples.get(comboBox2.getSelectedIndex());

            // Agenda agenda = new DAO.AgendaDAO(false).read(false,idAgenda);

            // agenda.setInstructor(peoples.get(comboBox2.getSelectedIndex()));
            agenda.setIdInstructor(peoples.get(comboBox2.getSelectedIndex()).getId());

            new DAO.AgendaDAO(false).update(agenda);

            new Utils.PopUp().ShowMessage("Aviso", "Se ha actualizado el instructor de esta clase.");
        } else {
            new Utils.PopUp().ShowMessage("Aviso", "No se ha actualizado esta clase.");
        }
    }

    public void setCar(Agenda agenda) {
        ArrayList<Car> cars = new DAO.CarDAO(false).read();

        new Service.CarService().showActiveCar(cars);

        ArrayList<String> carsArrayList = new ArrayList<String>();
        for (int i = 0; i < cars.size(); i++) {
            carsArrayList
                    .add(cars.get(i).getBrand() + " | " + cars.get(i).getModel() + " | " + cars.get(i).getPatent());
        }

        // Crear los elementos del diálogo
        JLabel label1 = new JLabel("Seleccione el auto:");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        String[] carsArray = carsArrayList.toArray(new String[0]);
        JComboBox<String> comboBox1 = new JComboBox<>(carsArray);

        // Crear el panel con los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label1);
        comboBox1.setAlignmentX(SwingConstants.CENTER);
        panel.add(comboBox1);

        // Mostrar el diálogo
        int option = JOptionPane.showOptionDialog(null, panel, "Auto asignado",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                new String[] { "Aceptar", "Cancelar" }, null);

        // Procesar la opción seleccionada
        if (option == JOptionPane.OK_OPTION) {
            // Se presionó el botón "Aceptar"
            cars.get(comboBox1.getSelectedIndex());

            // Agenda agenda = new DAO.AgendaDAO(false).read(false,idAgenda);
            agenda.setIdCar(cars.get(comboBox1.getSelectedIndex()).getId());

            new DAO.AgendaDAO(false).update(agenda);

            new Utils.PopUp().ShowMessage("Aviso", "Se ha actualizado el auto de esta clase.");
        } else {
            new Utils.PopUp().ShowMessage("Aviso", "No se ha actualizado esta clase.");
        }
    }

    public void changeDate(Agenda agenda) {
        // Agenda agenda = new DAO.AgendaDAO(false).read(false,idAgenda);

        // Crear los SpinnerNumberModel con los rangos específicos
        SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 0, 24, 1);
        SpinnerNumberModel dayModel = new SpinnerNumberModel(1, 1, 31, 1);
        SpinnerNumberModel monthModel = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(2000, 2000, 2050, 1);

        // Crear los JSpinner con los SpinnerNumberModel
        JSpinner hourSpinner = new JSpinner(hourModel);
        hourSpinner.setValue(agenda.getDateStart().get(Calendar.HOUR_OF_DAY));
        JSpinner daySpinner = new JSpinner(dayModel);
        daySpinner.setValue(agenda.getDateStart().get(Calendar.DAY_OF_MONTH));
        JSpinner monthSpinner = new JSpinner(monthModel);
        monthSpinner.setValue(agenda.getDateStart().get(Calendar.MONTH) + 1);
        JSpinner yearSpinner = new JSpinner(yearModel);
        yearSpinner.setValue(agenda.getDateStart().get(Calendar.YEAR));

        // Crear el array de objetos a mostrar en el JOptionPane
        Object[] message = {
                "Hora:", hourSpinner,
                "Día:", daySpinner,
                "Mes:", monthSpinner,
                "Año:", yearSpinner
        };

        // Mostrar el JOptionPane con los JSpinner
        int option = JOptionPane.showConfirmDialog(null, message, "Seleccione una nueva fecha",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Obtener los valores seleccionados de los JSpinner
        if (option == JOptionPane.OK_OPTION) {
            int hour = (int) hourSpinner.getValue();
            int day = (int) daySpinner.getValue();
            int month = (int) monthSpinner.getValue();
            int year = (int) yearSpinner.getValue();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, hour, 0, 0);

            if (hour >= 0 && hour <= 23 && day >= 0 && day <= 31 && month >= 0 && month <= 12 && year >= 2023) {
                ArrayList<Agenda> agendas = new DAO.AgendaDAO(false).getAgendasDay(calendar);
                if (!(hour >= 7 && hour <= 23)) {
                    new PopUp().ErrorMessage("ERROR", "Debe seleccionar una hora entre las 7hs y las 23hs");
                } else if ((agendas == null || agendas.size() < new DAO.CarDAO(false).getCarsAvailables().size())) {

                    // calendar.set(year, month, day);
                    agenda.setDateStart(calendar);
                    new DAO.AgendaDAO(false).update(agenda);

                    new Utils.PopUp().ShowMessage("Aviso", "Se ha realizado un cambio de fecha de manera exitosa.");
                } else {
                    new PopUp().ErrorMessage("ERROR",
                            "Ya existe una clase en este horario o la cantidad de clases en esta hora ya es igual a la cantidad de autos disponibles");
                }
            }
        } else {
            new Utils.PopUp().ShowMessage("Aviso", "No se ha realizado ningún cambio.");
        }
    }

    /**
     * Verifica si un día y hora específicos están disponibles en las agendas
     * proporcionadas.
     *
     * @param agendas  la lista de agendas a verificar
     * @param calendar el objeto Calendar que representa el día y hora a verificar
     * @param hour     la hora a verificar
     * @return true si el día y hora están disponibles, false si están reservados o
     *         si hay un error
     */
    public boolean checkDay(ArrayList<Agenda> agendas, Calendar calendar, int hour) {
        boolean allCorrect = true;
        int count = 0;
        int cars = new DAO.CarDAO(false).countAll();

        // Iterar sobre la lista de agendas
        for (int i = 0; i < agendas.size(); i++) {
            // Verificar si el año es diferente
            if (!(calendar.get(Calendar.YEAR) == agendas.get(i).getDateStart().get(Calendar.YEAR))) {
                continue;
            }
            // Verificar si el mes es diferente
            if (!(calendar.get(Calendar.MONTH) == agendas.get(i).getDateStart().get(Calendar.MONTH))) {
                continue;
            }
            // Verificar si el día del mes es diferente
            if (!(calendar.get(Calendar.DAY_OF_MONTH) == agendas.get(i).getDateStart().get(Calendar.DAY_OF_MONTH))) {
                continue;
            }
            // Verificar si la hora es diferente
            if (!(hour == agendas.get(i).getDateStart().get(Calendar.HOUR_OF_DAY))) {
                continue;
            }

            count++;
            // Verificar si no hay suficientes autos disponibles o si se ha alcanzado el
            // límite de reservas
            if (cars <= 0 || count >= cars) {
                // Mostrar un mensaje de error indicando que la fecha y hora ya están reservadas
                new PopUp().ErrorMessage("ERROR", "La fecha y hora ingresada ya están reservados.");
                allCorrect = false;
            }
        }

        // Devolver el valor de allCorrect
        return allCorrect;
    }

    public void forceChangeState(Agenda agenda) {

        new StateConstants();
        // Crear un array de elementos para el JComboBox
        String[] options = new String[] { StateConstants.PENDIENTE, StateConstants.REALIZADA,
                StateConstants.CANCELADA };

        JLabel label1 = new JLabel("La fecha y hora de esta clase son anteriores a la actual");
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(" ");

        JLabel label2 = new JLabel(String.join(
                " ",
                "Persona:",
                agenda.getPeople().getName(),
                agenda.getPeople().getSurname(),
                "|",
                agenda.getPeople().getDni()));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label3 = new JLabel(String.join(
                " ",
                "Fecha:",
                new Utils.Formats().getCalendarToString("HH:mm | dd-MM-yyyy", agenda.getDateStart())));
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JLabel "Seleccione método de pago"
        JLabel labelMetodoPago = new JLabel("Seleccione el nuevo estado para esta agenda:");
        labelMetodoPago.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JComboBox con los elementos
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(1);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JPanel con BoxLayout en el eje Y
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(label1);
        panel.add(Box.createVerticalGlue());
        panel.add(label);
        panel.add(Box.createVerticalGlue());
        panel.add(label2);
        panel.add(Box.createVerticalGlue());
        panel.add(label3);
        panel.add(Box.createVerticalGlue());
        panel.add(labelMetodoPago);
        panel.add(Box.createVerticalGlue());
        panel.add(comboBox);
        panel.add(Box.createVerticalGlue());

        int result = JOptionPane.showOptionDialog(null, panel, "Ingresar nuevo estado", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new String[] { "Aceptar", "Cancelar" }, "Aceptar");

        if (result == JOptionPane.OK_OPTION) {
            // Se presionó "Aceptar"
            // Agenda agenda = new IAgenda().load(idAgenda);

            agenda.setState(String.valueOf(comboBox.getSelectedItem()));

            agenda.setDateEnd(Calendar.getInstance());

            new DAO.AgendaDAO(false).update(agenda);
        } else {
            // Se presionó "Cancelar" o se cerró el diálogo
            // TODO:
        }

    }

    // ArrayList<Agenda> agendas = new IAgenda().load();

    public void changeState(Agenda agenda) {

        new StateConstants();
        // Crear un array de elementos para el JComboBox
        String[] options = new String[] { StateConstants.PENDIENTE, StateConstants.REALIZADA,
                StateConstants.CANCELADA };

        // Crear el JLabel "Seleccione método de pago"
        JLabel labelMetodoPago = new JLabel("Seleccione el nuevo estado para esta agenda:");
        labelMetodoPago.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JComboBox con los elementos
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JPanel con BoxLayout en el eje Y
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(labelMetodoPago);
        panel.add(Box.createVerticalGlue());
        panel.add(comboBox);
        panel.add(Box.createVerticalGlue());

        int result = JOptionPane.showOptionDialog(null, panel, "Ingresar nuevo estado", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new String[] { "Aceptar", "Cancelar" }, "Aceptar");

        if (result == JOptionPane.OK_OPTION) {
            // Se presionó "Aceptar"
            // Agenda agenda = new DAO.AgendaDAO(false).read(false,idAgenda);

            agenda.setState(String.valueOf(comboBox.getSelectedItem()));

            agenda.setDateEnd(Calendar.getInstance());

            new DAO.AgendaDAO(false).update(agenda);
        } else {
            // Se presionó "Cancelar" o se cerró el diálogo
            // TODO:
        }

    }

}