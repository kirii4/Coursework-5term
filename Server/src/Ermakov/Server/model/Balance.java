package Ermakov.Server.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Balance implements Serializable {
    private int idBalance;
    private double amount;
    private Date date;
    private String accountNumber;

    public Balance() {
        this.idBalance = 0;
    }

    public int getIDBalance() {
        return this.idBalance;
    }

    public void setIDBalance(int idBalance) {
        this.idBalance = idBalance;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String toString() {
        return "Balance{idBalance=" + this.idBalance + ", amount=" + this.amount + ", date=" + this.date + ", accountNumber='" + this.accountNumber + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Balance)) {
            return false;
        } else {
            Balance balance = (Balance)o;
            return this.getIDBalance() == balance.getIDBalance() && Double.compare(this.getAmount(), balance.getAmount()) == 0 && Objects.equals(this.getDate(), balance.getDate()) && Objects.equals(this.getAccountNumber(), balance.getAccountNumber());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIDBalance(), this.getAmount(), this.getDate(), this.getAccountNumber()});
    }
}
