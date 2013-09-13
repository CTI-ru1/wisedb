package eu.wisebed.wisedb.test.runnable;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetTestbedsTest extends BaseTestSetup{

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetTestbedsTest.class);

    @Test
    public void testGetTestbeds() {


        final List<Testbed> testbeds = TestbedControllerImpl.getInstance().list();
        for (final Testbed testbed : testbeds) {
            LOGGER.info("Testbed: " + testbed.getName());
        }


    }
}
