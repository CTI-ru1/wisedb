package eu.wisebed.wisedb.test.get;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkControllerImpl;
import eu.wisebed.wisedb.model.Link;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetLink {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetLink.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final int id = 1;
        try {
            final Link link = LinkControllerImpl.getInstance().getByID(32);
            if (link != null) {
                LOGGER.info("id: " + link.getId());
                LOGGER.info("src: " + link.getSource());
                LOGGER.info("target: " + link.getTarget());
                //                LOGGER.info("Position :" + NodeControllerImpl.getInstance().getPosition(node));
//                LOGGER.info("Origin :" + NodeControllerImpl.getInstance().getOrigin(node));
                LOGGER.info("Setup : " + link.getSetup());
                LOGGER.info("Testbed : " + link.getSetup().getTestbed());
            } else {
                LOGGER.error("link " + id + " does not exist!");
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
