package eu.wisebed.wisedb.test.get;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetNode {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetNode.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final String name = "urn:test:0x1";
        try {
            final Node node = NodeControllerImpl.getInstance().getByID(1);
            if (node != null) {
                LOGGER.info("id: " + node.getId());
//                LOGGER.info("description: " + NodeControllerImpl.getInstance().getDescription(node));
//                LOGGER.info("Position :" + NodeControllerImpl.getInstance().getPosition(node));
//                LOGGER.info("Origin :" + NodeControllerImpl.getInstance().getOrigin(node));
                LOGGER.info("Setup : " + node.getSetup());
                LOGGER.info("Testbed : " + node.getSetup().getTestbed());
            } else {
                LOGGER.error("node " + name + " does not exist!");
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
