package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.LastNodeReadingControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Lists All readings of the database.
 */
public class ListLastReadings {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListLastReadings.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            long start = System.currentTimeMillis();

            final Testbed testbed = TestbedControllerImpl.getInstance().getByID(1);

            final Capability capability = CapabilityControllerImpl.getInstance().getByID("urn:wisebed:node:capability:pir");
            final Node node = NodeControllerImpl.getInstance().getByName("urn:wisebed:ctitestbed:virtual:room:0.I.1");

            LOGGER.info(node);
            LOGGER.info(capability);
            final LastNodeReading lnr = LastNodeReadingControllerImpl.getInstance().getByNodeCapability(node, capability);

            System.out.println("end @" + (System.currentTimeMillis() - start));
            LOGGER.info("Last Node Reading : " + lnr);
            LOGGER.info(lnr.getNodeCapability().getCapability().getName() + " " + lnr.getReading() + " " + lnr.getNodeCapability().getNode().getName());

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
