package eu.wisebed.wisedb.test.runnable;

import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;
import java.util.TimeZone;

/**
 * Adds a simple Testbed (Not running testbed-runtime).
 */
public class AddTestbedSetup extends BaseTestSetup {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddTestbedSetup.class);

    @Test
    public void runTests() {
        addTestbed();
        listTestbeds();
    }

    public void addTestbed() {
        Testbed testbed = new Testbed();
        LOGGER.info("Provide Testbed Name");
        testbed.setName("theTestbed");
        LOGGER.info("Provide Testbed Description");
        testbed.setDescription("description2");
        LOGGER.info("Provide Testbed's web page URL");
        testbed.setUrl("www.new.testbed2.com");
        LOGGER.info("Provide Testbed's urnPrefix");
        testbed.setUrnPrefix("urn:testbed2:");
        LOGGER.info("Using your default TimeZone : " + TimeZone.getDefault().getDisplayName());
        testbed.setTimeZone(TimeZone.getDefault());
        testbed.setFederated(false);
        testbed.setUrnCapabilityPrefix("urn:node:capability:");
        // import to db
        TestbedControllerImpl.getInstance().add(testbed);
    }


    public void listTestbeds() {


        final List<Testbed> testbeds = TestbedControllerImpl.getInstance().list();
        for (final Testbed testbed : testbeds) {
            LOGGER.info("Testbed: " + testbed.getName());
        }


    }
}
