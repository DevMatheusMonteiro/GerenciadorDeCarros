package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class GenericDAO<E> {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private Class<E> eClass;
    static  {
        try {
            emf = Persistence.createEntityManagerFactory("gerenciadorCarros");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public GenericDAO(Class<E> eClass) {
        this.eClass = eClass;
        em = emf.createEntityManager();
    }
    public GenericDAO<E> begin() {
        em.getTransaction().begin();
        return this;
    }
    public GenericDAO<E> end() {
        em.getTransaction().commit();
        return this;
    }
    public void create(E entity) {
        em.persist(entity);
    }
    public void update(E entity) {
        em.merge(entity);
    }
    public void delete(int id) {
        E entity = findById(id);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
    public E findById(int id) {
        return em.find(eClass, id);
    }
    public List<E> findAll() {
        Query query = em.createQuery("FROM " + eClass.getSimpleName());
        return query.getResultList();
    }
}