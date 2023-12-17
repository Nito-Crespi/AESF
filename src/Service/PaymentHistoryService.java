package Service;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objects.Agenda;
//import DataBase.IAgenda;
//import DataBase.IPaymentHistory;
//import DataBase.IPaymentMethod;
//import DataBase.IPeople;
//import DataBase.U_IAgenda_IPaymentHistory;
import Objects.PaymentHistory;
import Objects.PaymentMethod;
import Objects.Unifiers.UAgendaPH;
import PDF.PDFCreator;
import Utils.Formats;

/**
 * Clase que contiene el método para obtener el método de pago.
 * 
 * @author Nito.Crespi
 */
public class PaymentHistoryService {
    
    /**
     * Muestra un diálogo para ingresar el método de pago y el monto.
     * 
     * @param idGroup el ID del grupo
     */
    public void newPayment(Agenda agenda) {
        // Cargar los métodos de pago desde la base de datos
        ArrayList<PaymentMethod> paymentMethods = new DAO.PaymentMethodDAO(false).read();

        // Crear una lista de nombres de métodos de pago para usar en el JComboBox
        ArrayList<String> paymentMethodsName = new ArrayList<String>();
        for (int i = 0; i < paymentMethods.size(); i++) {
            paymentMethodsName.add(paymentMethods.get(i).getName());
        }

        // Crear un array de elementos para el JComboBox
        String[] elementos = paymentMethodsName.toArray(new String[0]);

        // Crear el JLabel "Seleccione método de pago"
        JLabel labelMetodoPago = new JLabel("Seleccione método de pago:");
        labelMetodoPago.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JComboBox con los elementos
        JComboBox<String> comboBox = new JComboBox<>(elementos);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JLabel "Ingrese monto"
        JLabel labelMonto = new JLabel("Ingrese monto:");
        labelMonto.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JTextField para ingresar un número
        JTextField textField = new JTextField(10);
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                try {
                    Integer.parseInt(textField.getText());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear el JPanel con BoxLayout en el eje Y
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(labelMetodoPago);
        panel.add(Box.createVerticalGlue());
        panel.add(comboBox);
        panel.add(Box.createVerticalGlue());
        panel.add(labelMonto);
        panel.add(Box.createVerticalGlue());
        panel.add(textField);
        panel.add(Box.createVerticalGlue());

        // Mostrar el diálogo de ingreso de pago
        int result = JOptionPane.showOptionDialog(null, panel, "Ingresar nuevo pago", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Aceptar", "Cancelar"}, "Aceptar");

        int idGroup = new DAO.UAgendaPHDAO(false).getIdGroupByIdAgenda(agenda.getId());

        if (result == JOptionPane.OK_OPTION) {
            // Se presionó "Aceptar"
            if (textField.getText().length() >= 0 && new Formats().justNumber(textField.getText())) {
                // Insertar el nuevo registro de historial de pagos
                new DAO.PaymentHistoryDAO(false).save(new PaymentHistory(
                    -1,
                    String.valueOf(comboBox.getSelectedItem()),
                    Integer.valueOf(textField.getText()),
                    "Pagado",
                    1, // TODO: añadir el id del usuario
                    agenda.getPeople().getId(),
                    0,
                    Calendar.getInstance(),
                    new Formats().getNullCalendar()
                ));

                int idCountPaymentHistory = new DAO.PaymentHistoryDAO(false).countAll();
                int idPaymentHistory = new DAO.PaymentHistoryDAO(false).read().get(idCountPaymentHistory-1).getId();
                
                // Insertar la relación entre la agenda y el historial de pagos
                new DAO.UAgendaPHDAO(false).save(new UAgendaPH(
                    0,
                    idGroup,
                    new DAO.UAgendaPHDAO(false).getLastIdAgendaByIdGroup(idGroup),
                    idPaymentHistory
                ));
                
                // Crear el recibo de pago en formato PDF
                new PDFCreator().paymentInvoice(
                    agenda.getPeople().getName() + " " +
                    agenda.getPeople().getSurname(),
                    agenda.getPeople().getDni(),
                    Integer.valueOf(textField.getText()),
                    agenda.getNameClass(),
                    Calendar.getInstance()
                );
                
                // Mostrar mensaje de éxito
                new Utils.PopUp().ShowMessage("Aviso", "El pago se realizó correctamente. El recibo de pago está ubicado en el Escritorio");
            } else {
                // Mostrar mensaje de error si el monto ingresado no es válido
                new Utils.PopUp().ErrorMessage("ERROR", "Debe ingresar un monto solamente numérico.");
            }
        } else {
            // Se presionó "Cancelar" o se cerró el diálogo
            // TODO: Implementar acciones necesarias
        }
    }
}
