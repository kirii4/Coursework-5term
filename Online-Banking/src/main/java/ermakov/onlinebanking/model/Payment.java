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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Payment)) {
            return false;
        } else {
            Payment payment = (Payment)o;
            return this.getIdPayment() == payment.getIdPayment() && Double.compare(this.getAmount(), payment.getAmount()) == 0 && Objects.equals(this.getPaymentType(), payment.getPaymentType()) && Objects.equals(this.getDate(), payment.getDate()) && Objects.equals(this.getAccountNumber(), payment.getAccountNumber());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIdPayment(), this.getPaymentType(), this.getAmount(), this.getDate(), this.getAccountNumber()});
    }

    public String toString() {
        return "Payment{idPayment=" + this.idPayment + ", paymentType='" + this.paymentType + "', amount=" + this.amount + ", date=" + this.date + ", accountNumber='" + this.accountNumber + "'}";
    }
}