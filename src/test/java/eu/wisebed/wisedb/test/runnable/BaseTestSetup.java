package eu.wisebed.wisedb.test.runnable;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class BaseTestSetup {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(BaseTestSetup.class);
    protected Transaction tx;


    @Before
    public void setup() throws Exception {
        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        tx = HibernateUtil.getInstance().getSession().beginTransaction();

    }

    @After
    public void teardown() throws Exception {
        try {
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e, e);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
