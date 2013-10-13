package eu.wisebed.wisedb.test.runnable;

import eu.wisebed.wisedb.controller.*;
import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Adds a simple Testbed.
 */
public class WiseDBTest extends BaseTestSetup {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(WiseDBTest.class);
    private static final java.lang.String COORDINATE_TYPE = "Absolute";
    private static final java.lang.String TESTBED_NAME = "theTestbed";
    private static final String PREFIX = "urn:testbed2:";
    private static final String CAPABILITY_PREFIX = "urn:node:capability:";


    @Test
    @Order(value = 0)
    public void runTestbedTests() throws UnknownTestbedException {
        addTestbed();
        listTestbeds();
        checkTestbed();
//    }
//
//    @Test
//    @Order(value = 1)
//    public void runNodeTests() throws UnknownTestbedException {
        addNodes();
        listNodes();
//    }
//
//    @Test
//    @Order(value = 2)
//    public void runLinkTests() throws UnknownTestbedException {
        addLinks();
        listLinks();
//    }
//
//    @Test
//    @Order(value = 3)
//    public void nodeReadingTests() throws UnknownTestbedException {
        addNodeReadings();
        listNodeReadings();
    }

    public void addTestbed() {
        Testbed testbed = new Testbed();
        LOGGER.info("Provide Testbed Name");
        testbed.setName(TESTBED_NAME);
        LOGGER.info("Provide Testbed Description");
        testbed.setDescription("description2");
        LOGGER.info("Provide Testbed's urnPrefix");
        testbed.setUrnPrefix(PREFIX);
        LOGGER.info("Using your default TimeZone : " + TimeZone.getDefault().getDisplayName());
        testbed.setTimeZone(TimeZone.getDefault());
        testbed.setUrnCapabilityPrefix(CAPABILITY_PREFIX);
        // import to db
        TestbedControllerImpl.getInstance().add(testbed);


        Setup setup = new Setup();
        setup.setTestbed(testbed);
//        setup.setId(testbed.getId());
        setup.setCoordinateType(COORDINATE_TYPE);
        setup.setDescription("description");
        Origin origin = new Origin();
        origin.setX(Float.valueOf(0));
        origin.setY(Float.valueOf(0));
        origin.setZ(Float.valueOf(0));
        origin.setPhi(Float.valueOf(0));
        origin.setTheta(Float.valueOf(0));
        setup.setOrigin(origin);

        SetupControllerImpl.getInstance().add(setup);

        testbed.setSetup(setup);
    }


    public void checkTestbed() {
        final Testbed testbed = TestbedControllerImpl.getInstance().getByName("theTestbed");
        assertNotNull(testbed);
        final Setup setup = SetupControllerImpl.getInstance().getByID(testbed.getId());
        assertEquals(testbed.getName(), TESTBED_NAME);
        assertEquals(setup.getId(), testbed.getId());
        assertEquals(setup.getCoordinateType(), COORDINATE_TYPE);
    }

    public void listTestbeds() {
        final List<Testbed> testbeds = TestbedControllerImpl.getInstance().list();
        for (final Testbed testbed : testbeds) {
            LOGGER.info("Testbed: " + testbed);
            LOGGER.info("Setup: " + testbed.getSetup());
        }


    }

    public void addNodes() throws UnknownTestbedException {
        for (int i = 0; i < 10; i++) {
            NodeControllerImpl.getInstance().prepareInsertNode(PREFIX + "0x" + i);
        }
    }

    public void listNodes() {
        final List<Node> nodes = NodeControllerImpl.getInstance().list();
        for (final Node noode : nodes) {
            LOGGER.info("Node: " + noode + ", Setup: " + noode.getSetup());
        }


    }

    public void addLinks() throws UnknownTestbedException {
        for (int i = 0; i < 9; i++) {
            LinkControllerImpl.getInstance().prepareInsertLink(PREFIX + "0x1" + i, PREFIX + "0x2" + i);
        }
    }

    public void listLinks() {
        final List<Node> nodes = NodeControllerImpl.getInstance().list();
        for (final Node noode : nodes) {
            LOGGER.info("Node: " + noode + ", Setup: " + noode.getSetup());
        }
    }

    public void addNodeReadings() throws UnknownTestbedException {
        final List<Node> nodes = NodeControllerImpl.getInstance().list();
        Random rand = new Random();
        for (Node node : nodes) {
            for (int i = 0; i < 10; i++) {
                NodeReadingControllerImpl.getInstance().insertReading(node.getName(), CAPABILITY_PREFIX + "cap1", rand.nextDouble(), null, new Date());
            }
        }
    }

    private void listNodeReadings() {
        Node node = NodeControllerImpl.getInstance().getByName(PREFIX + "0x1");
        Capability capability = CapabilityControllerImpl.getInstance().getByID(CAPABILITY_PREFIX + "cap1");
        List<NodeReading> readings = NodeReadingControllerImpl.getInstance().listNodeReadings(node, capability);
        for (NodeReading reading : readings) {
            LOGGER.info(reading);
        }
    }
}
