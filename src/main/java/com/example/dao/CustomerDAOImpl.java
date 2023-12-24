package com.example.dao;

import com.example.application.HibernateUtil;
import com.example.entities.Customer;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.Comparator;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> getCustomers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
        List<Customer> customers = query.getResultList();
        //customers.sort(Comparator.comparing(Customer::getLastName)); //alternate way of sorting
        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Integer id = customer.getId();
        if (id == null) {
            session.persist(customer);
            System.out.println(">>Creating new customer");
        } else {
            session.merge(customer);
            System.out.println(">>Updating existing customer");
        }
        session.getTransaction().commit();
    }

    @Override
    public Customer getCustomer(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Customer customer = session.get(Customer.class, id);
        return customer;
    }

    @Override
    public void deleteCustomer(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        MutationQuery query = session.createMutationQuery("delete from Customer where id=:customerId");
        query.setParameter("customerId", id);
        query.executeUpdate();
        session.getTransaction().commit();
    }
}