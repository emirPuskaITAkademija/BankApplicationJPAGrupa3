package bank.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class AbstractBankAccount {

    //SessionFactory
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("BankApplicationJPAGrupa3PU");

    public Bankaccount getThis() {
        return (Bankaccount) this;
    }

    public void save() {
        EntityManager session = null;
        try {
            session = ENTITY_MANAGER_FACTORY.createEntityManager();
            session.getTransaction().begin();
            session.persist(this);
            session.getTransaction().commit();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void update() {
        EntityManager entityManager = null;
        try {
            entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(this);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Bankaccount get() {
        EntityManager session = null;
        try {
            session = ENTITY_MANAGER_FACTORY.createEntityManager();
            Bankaccount bankAccount = (Bankaccount) session.find(Bankaccount.class, getThis().getAccountNumber());
            return bankAccount;
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //HQL imena tabela u bazi: ima klase u javi mapirane iz te tabele
    public void delete() {
        EntityManager session = null;
        try {
            session = ENTITY_MANAGER_FACTORY.createEntityManager();
            session.getTransaction().begin();
            session.remove(this);
            session.getTransaction().commit();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //SQL statement : koristi ime tabele u bazi bankaccount
    public static List<Bankaccount> loadAll() {
        //Hibernate 4.3
        EntityManager entityManager = null;
        try {
            entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            //HQL NamedQuery
            Query query = entityManager.createNamedQuery("Bankaccount.findAll");
            List<Bankaccount> bankaccounts = query.getResultList();
            return bankaccounts;
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static boolean transferMoney(Bankaccount fromAccount, Bankaccount toAccount, double amount) {
        if (fromAccount == null || toAccount == null || amount <= 0) {
            return false;
        }
        if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
            return false;
        }
        EntityManager entityManager = null;
        try {
            entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            if (fromAccount.getAmount() < amount) {
                System.err.println("Nemate dovoljno sredstava na vašem računu");
                return false;
            }
            double stariIznosFromAccount = fromAccount.getAmount();
            fromAccount.setAmount(stariIznosFromAccount - amount);
            double stariIznosToAccount = toAccount.getAmount();
            toAccount.setAmount(stariIznosToAccount + amount);
            entityManager.merge(fromAccount);
            entityManager.merge(toAccount);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
