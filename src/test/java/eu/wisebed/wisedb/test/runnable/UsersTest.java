package eu.wisebed.wisedb.test.runnable;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.UserControllerImpl;
import eu.wisebed.wisedb.model.User;
import eu.wisebed.wisedb.model.UserRole;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Lists all testbeds on the database and provides additional information on them.
 */
public class UsersTest extends BaseTestSetup {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(UsersTest.class);
    private static final String USERNAME = "user";
    private static final String USEREMAIL = "user@mail.com";
    private static final String USERPASS = "pass";
    private static final String FORM_USERNAME = "formuser";
    private static final String FORM_USEREMAIL = "formuser@mail.com";
    private static final String FORM_USERPASS = "formpass";

    @Test
    public void test() throws Exception {
    add();
        getByUsername();
        addUsingForm();
        list();
    }

    public void list() {
        LOGGER.info("list");

        List<User> users = UserControllerImpl.getInstance().list();

        for (User user : users) {
            LOGGER.info(user);
            List<UserRole> roles = UserControllerImpl.getInstance().listRoles(user);
            for (UserRole role : roles) {
                LOGGER.info(role);
            }
        }

    }

    public void getByUsername() {
        LOGGER.info("getByUsername");

        User user = UserControllerImpl.getInstance().getByUsername(USERNAME);
        LOGGER.info(user);
        assertNotNull(user);
        assertEquals(user.getUsername(), USERNAME);
        assertEquals(user.getEmail(), USEREMAIL);
        assertEquals(user.getPassword(), USERPASS);
    }

    public void addUsingForm() throws Exception {
        LOGGER.info("addUsingForm");

        int uuid = UserControllerImpl.getInstance().getFormInt();
        User user = new User();
        user.setUsername(FORM_USERNAME);
        user.setPassword(FORM_USERPASS);
        user.setEnabled(true);
        user.setEmail(FORM_USEREMAIL);
        UserControllerImpl.getInstance().add(user, uuid);
    }


    public void add() {
        LOGGER.info("add");

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(USERPASS);
        user.setEnabled(true);
        user.setEmail(USEREMAIL);
        UserControllerImpl.getInstance().add(user);
    }
}
