package Files;

import java.io.*;
import java.util.ArrayList;

import Objects.Agenda;
import Utils.Path;

public class AgendaFileManager {

    private static final String fileName = new Path().getPath(
        new String[] {"Resources", "Files", "Registry"}, "Agendas.bin");

    public void add(AgendaCache agenda) {
        ArrayList<AgendaCache> agendas = load();
        if (agendas == null) {
            agendas = new ArrayList<AgendaCache>();
        }
        agendas.add(agenda);
        save(agendas);
    }

    public void save(ArrayList<AgendaCache> agendas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(agendas);
        } catch (IOException e) {
            // e.printStackTrace();
            // TODO: 
        }
    }

    public ArrayList<AgendaCache> load() {
        ArrayList<AgendaCache> agendas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)) ) {
            agendas = (ArrayList<AgendaCache>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
            // TODO: 
            agendas = null;
        }
        return agendas;
    }
}
