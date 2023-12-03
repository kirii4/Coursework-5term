package ermakov.onlinebanking.database;

import static org.junit.Assert.*;

import ermakov.onlinebanking.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SQLUsersTest {

    private SQLUsers sqlUsers;

    @Before
    public void setUp() {
        sqlUsers = SQLUsers.getInstance();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindUser() {
        User testUser = new User("admin", "admin");
        String status = sqlUsers.findUser(testUser);
        assertEquals("Expected status should be 'admin'", "admin", status);
    }

    @Test
    public void testInsert() {
        User testUser = new User(1, "login", "123", "user", "user1@example.com", "John", "Doe", "Pat", "KH", 6789);
        //User testUser1 = new User(1, "login", "123", "user", "user11@example.com", "John", "Doe", "Pat", "KH", 6789);
        sqlUsers.insert(testUser);
        //sqlUsers.insert(testUser1);
        assertTrue(sqlUsers.isEmailExists(testUser.getEmail()));
    }

    @Test
    public void testEditUser() {
        String email = "user1@example.com";
        String newEmail = "user10@example.com";
        assertTrue(sqlUsers.editUser(email, newEmail, "Почта"));
    }

    @Test
    public void testUpdatePassword() {
        String email = "user1@example.com";
        String newPassword = "1234";
        assertTrue(!sqlUsers.updatePassword(email, newPassword));
    }
    @Test
    public void testDeleteUser() {
        String email = "user11@example.com";
        assertTrue(sqlUsers.deleteUser(email));
    }
}
