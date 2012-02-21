package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Capability;
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
public class ShowRooms {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ShowRooms.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final int id = 1;
        try {
            // look up setup
            final Setup setup = TestbedControllerImpl.getInstance().getByID(id).getSetup();

            final Capability capability = CapabilityControllerImpl.getInstance().getByID("room");


            // get a list of node last readings from setup
            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(setup, capability);

            final Map<String, Integer> uniqueRooms = new HashMap<String, Integer>();

            for (final NodeCapability nodeCapability : nodeCapabilities) {
                uniqueRooms.put(nodeCapability.getLastNodeReading().getStringReading(), 1);
            }
            for (String room : uniqueRooms.keySet()) {

                LOGGER.info(room + "\n");
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            e.printStackTrace();
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
