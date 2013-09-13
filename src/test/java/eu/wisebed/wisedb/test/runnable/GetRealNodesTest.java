package eu.wisebed.wisedb.test.runnable;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetRealNodesTest extends BaseTestSetup {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetRealNodesTest.class);

    @Test
    public void getRealNodesTest() {

        final String name = "urn:wisebed:ctitestbed:virtual:room:0.I.9";
        Node node = NodeControllerImpl.getInstance().getByName(name);
        Capability capability = CapabilityControllerImpl.getInstance().getByID("urn:wisebed:node:capability:lz1");

        List<Node> nodes = NodeControllerImpl.getInstance().getRealNodes(node);
        for (Node node1 : nodes) {
            if (CapabilityControllerImpl.getInstance().hasCapability(node1, capability)) {
                LOGGER.info(node1);
            }
        }
    }
}
