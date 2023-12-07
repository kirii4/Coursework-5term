package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.Payment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SQLTransactionTest {
    private SQLTransaction sqlTransaction;

    @Before
    public void setUp() {
        sqlTransaction = SQLTransaction.getInstance();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetCardNumber() {
        int userId = 35;
        String cardNumber = sqlTransaction.getCardNumber(userId);
        assertNotNull(cardNumber);
        System.out.println("Card Number: " + cardNumber);
    }

    @Test
    public void testDoPayment() {
        Payment payment = new Payment();
        payment.setPaymentType("МТС");
        payment.setAmount(100.0);
        payment.setDate(new java.sql.Date(System.currentTimeMillis()));
        payment.setCardNumber("1305 7013 6300 6671");

        boolean result = sqlTransaction.doPayment(payment);
        assertTrue(result);
        System.out.println("Payment successful.");
    }

    @Test
    public void testSelectUserPayments() {
        String userEmail = "vika.kirill.2004@gmail.com";
        ArrayList<Payment> paymentsList = sqlTransaction.selectUserPayments(userEmail);

        assertNotNull(paymentsList);
        if (!paymentsList.isEmpty()) {
            System.out.println("User Payments:");
            for (Payment payment : paymentsList) {
                System.out.println(payment);
            }
        } else {
            System.out.println("No payments found for the user.");
        }
    }
}