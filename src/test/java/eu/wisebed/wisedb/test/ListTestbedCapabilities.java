package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class ListTestbedCapabilities {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(ListTestbedCapabilities.class);


    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            {
                long start = System.currentTimeMillis();
                final Setup setup = TestbedControllerImpl.getInstance().getByID(1).getSetup();
                LOGGER.info("testbed @ " + (System.currentTimeMillis() - start));
                final List<Capability> capabilities = CapabilityControllerImpl.getInstance().list(setup);
                for (Capability capability : capabilities) {
                    LOGGER.info(capability.toString() + ":" + capability.hashCode());

                }
                LOGGER.info("capabilities @ " + (System.currentTimeMillis() - start));
            }
            {
                long start = System.currentTimeMillis();
                final Setup setup = TestbedControllerImpl.getInstance().getByID(1).getSetup();
                LOGGER.info("setup @ " + (System.currentTimeMillis() - start));
                final List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(setup);
                LOGGER.info("nodeCapabilities @ " + (System.currentTimeMillis() - start));
                final List<LinkCapability> linkCapabilities = LinkCapabilityControllerImpl.getInstance().list(setup);
                LOGGER.info("linkCapabilities @ " + (System.currentTimeMillis() - start));
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
