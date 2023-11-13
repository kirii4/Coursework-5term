package ermakov.onlinebanking.model;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private int idAccount;
    private String accountNumber;
    private String accountType;
    private double balance;
    private int userID;

    public Account() {
        this.idAccount = 0;
    }

    public int getIdAccount() {
        return this.idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String toString() {
        return "Account{idAccount=" + this.idAccount + ", accountNumber='" + this.accountNumber + "', accountType='" + this.accountType + "', balance=" + this.balance + ", userID=" + this.userID + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Account)) {
            return false;
        } else {
            Account account = (Account)o;
            return this.getIdAccount() == account.getIdAccount() && Double.compare(this.getBalance(), account.getBalance()) == 0 && this.getUserID() == account.getUserID() && Objects.equals(this.getAccountNumber(), account.getAccountNumber()) && Objects.equals(this.getAccountType(), account.getAccountType());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIdAccount(), this.getAccountNumber(), this.getAccountType(), this.getBalance(), this.getUserID()});
    }
}
