package Files;

import java.io.*;
import java.util.ArrayList;

import Objects.PaymentHistory;
import Utils.Path;

public class PaymentHistoryFileManager {

    private static final String fileName = new Path().getPath(
        new String[] {"Resources", "Files", "Registry"}, "PaymentHistory.bin");

    public void add(PaymentHistory paymentHistory) {
        ArrayList<PaymentHistory> paymentHistories = load();
        if (paymentHistories == null) {
            paymentHistories = new ArrayList<>();
        }
        paymentHistories.add(paymentHistory);
        save(paymentHistories);
    }

    public void save(ArrayList<PaymentHistory> paymentHistories) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(paymentHistories);
        } catch (IOException e) {
            // e.printStackTrace();
            // TODO: 
        }
    }

    public ArrayList<PaymentHistory> load() {
        ArrayList<PaymentHistory> paymentHistories = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)) ) {
            paymentHistories = (ArrayList<PaymentHistory>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
            // TODO: 
        }
        return paymentHistories;
    }
}
