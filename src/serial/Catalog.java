package serial;

import model.Bike;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Objekt-Katalog
 * Dient der Persistierung von Objekten.
 */
public class Catalog
{
    private List<Serializable> objects;
    private static Catalog instance;

    /**
     * Konstruktor.
     */
    private Catalog()
    {
        this.objects = new LinkedList<>();
    }

    public static Catalog getInstance()
    {
        if (instance == null)
        {
            instance = new Catalog();
        }
        return instance;
    }

    /**
     * Persistieren aller bislang geSaveTer Objekte.
     * ... inklusive aller Beziehungen zwischen ihnen.
     */
    public void persist()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("catalog.ser")))
        {
            oos.writeObject(objects);
            System.out.println("Database saved successfully!");
        }
        catch (IOException e)
        {
            // kann nicht auftreten
            e.printStackTrace();
        }
    }

    /**
     * Wiederherstellen aller zuletzt persistierter Objekte.
     * ... inlkusive aller Beziehungen zwischen ihnen.
     */
    public void restore()
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("catalog.ser")))
        {
            objects = (List<Serializable>) ois.readObject();
            System.out.println("Database restored successfully!");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Catalog contains class description of unknown class! Nothing to restore!");
        }
        catch (IOException e)
        {
            // kann nicht auftreten
            e.printStackTrace();
        }
    }

    /**
     * Alle Subscription-Objekte selektieren.
     * @return Liste aller Subscriptions
     */
    public Bike selectBikeByFrameNr(String frameNr)
    {
        Bike bike;

        for (Object object:objects)
        {
            bike = (Bike) object;

            if (bike.getFrameNr().equals(frameNr))
            {
                System.out.printf("Bike with frameNr: %s found!%n", frameNr);

                return bike;
            }
        }
        System.out.printf("There is no Bike with frameNr: %s stored!%n", frameNr);

        return null;
    }

    /**
     * Speichert ein Objekt im Katalog.
     * Existiert bereits ein gleiches (!) im Catalog, so wird das existierende zuerst entfernt.
     * @param serializable zu speicherndes Objekt
     */
    public void save(Serializable serializable)
    {
        objects.remove(serializable);
        objects.add(serializable);
    }

    @Override
    public String toString()
    {
        return "Catalog{" + "serObjects=" + objects + '}';
    }
}