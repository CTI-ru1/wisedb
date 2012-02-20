package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetTestbedStatus {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetTestbedStatus.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();


        try {

            // a specific setup is requested by setup Id
            int testbedId;

            testbedId = 1;


            // look up setup
            final Setup setup = TestbedControllerImpl.getInstance().getByID(3).getSetup();
            LOGGER.info(setup);
            // get a list of node last readings from setup
            final List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(setup);
            LOGGER.info("nodeCapabilities");
            // get a list of link statistics from setup
            final List<LinkCapability> linkCapabilities = LinkCapabilityControllerImpl.getInstance().list(setup);
            LOGGER.info("linkCapabilities");

            // Prepare data to pass to jsp
            final Map<String, Object> refData = new HashMap<String, Object>();
            refData.put("setup", setup);
            refData.put("lastNodeReadings", nodeCapabilities);
            refData.put("lastLinkReadings", linkCapabilities);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
