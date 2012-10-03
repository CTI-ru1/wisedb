package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.LinkControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Adds a node reading
 */
public class DeleteNode {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(DeleteNode.class);

    public static void main(String args[]) {


        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // a valid urnPrefix for the testbed


            // a node id for the testbed
            final String nodeId = "urn:wisebed:ctitestbed:0x99c";

            // insert reading
            Node node = NodeControllerImpl.getInstance().getByName(nodeId);
            if (node != null) {
                List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(node);
                LOGGER.info("found " + nodeCapabilities.size() + " capabilities");
                for (NodeCapability nodeCapability : nodeCapabilities) {
                 if (!nodeCapability.getCapability().getName().contains("wisebed"))   continue;
                    LOGGER.info(nodeCapability);
//                    List<NodeReading> readings = NodeReadingControllerImpl.getInstance().listNodeReadings(node, nodeCapability.getCapability());
//                    for (NodeReading reading : readings) {
//                        NodeReadingControllerImpl.getInstance().delete(reading.getId());
//                        LOGGER.info("reading:" + reading.getId());
//                    }
                    NodeCapabilityControllerImpl.getInstance().delete(nodeCapability.getId());
                    LOGGER.info("nodeCapability:" + nodeCapability.getId());
                }

                List<Link> linksSource = LinkControllerImpl.getInstance().getBySource(node);
                List<Link> linksTarget = LinkControllerImpl.getInstance().getByTarget(node);
//
                for (Link link : linksSource) {
                    List<LinkCapability> linkCapabilities = LinkCapabilityControllerImpl.getInstance().list(link);
//                    List<LinkReading> readings = LinkReadingControllerImpl.getInstance().list(link);
//                    for (LinkReading reading : readings) {
//                        LinkReadingControllerImpl.getInstance().delete(reading.getId());
//                        LOGGER.info("linkCapability:" + reading.getId());
//                    }
                    for (LinkCapability linkCapability : linkCapabilities) {
                        LinkCapabilityControllerImpl.getInstance().delete(linkCapability.getId());
                        LOGGER.info("linkCapability:" + linkCapability.getId());
                    }
                    LinkControllerImpl.getInstance().delete(link.getId());
                    LOGGER.info("link:" + link.getId());
                }
//
                for (Link link : linksTarget) {
                    List<LinkCapability> linkCapabilities = LinkCapabilityControllerImpl.getInstance().list(link);
//                    List<LinkReading> readings = LinkReadingControllerImpl.getInstance().list(link);
//                    for (LinkReading reading : readings) {
//                        LinkReadingControllerImpl.getInstance().delete(reading.getId());
//                        LOGGER.info("linkCapability:" + reading.getId());
//                    }
                    for (LinkCapability linkCapability : linkCapabilities) {
                        LinkCapabilityControllerImpl.getInstance().delete(linkCapability.getId());
                        LOGGER.info("linkCapability:" + linkCapability.getId());
                    }
                    LinkControllerImpl.getInstance().delete(link.getId());
                    LOGGER.info("link:" + link.getId());
                }
                NodeControllerImpl.getInstance().delete(node.getId());
                LOGGER.info("node:" + node.getId());
            } else {
                LOGGER.error("Node does not exist");
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
