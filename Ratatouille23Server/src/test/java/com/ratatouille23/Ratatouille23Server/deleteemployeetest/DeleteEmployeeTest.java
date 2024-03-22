package com.ratatouille23.Ratatouille23Server.deleteemployeetest;

import com.ratatouille23.Ratatouille23Server.model.Employee;
import com.ratatouille23.Ratatouille23Server.model.Store;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;
import com.ratatouille23.Ratatouille23Server.updateemployeetest.DeleteEmployeeMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


/**
 * Approccio Black-Box per la valutazione di input-output misto a White-Box dato che si sfruttano input
 * appositamente pensati per valutare il corretto comportamento del metodo e la sua logica interna.
 * Lo scopo in sostanza è valutare che i requisiti logici del metodo siano rispettati:
 *      - se l'utente esiste in database può essere eliminato, altrimenti no
 *      - se un amministratore sta per essere eliminato
 *          - se non è l'unico amministratore dello store allora può essere eliminato
 *          - se è l'unico amministratore dello store
 *              - se lo store non ha altri dipendenti può essere eliminato, anzi viene proprio richiamato il wipe dei dati di tutta l'attività
 *              - se lo store ha altri dipendenti (anche non amministratori) allora non può essere eliminato (altrimenti non esisterebbero altri amministratori)
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
 *      - TC1: si prova ad eliminare chef1 (ci aspettiamo che vada tutto tranquillamente)
 *      - TC2: si prova ad eliminare admin3 (ci aspettiamo che avvenga il wipe dei dati di store2)
 *      - TC3: si prova a eliminare admin1 (ci aspettiamo che venga eliminato normalmente)
 *      - TC4: si prova a eliminare admin1 e poi admin2 (ci aspettiamo che generi IllegalStateException dato che admin2 non puà essere
 *          eliminato senza prima eliminare anche chef1)
 *      - TC5: si prova a eliminare un utente che non esiste
 */
public class DeleteEmployeeTest {
    private DeleteEmployeeMock mock;
    private Employee admin1, admin2, admin3, chef1; //Impiegati esistenti nel database mockato
    private Store store1, store2; //Store esistenti nel database mockato

    @Before
    public void setUp(){
        mock = new DeleteEmployeeMock();

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

    //Elimina chef1
    @Test
    public void TC1(){
        mock.deleteEmployee(chef1.getId());

        Assert.assertFalse(mock.getAllEmployees().contains(chef1));
    }

    //Elimina admin3
    @Test
    public void TC2(){
        mock.deleteEmployee(admin3.getId());

        Assertions.assertAll(
                () -> Assert.assertFalse(mock.getAllEmployees().contains(admin3)),
                () -> Assert.assertTrue(mock.getEmployeesOfStore(store2.getId()).size() == 0),
                () -> Assert.assertThrows(NullPointerException.class, () -> mock.findStoreById(store2.getId())) //Solo nei test per come sono stati mockati i metodi di appoggio
        );
    }

    //Elimina admin1
    @Test
    public void TC3(){
        mock.deleteEmployee(admin1.getId());

        Assert.assertFalse(mock.getAllEmployees().contains(admin1));
    }

    //Elimina sia admin1 che admin2
    @Test
    public void TC4(){
        mock.deleteEmployee(admin1.getId());
        Assert.assertFalse(mock.getAllEmployees().contains(admin1));

        Exception exception = Assert.assertThrows(IllegalStateException.class, () -> mock.deleteEmployee(admin2.getId()));
        System.out.println("TC4 genera eccezione: " + exception.getMessage()); //Giusto per loggare
    }

    //Elimina un utente che non esiste
    @Test
    public void TC5(){
        Employee e = new Employee();
        e.setId(-1l);
        e.setName("Not");
        e.setSurname("Found");
        e.setEmail("notfound@404.com");
        e.setRole(Role.WAITER);

        Exception exception = Assert.assertThrows(IllegalStateException.class, () -> mock.deleteEmployee(e.getId()));
        System.out.println("TC5 genera eccezione: " + exception.getMessage());
    }
}
