package eu.wisebed.wisedb.controller;

import eu.uberdust.caching.Cachable;
import eu.uberdust.caching.EvictCache;
import eu.wisebed.wisedb.Coordinate;
import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
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
    private String CAPABILITY = "capability";
    /**
     * NodeCapability literal.
     */
    private String NODE_CAPABILITY = "nodeCapability";


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
    public void delete(final int nodeId) {
        LOGGER.info("delete(" + nodeId + ")");
        super.delete(new Node(), nodeId);
    }


    /**
     * Prepares and inserts a node to the testbed's setup with the id provided.
     *
     * @param nodeId , a node id.
     * @return returns the inserted node instance.
     */
    @EvictCache(cacheName = "eu.wisebed.wisedb.controller.NodeControllerImpl.list,eu.wisebed.wisedb.controller.NodeControllerImpl.count")
    public Node prepareInsertNode(final String nodeId) throws UnknownTestbedException {
        LOGGER.info("prepareInsertNode(" + nodeId + ")");

        final List<Testbed> testbeds = TestbedControllerImpl.getInstance().list();

        for (final Testbed testbed : testbeds) {
            if (nodeId.startsWith(testbed.getUrnPrefix())) {
                final Node node = new Node();
                node.setName(nodeId);
                node.setSetup(testbed.getSetup());
                add(node);
                return node;
            }
        }

        throw new UnknownTestbedException();
    }

    /**
     * get the Nodes from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Node getByID(final int entityID) {
        LOGGER.debug("getByID(" + entityID + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(ID, entityID));
        return (Node) criteria.uniqueResult();
    }

    /**
     * get the Nodes from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Node getByName(final String entityID) {
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
    @Cachable
    public List<Node> list(final Setup setup) {
        LOGGER.debug("list(" + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.addOrder(Order.asc(NODE_ID));
        return (List<Node>) criteria.list();
    }

    //     *
//     * Listing all the nodes from the database belonging to a selected testbed.
    @Override
    public List<Node> getRealNodes(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        final List<Link> links = LinkControllerImpl.getInstance().getBySource(node);
        for (Link link : links) {
            LinkCapability linkCap = LinkCapabilityControllerImpl.getInstance().getByID(link, "virtual");
            if (linkCap != null && linkCap.getLastLinkReading().getReading() > 0) {
                nodes.add(link.getTarget());
            }
        }
        return nodes;

    }

//    /**
//     * @param setup , a selected testbed.
//     * @return a list of testbed links.
//     */
//    public List<String> listNames(final Setup setup) {
//        LOGGER.info("list(" + setup + ")");
//        long millis = System.currentTimeMillis();
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Node.class);
//        criteria.add(Restrictions.eq(SETUP, setup));
//        criteria.setProjection(Projections.property("name"));
//        final List res = criteria.list();
//        LOGGER.info("return @ " + (System.currentTimeMillis() - millis));
//        return (List<String>) res;
//    }


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
    @Cachable
    public Long count(final Setup setup) {
        LOGGER.debug("count(" + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Cachable
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
        if (nodeCapability == null) {
            return "";
        }
        LastNodeReading lnr = LastNodeReadingControllerImpl.getInstance().getByID(nodeCapability);
        if (lnr == null) {
            return "";
        }
        return lnr.getStringReading();
    }

    public Position getPosition(final Node node) {
        final Position position = new Position();

        try {
            position.setX(NodeCapabilityControllerImpl.getInstance().getByID(node, "x").getLastNodeReading().getReading().floatValue());
            position.setY(NodeCapabilityControllerImpl.getInstance().getByID(node, "y").getLastNodeReading().getReading().floatValue());
            position.setZ(NodeCapabilityControllerImpl.getInstance().getByID(node, "z").getLastNodeReading().getReading().floatValue());
            position.setTheta(node.getSetup().getOrigin().getTheta());
            position.setPhi(node.getSetup().getOrigin().getPhi());

        } catch (NullPointerException n) {
            position.setX(node.getSetup().getOrigin().getX());
            position.setY(node.getSetup().getOrigin().getY());
            position.setZ(node.getSetup().getOrigin().getZ());
            position.setPhi(node.getSetup().getOrigin().getPhi());
            position.setTheta(node.getSetup().getOrigin().getTheta());
        }
        return position;
    }

    @Cachable
    @Override
    public Position getAbsolutePosition(Node node) {
        Position nodePosition = getPosition(node);
        final Setup setup = node.getSetup();

        if (!(setup.getCoordinateType().equals("Absolute"))) {
            // determine testbed origin by the type of coordinates given
            final Origin origin = setup.getOrigin();
            Coordinate originCoordinate = new Coordinate();
            originCoordinate.setX((double) origin.getX());
            originCoordinate.setY((double) origin.getY());
            originCoordinate.setZ((double) origin.getZ());
            originCoordinate.setPhi((double) origin.getPhi());
            originCoordinate.setTheta((double) origin.getTheta());
            Coordinate properOrigin = Coordinate.blh2xyz(originCoordinate);

            Position testbedPosition = new Position();
            testbedPosition.setX(setup.getOrigin().getX());
            testbedPosition.setY(setup.getOrigin().getY());
            testbedPosition.setZ(setup.getOrigin().getZ());
            testbedPosition.setPhi(setup.getOrigin().getPhi());
            testbedPosition.setTheta(setup.getOrigin().getTheta());

            Coordinate nodeCoordinate = new Coordinate();
            nodeCoordinate.setX((double) nodePosition.getX());
            nodeCoordinate.setY((double) nodePosition.getY());
            nodeCoordinate.setZ((double) nodePosition.getZ());

            final Coordinate rotated = Coordinate.rotate(nodeCoordinate, properOrigin.getPhi());
            final Coordinate absolute = Coordinate.absolute(properOrigin, rotated);
            final Coordinate finalNodePosition = Coordinate.xyz2blh(absolute);
            nodePosition.setX(Float.parseFloat(finalNodePosition.getX().toString()));
            nodePosition.setY(Float.parseFloat(finalNodePosition.getY().toString()));
        }
        return nodePosition;
    }

    public Origin getOrigin(final Node node) {
        return node.getSetup().getOrigin();
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
