package bank.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "bankaccount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bankaccount.findAll", query = "SELECT b FROM Bankaccount b")
    , @NamedQuery(name = "Bankaccount.findByAccountNumber", query = "SELECT b FROM Bankaccount b WHERE b.accountNumber = :accountNumber")
    , @NamedQuery(name = "Bankaccount.findByAmount", query = "SELECT b FROM Bankaccount b WHERE b.amount = :amount")})
public class Bankaccount extends AbstractBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "account_number")
    private String accountNumber;

    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;

    public Bankaccount() {
    }

    public Bankaccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Bankaccount(String accountNumber, double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountNumber != null ? accountNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bankaccount)) {
            return false;
        }
        Bankaccount other = (Bankaccount) object;
        if ((this.accountNumber == null && other.accountNumber != null) || (this.accountNumber != null && !this.accountNumber.equals(other.accountNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return accountNumber + " = " + amount;
    }

}
