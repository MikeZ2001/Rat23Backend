package com.ratatouille23.Ratatouille23Server;

import com.ratatouille23.Ratatouille23Server.model.Employee;
import com.ratatouille23.Ratatouille23Server.model.Store;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDBMock {
    private List<Employee> employees;
    private Store store1, store2;

    public EmployeeDBMock() {
        populateDB();
    }

    private void populateDB() {
        employees = new ArrayList<>();

        long id = 0;

        store1 = new Store();
        store1.setId(0);
        store1.setName("Correct Store");

        store2 = new Store();
        store2.setId(1);
        store1.setName("Other Store");

        //Admin 1
        Employee e1 = new Employee();
        e1.setId(++id);
        e1.setEmail("admin1@gmail.com");
        e1.setName("Admin");
        e1.setSurname("One");
        e1.setRole(Role.ADMINISTRATOR);
        e1.setStore(store1);

        //Admin 2
        Employee e2 = new Employee();
        e2.setId(++id);
        e2.setEmail("admin2@gmail.com");
        e2.setName("Admin");
        e2.setSurname("Two");
        e2.setRole(Role.ADMINISTRATOR);
        e2.setStore(store1);

        //Chef 1
        Employee e3 = new Employee();
        e3.setId(++id);
        e3.setEmail("chef1@gmail.com");
        e3.setName("Chef");
        e3.setSurname("One");
        e3.setRole(Role.CHEF);
        e3.setStore(store1);

        //Admin of other store
        Employee e4 = new Employee();
        e4.setId(++id);
        e4.setEmail("otheradmin@gmail.com");
        e4.setName("Other");
        e4.setSurname("Admin");
        e4.setRole(Role.ADMINISTRATOR);
        e4.setStore(store2);

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
    }

    //Simula il findById della repository
    public Employee findById(long id){
        for (Employee e: employees){
            if (e.getId() == id)
                return e;
        }
        return null;
    }

    //Simula getAdminsOfStore della repository
    public List<Employee> getAdminsOfStore(long storeId){
        List<Employee> ret = null;
        for (Employee e: employees){
            if (e.getStore().getId() == storeId && e.getRole().equals(Role.ADMINISTRATOR)){
                if (ret == null){
                    ret = new ArrayList<>();
                }
                ret.add(e);
            }
        }
        return ret;
    }

    //Simula updateEmployee della repository
    public void updateEmployee(long id,String name,String surname,String email, Role role){
        Employee e = findById(id);
        e.setRole(role);
        e.setName(name);
        e.setSurname(surname);
        e.setEmail(email);
    }

    //Simula deleteById della repository
    public void deleteById(long id){
        for (Employee e: employees){
            if(e.getId() == id){
                employees.remove(e);
                break;
            }
        }
    }

    //Simula le varie chiamate di repository necessarie al wipe dei dati di uno store specifico
    public void wipeData(long storeId) {
        List<Employee> toRemove = new ArrayList<>();
        for (Employee e: employees){
            if (e.getStore().getId() == storeId)
                toRemove.add(e);
        }
        employees.removeAll(toRemove);

        if (store1.getId() == storeId)
            store1 = null;
        else
            store2 = null;
    }

    //Simula la stessa chiamata della repository
    public List<Employee> getEmployeesOfStore(long storeId){
        List<Employee> ret = new ArrayList<>();

        for (Employee e: employees){
            if (e.getStore().getId() == storeId)
                ret.add(e);
        }
        return ret;
    }

    //Simula il getAll della repository
    public List<Employee> getAll(){
        return employees;
    }

    //Simula la chiamata findById della repository Store
    public Store findStoreById(long storeId){
       if (store1.getId() == storeId)
           return store1;
       if (store2.getId() == storeId)
           return store2;
       return null;
    }
}
