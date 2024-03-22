package com.ratatouille23.Ratatouille23Server.updateemployeetest;

import com.ratatouille23.Ratatouille23Server.deleteemployeetest.UpdateEmployeeMock;
import com.ratatouille23.Ratatouille23Server.model.Employee;
import com.ratatouille23.Ratatouille23Server.model.Store;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Approccio Black-Box per la valutazione di input-output misto a White-Box dato che si sfruttano input
 * appositamente pensati per valutare il corretto comportamento del metodo e la sua logica interna.
 * Lo scopo in sostanza è valutare che i requisiti logici del metodo siano rispettati:
 *      - se l'utente esiste in database può essere modificato, altrimenti no
 *      - se un amministratore sta cambiando il proprio ruolo (perdendo così i privilegi di amministratore):
 *          - se esistono altri amministratori dello stesso store allora può farlo
 *          - se è l'unico amministratore di quello store allora viene lanciata una IllegalStateException che vieta il suo cambio di ruolo
 * Nel database mockato ci sono:
 *      - Store:
 *          - store1: ha due amministratori e uno chef
 *          - store2: ha un solo amministratore
 *      - Employee:
 *          - admin1: amministratore di store1
 *          - admin2: amministratore di store1
 *          - admin3: amministratore di store2
 *          - chef1: chef di store 1
 *
 * Possibili casi di interesse:
 *      - TC1: chef1 viene convertito in supervisore e gli vengono cambiati alcuni dati (ci attendiamo che tutto avvenga come descritto)
 *      - TC2: si prova a modificare admin1 convertendo il suo ruolo in chef (ci attendiamo che tutto funzioni dato che admin1 condivide il ruolo
 *              di amministratore con admin2 per lo store1 prima di essere modificato)
 *      - TC3: si prova a modificare admin3 senza convertire il suo ruolo (ci attendiamo che tutto vada come descritto dato che il ruolo non cambia)
 *      - TC4: si prova a modifcare admin3 convertendo il suo ruolo in supervisore (ci attendiamo che venga generata una IllegalStateException
 *              perchè admin3 è l'unico amministratore di store2 prima della modifica)
 *      - TC5: si prova a modificare un utente che non esiste nel database (ci attendiamo che venga generata una IllegalStateException)
 */
public class UpdateEmployeeTest {

    private UpdateEmployeeMock mock;
    private Employee admin1, admin2, admin3, chef1; //Impiegati esistenti nel database mockato
    private Store store1, store2; //Store esistenti nel database mockato

    @Before
    public void setUp(){
        mock = new UpdateEmployeeMock();

        long id = 0;

        store1 = new Store();
        store1.setId(0);
        store1.setName("Correct Store");

        store2 = new Store();
        store2.setId(1);
        store1.setName("Other Store");

        //Admin 1
        admin1 = new Employee();
        admin1.setId(++id);
        admin1.setEmail("admin1@gmail.com");
        admin1.setName("Admin");
        admin1.setSurname("One");
        admin1.setRole(Role.ADMINISTRATOR);
        admin1.setStore(store1);

        //Admin 2
        admin2 = new Employee();
        admin2.setId(++id);
        admin2.setEmail("admin2@gmail.com");
        admin2.setName("Admin");
        admin2.setSurname("Two");
        admin2.setRole(Role.ADMINISTRATOR);
        admin2.setStore(store1);

        //Chef 1
        chef1 = new Employee();
        chef1.setId(++id);
        chef1.setEmail("chef1@gmail.com");
        chef1.setName("Chef");
        chef1.setSurname("One");
        chef1.setRole(Role.CHEF);
        chef1.setStore(store1);

        //Admin of other store
        admin3 = new Employee();
        admin3.setId(++id);
        admin3.setEmail("otheradmin@gmail.com");
        admin3.setName("Other");
        admin3.setSurname("Admin");
        admin3.setRole(Role.ADMINISTRATOR);
        admin3.setStore(store2);
    }

    //Chef1 cambia ruolo e dati
    @Test
    public void TC1(){
        Role expectedRole = Role.SUPERVISOR;
        String expectedName = "Supervisor";
        String expectedEmail = "supervisor1@gmail.com";

        chef1.setRole(expectedRole);
        chef1.setEmail(expectedEmail);
        chef1.setName(expectedName);

        mock.updateEmployee(chef1.getId(), chef1);

        Employee expectedChef = mock.getEmployee(chef1);

        Assertions.assertAll(
                () -> Assert.assertEquals(expectedChef.getRole(), expectedRole),
                () -> Assert.assertEquals(expectedChef.getEmail(), expectedEmail),
                () -> Assert.assertEquals(expectedChef.getName(), expectedName)
        );
    }

    //Admin1 cambia ruolo e dati
    @Test
    public void TC2(){
        Role expectedRole = Role.CHEF;
        String expectedName = "Chef";
        String expectedEmail= "chef2@gmail.com";
        String expectedSurname = "Two";

        admin1.setRole(expectedRole);
        admin1.setEmail(expectedEmail);
        admin1.setName(expectedName);
        admin1.setSurname(expectedSurname);

        mock.updateEmployee(admin1.getId(), admin1);

        Employee expectedAdmin = mock.getEmployee(admin1);

        Assertions.assertAll(
                () -> Assert.assertEquals(expectedAdmin.getRole(), expectedRole),
                () -> Assert.assertEquals(expectedAdmin.getEmail(), expectedEmail),
                () -> Assert.assertEquals(expectedAdmin.getName(), expectedName),
                () -> Assert.assertEquals(expectedAdmin.getSurname(), expectedSurname)
        );
    }

    //Admin3 cambia dati ma rimane amministratore
    @Test
    public void TC3(){
        String expectedName = "Amministratore";
        String expectedEmail= "amministratore3@gmail.com";
        String expectedSurname = "Tre";

        admin3.setEmail(expectedEmail);
        admin3.setName(expectedName);
        admin3.setSurname(expectedSurname);

        mock.updateEmployee(admin3.getId(), admin3);

        Employee expectedAdmin = mock.getEmployee(admin3);

        Assertions.assertAll(
                () -> Assert.assertEquals(expectedAdmin.getEmail(), expectedEmail),
                () -> Assert.assertEquals(expectedAdmin.getName(), expectedName),
                () -> Assert.assertEquals(expectedAdmin.getSurname(), expectedSurname)
        );
    }

    //Admin3 cambia dati E cambia ruolo
    @Test
    public void TC4(){
        Role expectedRole = Role.SUPERVISOR;
        String expectedName = "Amministratore";
        String expectedEmail= "amministratore3@gmail.com";
        String expectedSurname = "Tre";

        admin3.setRole(expectedRole);
        admin3.setEmail(expectedEmail);
        admin3.setName(expectedName);
        admin3.setSurname(expectedSurname);

        Exception exception = Assert.assertThrows(IllegalStateException.class, () -> mock.updateEmployee(admin3.getId(), admin3));
        System.out.println("TC4 genera eccezione: " + exception.getMessage()); //Giusto per loggare
    }

    //Si prova a modifcare un utente che non esiste nel db
    @Test
    public void TC5(){
        Employee e = new Employee();
        e.setId(-1l);
        e.setEmail("notfound@gmail.com");
        e.setName("Nome");
        e.setSurname("Found");
        e.setRole(Role.ADMINISTRATOR);

        Exception exception = Assert.assertThrows(IllegalStateException.class, () -> mock.updateEmployee(e.getId(), e));
        System.out.println("TC5 genera eccezione: " + exception.getMessage()); //Giusto per loggare
    }
}
