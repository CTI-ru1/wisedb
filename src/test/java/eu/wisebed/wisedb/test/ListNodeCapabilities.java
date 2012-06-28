package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Lists All readings of the database.
 */
public class ListNodeCapabilities {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListNodeCapabilities.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            long start = System.currentTimeMillis();
            Testbed testbed = TestbedControllerImpl.getInstance().getByID(1);
            Node node = NodeControllerImpl.getInstance().getByName("urn:wisebed:ctitestbed:virtual:0.I.9");
            List<NodeCapability> capabilities = NodeCapabilityControllerImpl.getInstance().list(node);
            for (NodeCapability capability : capabilities) {
                LOGGER.info(capability.getCapability().getName());
            }

            LOGGER.info((System.currentTimeMillis() - start));

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
