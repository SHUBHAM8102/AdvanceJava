package com.passport;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;

import jakarta.persistence.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PassportDaoTest {

    private PassportDao dao;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    void setUp() {
        dao = new PassportDao();
        emf = Persistence.createEntityManagerFactory("mysql");
        em = emf.createEntityManager();
    }

    @AfterEach
    void cleanUp() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery("delete from Passport").executeUpdate();
        et.commit();
    }

    @AfterAll
    void close() {
        em.close();
        emf.close();
        dao.close();
    }

    @Test
    void testAddPassport() {

        Passport p = new Passport();
        p.setPassportno("IND1001");
        p.setName("Nani");
        p.setGender("Male");
        p.setAddress("Kavali");
        p.setNo_of_travellers(2);

        dao.addPassport(p);

        Passport saved = dao.findByPassportNo("IND1001");

        assertNotNull(saved);
        assertEquals("Nani", saved.getName());
        assertEquals("Kavali", saved.getAddress());
    }

    @Test
    void testFindByPassportNo() {

        Passport p = new Passport();
        p.setPassportno("IND2001");
        p.setName("Test");
        dao.addPassport(p);

        Passport found = dao.findByPassportNo("IND2001");

        assertNotNull(found);
        assertEquals("Test", found.getName());
    }

    @Test
    void testFindByPassportNoWhenNotExists() {
        Passport found = dao.findByPassportNo("XXX999");
        assertNull(found);
    }

    @Test
    void testUpdateAddress() {

        Passport p = new Passport();
        p.setPassportno("IND3001");
        p.setName("Old");
        p.setAddress("Old Address");
        dao.addPassport(p);

        dao.updateAddress("IND3001", "New Address");

        Passport updated = dao.findByPassportNo("IND3001");

        assertEquals("New Address", updated.getAddress());
    }

    // ✅ DELETE
    @Test
    void testDeletePassport() {

        Passport p = new Passport();
        p.setPassportno("IND4001");
        p.setName("Delete");
        dao.addPassport(p);

        dao.deletePassport("IND4001");

        Passport deleted = dao.findByPassportNo("IND4001");

        assertNull(deleted);
    }


    @Test
    void testFindAll() {

        Passport p1 = new Passport();
        p1.setPassportno("IND5001");
        dao.addPassport(p1);

        Passport p2 = new Passport();
        p2.setPassportno("IND5002");
        dao.addPassport(p2);

        List<Passport> list = dao.findAll();

        assertTrue(list.size() >= 2);
    }
}

