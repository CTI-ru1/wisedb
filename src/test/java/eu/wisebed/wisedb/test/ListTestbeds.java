package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.LinkControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Lists all testbeds on the database and provides additional information on them.
 */
public class ListTestbeds {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListTestbeds.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            final List<Testbed> testbeds = TestbedControllerImpl.getInstance().list();
            LOGGER.info("Testbeds: " + testbeds.size());

            for (final Testbed testbed : testbeds) {

                LOGGER.info("\tTestbed:" + testbed.getName() + ", description:" + testbed.getDescription());

                final Setup setup = testbed.getSetup();
                LOGGER.info("\t\tSetup:" + setup.getId() + ", description:" + setup.getDescription());

                final Long nodesCount = NodeControllerImpl.getInstance().count(testbed.getSetup());
                final Long linksCount = LinkControllerImpl.getInstance().count(testbed.getSetup());
                LOGGER.info("\t\tNodes:" + nodesCount + ", Links:" + linksCount);

                final List<Node> nodes = NodeControllerImpl.getInstance().list(setup);
                final List<Link> links = LinkControllerImpl.getInstance().list(setup);

                for (final Node node : nodes) {
                    LOGGER.info("\t\t\t" + node.getId() + " des: " + NodeControllerImpl.getInstance().getDescription(node));
                    final List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(node);
                    LOGGER.info("\t\t\t\t" + node.getId() + " " + nodeCapabilities.size() + " nodeCaps");
                }

                for (final Link link : links) {
                    LOGGER.info("\t\t\t" + link.getSource() + "--" + link.getTarget());
                    List<LinkCapability> linkCapabilities = LinkCapabilityControllerImpl.getInstance().list(link);
                    LOGGER.info("\t\t\t\t" + link.getSource() + "--" + link.getTarget() + " " + linkCapabilities.size() + " LinkCaps");
                }
            }

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
