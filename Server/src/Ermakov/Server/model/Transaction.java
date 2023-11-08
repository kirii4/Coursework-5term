package Ermakov.Server.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Transaction implements Serializable {
    private int idTransaction;
    private Date date;
    private double amount;
    private String description;
    private String accountNumber;

    public Transaction() {
        this.idTransaction = 0;
    }

    public int getIdTransaction() {
        return this.idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Transaction)) {
            return false;
        } else {
            Transaction that = (Transaction)o;
            return this.getIdTransaction() == that.getIdTransaction() && Double.compare(this.getAmount(), that.getAmount()) == 0 && Objects.equals(this.getDate(), that.getDate()) && Objects.equals(this.getDescription(), that.getDescription()) && Objects.equals(this.getAccountNumber(), that.getAccountNumber());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIdTransaction(), this.getDate(), this.getAmount(), this.getDescription(), this.getAccountNumber()});
    }

    public String toString() {
        return "Transaction{idTransaction=" + this.idTransaction + ", date=" + this.date + ", amount=" + this.amount + ", description='" + this.description + "', accountNumber='" + this.accountNumber + "'}";
    }
}
