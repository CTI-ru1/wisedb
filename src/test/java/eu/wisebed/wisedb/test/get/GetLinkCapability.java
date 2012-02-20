package eu.wisebed.wisedb.test.get;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.model.LinkCapability;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetLinkCapability {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetLinkCapability.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final int id = 1;
        try {
            final LinkCapability capability = LinkCapabilityControllerImpl.getInstance().getByID(1);
            if (capability != null) {
                LOGGER.info("getId: " + capability.getId());
                LOGGER.info("getLink: " + capability.getLink());
                LOGGER.info("getCapability: " + capability.getCapability());
                LOGGER.info("getLastLinkReading: " + capability.getLastLinkReading());
                LOGGER.info("getLastLinkReading().getTimestamp: " + capability.getLastLinkReading().getTimestamp());

            } else {
                LOGGER.error("LinkCapability " + id + " does not exist!");
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
