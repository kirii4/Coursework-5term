package ermakov.onlinebanking.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Payment implements Serializable {
    private int idPayment;
    private String paymentType;
    private double amount;
    private Date date;
    private String accountNumber;
    private String cardNumber;

    public Payment() {
        this.idPayment = 0;
    }

    public int getIdPayment() {
        return this.idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return getIdPayment() == payment.getIdPayment() && Double.compare(getAmount(), payment.getAmount()) == 0 && Objects.equals(getPaymentType(), payment.getPaymentType()) && Objects.equals(getDate(), payment.getDate()) && Objects.equals(getAccountNumber(), payment.getAccountNumber()) && Objects.equals(getCardNumber(), payment.getCardNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPayment(), getPaymentType(), getAmount(), getDate(), getAccountNumber(), getCardNumber());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "idPayment=" + idPayment +
                ", paymentType='" + paymentType + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", accountNumber='" + accountNumber + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}