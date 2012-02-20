package eu.wisebed.wisedb.test.get;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetCapability {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetCapability.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final int id = 1;
        try {
            final Capability capability = CapabilityControllerImpl.getInstance().getByID("urn:wisebed:node:capability:batterycharge");
            if (capability != null) {
                LOGGER.info("getName: " + capability.getName());
                LOGGER.info("getDatatype: " + capability.getDatatype());
                LOGGER.info("getDefaultvalue: " + capability.getDefaultvalue());
                LOGGER.info("getDescription: " + capability.getDescription());
                LOGGER.info("getUnit: " + capability.getUnit());
                //                LOGGER.info("Position :" + NodeControllerImpl.getInstance().getPosition(node));
//                LOGGER.info("Origin :" + NodeControllerImpl.getInstance().getOrigin(node));

            } else {
                LOGGER.error("capability " + id + " does not exist!");
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
