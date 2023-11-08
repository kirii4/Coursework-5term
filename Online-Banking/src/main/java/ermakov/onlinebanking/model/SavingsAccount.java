package ermakov.onlinebanking.model;

import java.io.Serializable;
import java.util.Objects;

public class SavingsAccount implements Serializable {
    private int idSavingsAccount;
    private String savingsAccountNumber;
    private double interestRate;
    private String accountNumber;

    public SavingsAccount() {
        this.idSavingsAccount = 0;
    }

    public int getIdSavingsAccount() {
        return this.idSavingsAccount;
    }

    public void setIdSavingsAccount(int idSavingsAccount) {
        this.idSavingsAccount = idSavingsAccount;
    }

    public String getSavingsAccountNumber() {
        return this.savingsAccountNumber;
    }

    public void setSavingsAccountNumber(String savingsAccountNumber) {
        this.savingsAccountNumber = savingsAccountNumber;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
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
        } else if (!(o instanceof SavingsAccount)) {
            return false;
        } else {
            SavingsAccount that = (SavingsAccount)o;
            return this.getIdSavingsAccount() == that.getIdSavingsAccount() && Double.compare(this.getInterestRate(), that.getInterestRate()) == 0 && Objects.equals(this.getSavingsAccountNumber(), that.getSavingsAccountNumber()) && Objects.equals(this.getAccountNumber(), that.getAccountNumber());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIdSavingsAccount(), this.getSavingsAccountNumber(), this.getInterestRate(), this.getAccountNumber()});
    }

    public String toString() {
        return "SavingsAccount{idSavingsAccount=" + this.idSavingsAccount + ", savingsAccountNumber='" + this.savingsAccountNumber + "', interestRate=" + this.interestRate + ", accountNumber='" + this.accountNumber + "'}";
    }
}
