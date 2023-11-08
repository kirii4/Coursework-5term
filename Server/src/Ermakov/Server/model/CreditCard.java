package Ermakov.Server.model;

import java.io.Serializable;
import java.util.Objects;

public class CreditCard implements Serializable {
    private int idCreditCard;
    private String cardNumber;
    private double creditLimit;
    private double availableCredit;
    private String accountNumber;

    public CreditCard() {
        this.idCreditCard = 0;
    }

    public int getIdCreditCard() {
        return this.idCreditCard;
    }

    public void setIdCreditCard(int idCreditCard) {
        this.idCreditCard = idCreditCard;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getCreditLimit() {
        return this.creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getAvailableCredit() {
        return this.availableCredit;
    }

    public void setAvailableCredit(double availableCredit) {
        this.availableCredit = availableCredit;
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
        } else if (!(o instanceof CreditCard)) {
            return false;
        } else {
            CreditCard that = (CreditCard)o;
            return this.getIdCreditCard() == that.getIdCreditCard() && Double.compare(this.getCreditLimit(), that.getCreditLimit()) == 0 && Double.compare(this.getAvailableCredit(), that.getAvailableCredit()) == 0 && Objects.equals(this.getCardNumber(), that.getCardNumber()) && Objects.equals(this.getAccountNumber(), that.getAccountNumber());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIdCreditCard(), this.getCardNumber(), this.getCreditLimit(), this.getAvailableCredit(), this.getAccountNumber()});
    }

    public String toString() {
        return "CreditCard{idCreditCard=" + this.idCreditCard + ", cardNumber='" + this.cardNumber + "', creditLimit=" + this.creditLimit + ", availableCredit=" + this.availableCredit + ", accountNumber='" + this.accountNumber + "'}";
    }
}
