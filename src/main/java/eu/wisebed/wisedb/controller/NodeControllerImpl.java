package eu.wisebed.wisedb.controller;

import com.sun.syndication.feed.module.georss.GeoRSSModule;
import com.sun.syndication.feed.module.georss.SimpleModuleImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import eu.wisebed.wisedb.controller.AbstractController;
import eu.wisebed.wisedb.Coordinate;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Position;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * CRUD operations for Node entities.
 */
@SuppressWarnings("unchecked")
public class NodeControllerImpl extends AbstractController<Node> implements NodeController {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeController ourInstance = null;

    /**
     * Setup literal.
     */
    private static final String SETUP = "setup";

    /**
     * ID literal.
     */
    private static final String ID = "id";

    /**
     * Node ID literal.
     */
    private static final String NODE_ID = "name";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeControllerImpl.class);
    /**
     * Capability literal.
     */
    private String NODE_CAPABILITY = "capability";


    /**
     * Public constructor .
     */
    public NodeControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * NodeController is loaded on the first execution of
     * NodeController.getInstance() or the first access to
     * NodeController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static NodeController getInstance() {
        synchronized (NodeControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new NodeControllerImpl();
            }
        }

        return ourInstance;
    }

//    /**
//     * Delete the input Node from the database.
//     *
//     * @param node the Node tha we want to delete
//     */
//    public void delete(final Node node) {
//        LOGGER.info("delete(" + node + ")");
//        super.delete(node, node.getId());
//    }

    /**
     * Delete the input Node from the database.
     *
     * @param nodeId the id of the node tha we want to delete
     */
    public void delete(final String nodeId) {
        LOGGER.info("delete(" + nodeId + ")");
        super.delete(new Node(), nodeId);
    }


    /**
     * Prepares and inserts a node to the testbed's setup with the id provided.
     *
     * @param testbed , a testbed instance.
     * @param nodeId  , a node id.
     * @return returns the inserted node instance.
     */
    public Node prepareInsertNode(final Testbed testbed, final String nodeId) {
        LOGGER.info("prepareInsertNode(" + testbed + "," + nodeId + ")");
        final Node node = new Node();
        node.setName(nodeId);
        node.setSetup(testbed.getSetup());
        add(node);

        return node;
    }

    /**
     * get the Nodes from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Node getByID(final String entityID) {
        LOGGER.debug("getByID(" + entityID + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(NODE_ID, entityID));
        return (Node) criteria.uniqueResult();
    }


    /**
     * Listing all the Nodes from the database.
     *
     * @return a list of all the entries that exist inside the table Node.
     */
    public List<Node> list() {
        LOGGER.info("list()");
        return super.list(new Node());
    }
//
//    /**
//     * Listing all the nodes from the database belonging to a selected testbed.
//     *
//     * @param testbed , a selected testbed.
//     * @return a list of testbed links.
//     */
//    public List<Node> list(final Testbed testbed) {
//        LOGGER.info("list(" + testbed + ")");
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Node.class);
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
//        criteria.addOrder(Order.asc(NODE_ID));
//        return (List<Node>) criteria.list();
//    }

    public List<Node> list(Setup setup) {
        LOGGER.debug("list(" + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.addOrder(Order.asc(NODE_ID));
        return (List<Node>) criteria.list();
    }

    /**
     * Listing all the nodes from the database belonging to a selected testbed.
     *
     * @param setup , a selected testbed.
     * @return a list of testbed links.
     */
    public List<String> listNames(final Setup setup) {
        LOGGER.info("list(" + setup + ")");
        long millis = System.currentTimeMillis();
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.setProjection(Projections.property("id"));
        final List res = criteria.list();
        LOGGER.info("return @ " + (System.currentTimeMillis() - millis));
        return (List<String>) res;
    }


//    public List<String> listNames(final int id) {
//        final Testbed testbed = TestbedController.getInstance().getByID(id);
//        LOGGER.info("list(" + testbed + ")");
//        long millis = System.currentTimeMillis();
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Node.class);
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
//        criteria.setProjection(Projections.property("id"));
//        final List res = criteria.list();
//        LOGGER.info("return @ " + (System.currentTimeMillis() - millis));
//        return (List<String>) res;
//    }


    /**
     * Listing all the nodes from the database belonging to a selected testbed.
     *
     * @param setup, a selected testbed setup.
     * @return a list of testbed links.
     */
    public Long count(final Setup setup) {
        LOGGER.debug("count(" + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public List<Node> list(final Setup setup, final Capability capability) {
        final List<Node> nodes = list(setup);
        final List<Node> result = new ArrayList<Node>();
        for (final Node node : nodes) {
            if (NodeCapabilityControllerImpl.getInstance().isAssociated(node, capability)) {
                result.add(node);
            }
        }
        return result;
    }

    public String getDescription(final Node node) {
        final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, "description");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(NODE_CAPABILITY, nodeCapability));
        try {
            Object obj = criteria.uniqueResult();

            if (obj instanceof NodeReading) {
                NodeReading reading = (NodeReading) obj;
                return reading.getStringReading();
            }
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
        return "";
    }

    public Position getPosition(final Node node) {
        final Position position = new Position();

        position.setX(NodeCapabilityControllerImpl.getInstance().getByID(node, "x").getLastNodeReading().getReading().floatValue());
        position.setY(NodeCapabilityControllerImpl.getInstance().getByID(node, "y").getLastNodeReading().getReading().floatValue());
        position.setZ(NodeCapabilityControllerImpl.getInstance().getByID(node, "z").getLastNodeReading().getReading().floatValue());
        position.setTheta(NodeCapabilityControllerImpl.getInstance().getByID(node, "theta").getLastNodeReading().getReading().floatValue());
        position.setPhi(NodeCapabilityControllerImpl.getInstance().getByID(node, "phi").getLastNodeReading().getReading().floatValue());

        return position;
    }

    public Origin getOrigin(final Node node) {
        return node.getSetup().getOrigin();
    }


    public String getGeooRssFeed(final Node node, final String syndFeedLink, final String syndEntryLink) {

        final Testbed testbed = node.getSetup().getTestbed();

        // set up feed and entries
        final SyndFeed feed = new SyndFeedImpl();

        feed.setFeedType("rss_2.0");
        feed.setTitle(node.getId() + " GeoRSS feed");
        feed.setDescription(testbed.getDescription());
        feed.setLink(syndFeedLink);
        final List<SyndEntry> entries = new ArrayList<SyndEntry>();

        // set entry's title,link and publishing date
        final SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(node.getName());
        entry.setLink(syndEntryLink);
        entry.setPublishedDate(new Date());

        // set entry's description (HTML list)
        final SyndContent description = new SyndContentImpl();
        final StringBuilder descriptionBuffer = new StringBuilder();
        descriptionBuffer.append("<p>").append(getDescription(node)).append("</p>");
        descriptionBuffer.append("<ul>");
        for (NodeCapability capability : NodeCapabilityControllerImpl.getInstance().list(node)) {
            descriptionBuffer.append("<li>").append(capability.getCapability().getName()).append(":");
            if (capability.getLastNodeReading().getStringReading() != null) {
                descriptionBuffer.append(capability.getLastNodeReading().getReading()).append(",")
                        .append(capability.getLastNodeReading().getStringReading());
            } else {
                descriptionBuffer.append(capability.getLastNodeReading().getReading());
            }
            descriptionBuffer.append("</li>");
        }
        descriptionBuffer.append("</ul>");
        description.setType("text/html");
        description.setValue(descriptionBuffer.toString());
        entry.setDescription(description);

        Position nodePos = getPosition(node);

        // set the GeoRSS module and add it
        final GeoRSSModule geoRSSModule = new SimpleModuleImpl();
        if (testbed.getSetup().getCoordinateType().equals("Absolute")) {
            com.sun.syndication.feed.module.georss.geometries.Position position =
                    new com.sun.syndication.feed.module.georss.geometries.Position();

            position.setLatitude(nodePos.getX());
            position.setLongitude(nodePos.getY());
            geoRSSModule.setPosition(position);
        } else {

            // convert testbed origin from long/lat position to xyz if needed
            final Origin origin = testbed.getSetup().getOrigin();

            final Coordinate originCoordinate = new Coordinate((double) origin.getX(), (double) origin.getY(),
                    (double) origin.getZ(), (double) origin.getPhi(), (double) origin.getTheta());
            final Coordinate properOrigin = Coordinate.blh2xyz(originCoordinate);

            // convert node position from xyz to long/lat

            final Coordinate nodeCoordinate = new Coordinate((double) nodePos.getX(),
                    (double) nodePos.getY(), (double) nodePos.getZ());
            final Coordinate rotated = Coordinate.rotate(nodeCoordinate, properOrigin.getPhi());
            final Coordinate absolute = Coordinate.absolute(properOrigin, rotated);
            final Coordinate coordinate = Coordinate.xyz2blh(absolute);
            geoRSSModule.setPosition(new com.sun.syndication.feed.module.georss.geometries.Position(coordinate.getX(), coordinate.getY()));

        }
        entry.getModules().add(geoRSSModule);
        entries.add(entry);

        // add entries to feed
        feed.setEntries(entries);

        // the feed output goes to response            
        final SyndFeedOutput output = new SyndFeedOutput();
        StringWriter writer = new StringWriter();
        try {
            output.output(feed, writer);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (FeedException e) {
            LOGGER.error(e);
        }
        return writer.toString();

    }
//
//    /**
//     * Listing all nodes that have the given capability.
//     *
//     * @param capability , a capability.
//     * @return a list of nodes that share the given capability.
//     */
//    public List<Node> listCapabilityNodes(final Capability capability) {
//        LOGGER.info("listCapabilityNodes(" + capability + ")");
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Node.class);
//        criteria.createAlias(CAPABILITIES, "caps")
//                .add(Restrictions.eq("caps.name", capability.getName()));
//        criteria.addOrder(Order.asc(NODE_ID));
//        return (List<Node>) criteria.list();
//    }

//    /**
//     * Listing all nodes that have the given capability.
//     *
//     * @param capability a capability.
//     * @param testbed    a testbed.
//     * @return a list of nodes that share the given capability belonging to the same testbed.
//     */
//    public List<Node> listCapabilityNodes(final Capability capability, final Testbed testbed) {
//        LOGGER.info("listCapabilityNodes(" + capability + "," + testbed + ")");
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Node.class);
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
//        criteria.createAlias(CAPABILITIES, "caps")
//                .add(Restrictions.eq("caps.name", capability.getName()));
//        criteria.addOrder(Order.asc(NODE_ID));
//        return (List<Node>) criteria.list();
//    }

//    /**
//     * Checks if a capability and a node are associated.
//     *
//     * @param capability , capability.
//     * @param testbed    , testbed.
//     * @param node       , node .
//     * @return
//     */
//    public boolean isAssociated(final Capability capability, final Testbed testbed, final Node node) {
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Node.class);
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
//        criteria.createAlias(CAPABILITIES, "caps").add(Restrictions.eq("caps.name", capability.getName()));
//        criteria.add(Restrictions.eq(NODE_ID, node.getId()));
//        return criteria.list().size() > 0;
//    }

}
