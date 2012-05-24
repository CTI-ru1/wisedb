package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeReadingControllerImpl;
import eu.wisebed.wisedb.controller.SetupControllerImpl;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Adds a node reading
 */
public class DeleteCapability {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(DeleteCapability.class);

    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // a valid urnPrefix for the testbed


            // a node id for the testbed
            final String nodeId = "phi";

            final Setup setup = SetupControllerImpl.getInstance().getByID(1);
            // insert reading
            final List<NodeCapability> nodecapabilities = NodeCapabilityControllerImpl.getInstance().list(setup);
            if (nodecapabilities != null) {
                for (NodeCapability nodeCapability : nodecapabilities) {
                    if (nodeCapability.getCapability().getName().equals(nodeId)) {
                        LOGGER.info(nodeCapability);
                        List<NodeReading> readings = NodeReadingControllerImpl.getInstance().listNodeReadings(nodeCapability.getNode(), nodeCapability.getCapability());
                        for (NodeReading reading : readings) {
                            NodeReadingControllerImpl.getInstance().delete(reading.getId());
                            LOGGER.info("reading:" + reading.getId());
                        }
                        NodeCapabilityControllerImpl.getInstance().delete(nodeCapability.getId());
                        LOGGER.info("nodeCapability:" + nodeCapability.getId());

                    }
                }


            } else {
                LOGGER.error("Node Capability does not exist");
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
