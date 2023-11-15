package Files;

import java.io.*;
import java.util.ArrayList;

import Objects.People;
import Utils.Path;

public class PeopleFileManager {

    private static final String fileName = new Path().getPath(
        new String[] {"Resources", "Files", "Registry"}, "People.bin");

    public void add(People people) {
        ArrayList<People> peoples = load();
        if (peoples == null) {
            peoples = new ArrayList<>();
        }
        peoples.add(people);
        save(peoples);
    }

    public void save(ArrayList<People> peoples) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(peoples);
        } catch (IOException e) {
            // e.printStackTrace();
            // TODO: 
        }
    }

    public ArrayList<People> load() {
        ArrayList<People> peoples = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)) ) {
            peoples = (ArrayList<People>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
            // TODO: 
        }
        return peoples;
    }
}
