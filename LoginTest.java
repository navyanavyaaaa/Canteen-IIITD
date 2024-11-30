import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Scanner;

public class LoginTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {

        UserDataManager.initializeFiles();
     System.setOut(new PrintStream(outContent));
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
//TESTCASE1: a new user is able to sign up
    @Test
    public void testCustomerSignUp_Successful() {
        String testName = "Rayansh" + System.currentTimeMillis();

        Scanner scanner = new Scanner(testName + "\npassword\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            Method signUpMethod = Main.class.getDeclaredMethod("signUpAsCustomer", Scanner.class);
            signUpMethod.setAccessible(true);

            signUpMethod.invoke(null, scanner);

            System.setOut(originalOut);

            assertTrue("Successful customer signup message not found",
                    outContent.toString().contains("Customer signed up successfully"));

        } catch (Exception e) {
            fail("Exception during customer signup test: " + e.getMessage());
        }
    }
//TESTCASE2:an already signed up user tries to sign up with the same name again
    @Test
    public void testCustomerSignUp_DuplicateUser() {
        String testName = "UniqueTestUser";

        Scanner scanner = new Scanner(testName + "\npassword\n");

        try {
            Method signUpMethod = Main.class.getDeclaredMethod("signUpAsCustomer", Scanner.class);
            signUpMethod.setAccessible(true);

            signUpMethod.invoke(null, scanner);

            scanner = new Scanner(testName + "\npassword\n");

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            try {

                signUpMethod.invoke(null, scanner);
            } finally {

                System.setOut(originalOut);
            }
            assertTrue("Duplicate user message not found", outContent.toString().contains("This user already exists"));

        } catch (Exception e) {
            fail("Exception during signup test: " + e.getMessage());
        }
    }
//TESTCASE3:user tries to login with a username which is not signed up
    @Test
    public void testCustomerLogin_InvalidUsername() {

        provideInput("nonExistent\npassword\n");
        Scanner scanner = new Scanner(System.in);

        try {
            Method loginMethod = Main.class.getDeclaredMethod("logInAsCustomer", Scanner.class);
            loginMethod.setAccessible(true);
            loginMethod.invoke(null, scanner);
        } catch (Exception e) {
            fail("Exception occurred during login attempt: " + e.getMessage());
        }

        assertTrue(outContent.toString().contains("This customer user doesn't exist"));
    }
//TESTCASE4:username is correct but the password corresponding to it doesnt match the entered password
    @Test
    public void testCustomerLogin_WrongPassword() {
        provideInput("user123\n12345\n");
        Scanner scanner = new Scanner(System.in);

        try {
            Method signUpMethod = Main.class.getDeclaredMethod("signUpAsCustomer", Scanner.class);
            signUpMethod.setAccessible(true);
            signUpMethod.invoke(null, scanner);
        } catch (Exception e) {
            fail("Exception occurred during customer sign up: " + e.getMessage());
        }

        outContent.reset();

        provideInput("user123iiitd\nwrongpassword\n");
        scanner = new Scanner(System.in);

        try {
            Method loginMethod = Main.class.getDeclaredMethod("logInAsCustomer", Scanner.class);
            loginMethod.setAccessible(true);
            loginMethod.invoke(null, scanner);
        } catch (Exception e) {
            fail("Exception occurred during login attempt: " + e.getMessage());
        }

        assertTrue(outContent.toString().contains("Username and password do not match"));
    }
    //TESCASE5:a new user is able to sign up
    @Test
    public void testAdminSignUp_Successful() {
        String testName = "meAdmin" + System.currentTimeMillis();

        Scanner scanner = new Scanner(testName + "\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            Method signUpMethod = Main.class.getDeclaredMethod("signUpAsAdmin", Scanner.class);
            signUpMethod.setAccessible(true);

            signUpMethod.invoke(null, scanner);
            System.setOut(originalOut);

            assertTrue("Successful admin signup message not found",
                    outContent.toString().contains("Admin signed up successfully"));

        } catch (Exception e) {
            fail("Exception during admin signup test: " + e.getMessage());
        }
    }
    //TESTCASE6:an already signed up user tries to sign up with the same name again
    @Test
    public void testAdminSignUp_DuplicateUser() {
        String testName = "UniqueTestAdmin";

        Scanner scanner = new Scanner(testName + "\n");

        try {
            Method signUpMethod = Main.class.getDeclaredMethod("signUpAsAdmin", Scanner.class);
            signUpMethod.setAccessible(true);

            signUpMethod.invoke(null, scanner);

            scanner = new Scanner(testName + "\n");

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            try {
                signUpMethod.invoke(null, scanner);
            } finally {
                System.setOut(originalOut);
            }

            assertTrue("Duplicate admin user message not found",
                    outContent.toString().contains("This admin user already exists"));

        } catch (Exception e) {
            fail("Exception during admin signup test: " + e.getMessage());
        }
    }

//TESTCASE7: user tries to login with a username which is not signed up
    @Test
    public void testAdminLogin_InvalidUsername() {
        provideInput("nonExistentAdmin\n1234\n");
        Scanner scanner = new Scanner(System.in);

        try {
            Method loginMethod = Main.class.getDeclaredMethod("logInAsAdmin", Scanner.class);
            loginMethod.setAccessible(true);
            loginMethod.invoke(null, scanner);
        } catch (Exception e) {
            fail("Exception occurred during admin login attempt: " + e.getMessage());
        }
        assertTrue(outContent.toString().contains("This admin user doesn't exist"));
    }
//TESTCASE8:username is correct but the password corresponding to it doesnt match the entered password

    @Test
    public void testAdminLogin_WrongPassword() {
        provideInput("user567\n");
        Scanner scanner = new Scanner(System.in);

        try {
            Method signUpMethod = Main.class.getDeclaredMethod("signUpAsAdmin", Scanner.class);
            signUpMethod.setAccessible(true);
            signUpMethod.invoke(null, scanner);
        } catch (Exception e) {
            fail("Exception occurred during admin sign up: " + e.getMessage());
        }

        outContent.reset();

        provideInput("user567iiitd\nwrongpassword\n");
        scanner = new Scanner(System.in);

        try {
            Method loginMethod = Main.class.getDeclaredMethod("logInAsAdmin", Scanner.class);
            loginMethod.setAccessible(true);
            loginMethod.invoke(null, scanner);
        } catch (Exception e) {
            fail("Exception occurred during admin login attempt: " + e.getMessage());
        }

        assertTrue(outContent.toString().contains("Username and password do not match"));
    }
}