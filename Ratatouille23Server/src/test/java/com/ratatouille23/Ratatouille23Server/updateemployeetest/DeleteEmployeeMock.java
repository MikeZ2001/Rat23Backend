package com.ratatouille23.Ratatouille23Server.updateemployeetest;

import com.ratatouille23.Ratatouille23Server.EmployeeDBMock;
import com.ratatouille23.Ratatouille23Server.model.*;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;
import java.util.List;

public class DeleteEmployeeMock {
    private EmployeeDBMock dbMock;

    public DeleteEmployeeMock() {
        this.dbMock = new EmployeeDBMock();
    }

    //Metodo da testare
    public void deleteEmployee(Long id) throws IllegalStateException{
        Employee employee = dbMock.findById(id); //Equivalente di findById().orElse(null) della repository

        if (employee == null)
            throw new IllegalStateException("Impossibile eliminare l'utente perchè non esiste.");

        if (employee != null && employee.getRole().equals(Role.ADMINISTRATOR)){
            List<Employee> admins = dbMock.getAdminsOfStore(employee.getStore().getId()); //Equivalente di getAdminsOfStore().orElse(null) della repository
            if (admins != null && admins.size() > 1)
                dbMock.deleteById(id); //equivalente di deleteById() della repository
            else if ((getEmployeesOfStore(employee.getStore().getId())).size() == 1) {
                //rimane solo questo admin => wipe totale
                wipeData(employee);
            }
            else
                throw new IllegalStateException("Impossibile eliminare l'ultimo account amministratore senza prima eliminare gli altri dipendenti.");
        }else{
            dbMock.deleteById(id); //equivalente di deleteById() della repository
        }
    }

    //Metodo di appoggio che presupponiamo funzionante
    private void wipeData(Employee employee){
        dbMock.wipeData(employee.getStore().getId()); //Simula l'eliminazione di tutti i dati dal database senza dover richiamare singolarmente i metodi delle varie repository
    }

    //Simula la chiamata alla repository per ottenere tutti i dipendenti
    public List<Employee> getAllEmployees(){
        return dbMock.getAll();
    }

    //Simula una chiamata alla repository per ottenere tutti gli impiegati dello store
    public List<Employee> getEmployeesOfStore(long storeId){
        return dbMock.getEmployeesOfStore(storeId);
    }

    //Necessario per valutare il wipe dei dati
    public Store findStoreById(long storeId){
        return dbMock.findStoreById(storeId);
    }
}
