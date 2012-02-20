package eu.wisebed.wisedb.test.get;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.model.NodeCapability;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetNodeCapability {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetNodeCapability.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final int id = 0;
        try {
            final NodeCapability capability = NodeCapabilityControllerImpl.getInstance().getByID(id);
            if (capability != null) {
                LOGGER.info("getId: " + capability.getId());
                LOGGER.info("getNode: " + capability.getNode());
                LOGGER.info("getCapability: " + capability.getCapability());
                LOGGER.info("getLastNodeReading: " + capability.getLastNodeReading());
                //                LOGGER.info("Position :" + NodeControllerImpl.getInstance().getPosition(node));
//                LOGGER.info("Origin :" + NodeControllerImpl.getInstance().getOrigin(node));

            } else {
                LOGGER.error("NodeCapability " + id + " does not exist!");
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
