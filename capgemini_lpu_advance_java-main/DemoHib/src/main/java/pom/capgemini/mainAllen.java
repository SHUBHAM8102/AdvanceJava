package pom.capgemini;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class mainAllen {

    public static void main(String[] args) {

        // Create Objects
        Allen a1 = new Allen();
        a1.setAid(101);
        a1.setAname("Shashwat");
        a1.setColor("Green");

        Allen a2 = new Allen();
        a2.setAid(102);
        a2.setAname("Abhishek");
        a2.setColor("Red");

        // Load Configuration
        Configuration con = new Configuration()
                .configure()
                .addAnnotatedClass(Allen.class);

        SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();

        // 🔥 INSERT DATA
        Transaction tx1 = session.beginTransaction();

        session.save(a1);
        session.save(a2);

        tx1.commit();
        System.out.println("Records Inserted Successfully!");

        // 🔥 FETCH SINGLE RECORD
        Allen fetched = session.get(Allen.class, 101);

        if (fetched != null) {
            System.out.println("\nSingle Record Fetch:");
            System.out.println(fetched.getAid() + " "
                    + fetched.getAname() + " "
                    + fetched.getColor());
        }

        // 🔥 UPDATE RECORD
        Transaction tx2 = session.beginTransaction();

        Allen updateObj = session.get(Allen.class, 101);

        if (updateObj != null) {
            updateObj.setColor("Blue");
            updateObj.setAname("Shashwat Updated");
            System.out.println("\nRecord Updated Successfully!");
        }

        tx2.commit();

        // 🔥 DELETE RECORD (Delete aid = 102)
        Transaction tx3 = session.beginTransaction();

        Allen deleteObj = session.get(Allen.class, 102);

        if (deleteObj != null) {
            session.delete(deleteObj);
            System.out.println("\nRecord Deleted Successfully!");
        }

        tx3.commit();

        // 🔥 FETCH ALL RECORDS AFTER DELETE
        System.out.println("\nAll Records After Delete:");

        List<Allen> list = session
                .createQuery("from Allen", Allen.class)
                .list();

        for (Allen a : list) {
            System.out.println(a.getAid() + " "
                    + a.getAname() + " "
                    + a.getColor());
        }

        session.close();
        sf.close();
    }
}
