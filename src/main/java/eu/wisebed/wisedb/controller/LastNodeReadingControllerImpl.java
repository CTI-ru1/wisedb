package eu.wisebed.wisedb.controller;

import com.mysql.jdbc.NotImplemented;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD operations for LastNodeReading entities.
 */
@SuppressWarnings("unchecked")
public class LastNodeReadingControllerImpl extends AbstractController<LastNodeReading> implements LastNodeReadingController {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastNodeReadingController ourInstance = null;
    /**
     * Capability literal.
     */
    private static final String CAPABILITY = "id";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LastNodeReadingControllerImpl.class);


    /**
     * Public constructor .
     */
    public LastNodeReadingControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * LastNodeReadingController is loaded on the first execution of
     * LastNodeReadingController.getInstance() or the first access to
     * LastNodeReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LastNodeReadingController getInstance() {
        synchronized (LastNodeReadingControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new LastNodeReadingControllerImpl();
            }
        }
        return ourInstance;
    }


    /**
     * Returns the last reading row inserted in the persistence for a specific node & capability.
     *
     * @param nodeCapability a nodeCapability instance.
     * @return the last node reading of a node for a capability.
     */
    public LastNodeReading getByID(final NodeCapability nodeCapability) {

        LOGGER.info("getByID(" + nodeCapability + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, nodeCapability.getId()));
        return (LastNodeReading) criteria.uniqueResult();
    }

    public List<LastNodeReading> getByCapability(final Setup setup, final Capability capability) {

        LOGGER.info("getByCapability(" + setup + "," + capability + ")");

        final List<NodeCapability> nodeCapabilities = CapabilityControllerImpl.getInstance().listNodeCapabilities(setup, capability);

        final List<LastNodeReading> result = new ArrayList<LastNodeReading>();

        for (final NodeCapability nodeCapability : nodeCapabilities) {
            result.add(nodeCapability.getLastNodeReading());
        }
        return result;
    }

    @Override
    public LastNodeReading getByNodeCapability(final Node node, final Capability capability) {

        LastNodeReading lnr = null;
        if (!node.getName().contains("virtual")) {
            return NodeCapabilityControllerImpl.getInstance().getByID(node, capability).getLastNodeReading();
        } else {

            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);
            for (Link link : links) {
                final LinkCapability lcap = LinkCapabilityControllerImpl.getInstance().getByID(link, "virtual");
                if (lcap != null && lcap.getLastLinkReading().getReading() == 1.0) {
                    NodeCapability cap = NodeCapabilityControllerImpl.getInstance().getByID(link.getTarget(), capability);
                    if (cap == null) continue;
                    if (lnr == null) {
                        lnr = cap.getLastNodeReading();
                    } else {
                        if (cap.getLastNodeReading().getTimestamp().after(lnr.getTimestamp())) {
                            lnr = cap.getLastNodeReading();
                        }
                    }
                }
            }

        }
        return lnr;
    }


    /**
     * Returns a list of last node reading entries for the nodes of a testbed.
     *
     * @param testbed , a testbed.
     * @return a list last node readings from a testbed's nodes
     */
    public List<LastNodeReading> getByTestbed(final Testbed testbed) throws NotImplemented {

        LOGGER.info("getByTestbed(" + testbed + ")");
        throw new NotImplemented();
    }

    public LastNodeReading getLast(final Node node, final String capabilityName) {
        final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capabilityName);
        return nodeCapability.getLastNodeReading();
    }
}
