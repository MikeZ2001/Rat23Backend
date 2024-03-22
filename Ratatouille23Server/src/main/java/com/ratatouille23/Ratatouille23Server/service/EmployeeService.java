package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.*;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;
import com.ratatouille23.Ratatouille23Server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final TableRepository tableRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CategoryRepository categoryRepository,
                           OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                           ProductRepository productRepository, TableRepository tableRepository, StoreRepository storeRepository){
        this.employeeRepository = employeeRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.tableRepository = tableRepository;
        this.storeRepository = storeRepository;
    }


    @GetMapping
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @GetMapping
    public Employee getEmployeeById(Long id) throws IllegalStateException{
        Employee optionalEmployee = employeeRepository.findById(id).orElseThrow(()->new IllegalStateException("Il dipendente con id "+id+" non esiste."));

        return optionalEmployee;
    }

    @PostMapping
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping
    public void deleteEmployee(Long id) throws IllegalStateException{
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null)
            throw new IllegalStateException("Impossibile eliminare l'utente perchè non esiste.");

        if (employee != null && employee.getRole().equals(Role.ADMINISTRATOR)){
            List<Employee> admins = employeeRepository.getAdminsOfStore(employee.getStore().getId()).orElse(null);
            if (admins != null && admins.size() > 1)
                employeeRepository.deleteById(id);
             else if ((getEmployeesOfStore(employee.getStore().getId())).size() == 1) {
                //rimane solo questo admin => wipe totale
                wipeData(employee);
            }
             else
                throw new IllegalStateException("Impossibile eliminare l'ultimo account amministratore senza prima eliminare gli altri dipendenti.");
        }else{
            employeeRepository.deleteById(id);
        }
    }

    private void wipeData(Employee employee){
        List<Table> tables = tableRepository.getAllTablesOfStore(employee.getStore().getId()).orElse(null);
        List<Category> categories = categoryRepository.getCategoriesOfStore(employee.getStore().getId()).orElse(null);
        List<Order> orders = new ArrayList<>();
        if (tables != null)
            for (Table table : tables){
                orders.addAll(orderRepository.getAllOrdersOfTable(table.getId()).orElse(null));
            }
        List<OrderItem> orderItems = new ArrayList<>();
        if (orders != null && orders.size() != 0)
            for (Order order: orders){
                orderItems.addAll(orderItemRepository.getAllOrderItemByOrderId(order.getId()).orElse(null));
            }
        List<Product> products = productRepository.getAllProductsOfStore(employee.getStore().getId()).orElse(null);

        //DELETION
        if (orderItems != null)
            for (OrderItem orderItem: orderItems){
                orderItemRepository.deleteById(orderItem.getId());
            }
        if (orders != null)
            for (Order order : orders) {
                orderRepository.deleteById(order.getId());
            }
        if (products != null)
            for (Product prod: products){
                productRepository.deleteById(prod.getId());
            }
        if (categories != null)
            for (Category category : categories){
                categoryRepository.deleteById(category.getId());
            }
        if (tables != null)
            for (Table table : tables){
                tableRepository.deleteById(table.getId());
            }
        employeeRepository.deleteById(employee.getId());
        storeRepository.deleteById(employee.getStore().getId());
    }

    @PutMapping
    public void updateEmployee(Long id, Employee employee) throws IllegalStateException{
        Employee updateEmployee = employeeRepository.findById(id).orElse(null);

        if (updateEmployee == null)
            throw new IllegalStateException("Utente inesistente.");

        if (updateEmployee.getRole().equals(Role.ADMINISTRATOR) && !employee.getRole().equals(Role.ADMINISTRATOR)) {
            List<Employee> admins = employeeRepository.getAdminsOfStore(employee.getStore().getId()).orElse(null);
            if (admins != null && admins.size() > 1) {
                employeeRepository.updateEmployee(id,employee.getName(),employee.getSurname(),employee.getEmail(), employee.getRole());
            } else {
                throw new IllegalStateException("Impossibile avere 0 amministratori di questa attività.");
            }
        }else{
            employeeRepository.updateEmployee(id,employee.getName(),employee.getSurname(),employee.getEmail(), employee.getRole());
        }
    }

    @GetMapping
    public Employee getEmployeeByEmail(String email) throws IllegalStateException{
        Employee optionalEmployee = employeeRepository.getEmployeeByEmail(email).orElseThrow(()->new IllegalStateException("Il dipendente con email "+email+" non esiste."));
        return optionalEmployee;
    }

    @GetMapping
    public List<Employee> getEmployeesOfStore(Long storeId) throws IllegalStateException{
        List<Employee> employees = new ArrayList<>();
        employees.addAll(employeeRepository.getEmployeesOfStore(storeId).orElseThrow(()->new IllegalStateException("Non ci sono dipendenti per l'attività con id:"+storeId+".")));

        return employees;
    }
}
