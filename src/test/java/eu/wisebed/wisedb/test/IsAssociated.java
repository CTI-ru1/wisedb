package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.LinkControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Lists All readings of the database.
 */
public class IsAssociated {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(IsAssociated.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            final Node node1 = NodeControllerImpl.getInstance().getByID("urn:prefix:NODETEST3");
            final Node node2 = NodeControllerImpl.getInstance().getByID("urn:prefix:NODETEST1");

            final Capability capability1 = CapabilityControllerImpl.getInstance().getByID("temp");
            final Capability capability2 = CapabilityControllerImpl.getInstance().getByID("blah3");

            final Link link = LinkControllerImpl.getInstance().getByID(node1.getId(), node2.getId());


            LOGGER.info(NodeCapabilityControllerImpl.getInstance().isAssociated(node1, capability1));
            LOGGER.info(NodeCapabilityControllerImpl.getInstance().isAssociated(node2, capability2));
            LOGGER.info(NodeCapabilityControllerImpl.getInstance().isAssociated(node1, capability2));
            LOGGER.info(NodeCapabilityControllerImpl.getInstance().isAssociated(node2, capability1));
            LOGGER.info(LinkCapabilityControllerImpl.getInstance().isAssociated(link, capability1));
            LOGGER.info(LinkCapabilityControllerImpl.getInstance().isAssociated(link, capability2));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
