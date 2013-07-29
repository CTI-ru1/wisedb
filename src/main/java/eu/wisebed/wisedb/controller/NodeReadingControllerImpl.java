package eu.wisebed.wisedb.controller;

import eu.uberdust.caching.Cachable;
import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * CRUD operations for NodeReadings entities.
 */
@SuppressWarnings("unchecked")
public class NodeReadingControllerImpl extends AbstractController<NodeReading> implements NodeReadingController {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeReadingController ourInstance = null;

    /**
     * Capability literal.
     */
    private static final String CAPABILITY = "capability";

    /**
     * Timestamp literal.
     */
    private static final String TIMESTAMP = "timestamp";
    private static final String READING = "reading";

    private static final String ID = "id";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeReadingControllerImpl.class);

    /**
     * Public constructor .
     */
    public NodeReadingControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * NodeReadingController is loaded on the first execution of
     * NodeReadingController.getInstance() or the first access to
     * NodeReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static NodeReadingController getInstance() {
        synchronized (NodeReadingControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = (NodeReadingController) new NodeReadingControllerImpl();
            }
        }

        return ourInstance;
    }

    /**
     * Listing all the NodeReadings from the database.
     *
     * @return a list of all the entries that exist inside the table NodeReadings.
     */
    public List<NodeReading> list() {
        LOGGER.info("list()");
        return super.list(new NodeReading());
    }

    public Long count() {
        LOGGER.info("count()");
        Criteria criteria = null;
        final Session session = getSessionFactory().getCurrentSession();
        criteria = session.createCriteria(NodeReading.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    /**
     * Delete the node reading from the database.
     *
     * @param readingId the id of the node tha we want to delete
     */
    public void delete(final int readingId) {
        LOGGER.info("delete(" + readingId + ")");
        super.delete(new NodeReading(), readingId);
    }


    /**
     * Insert a node's reading from it's capabilities and make the appropriate associations.
     *
     * @param nodeId         , a node id.
     * @param capabilityName , a capability name.
     * @param dReading       , a reading value (double).
     * @param sReading       , a reading value (string).
     * @param timestamp      , a timestamp.
     * @throws UnknownTestbedException exception that occurs when the testbedId is unknown.
     */
    public NodeReading insertReading(final String nodeId, final String capabilityName,
                                     final Double dReading, String sReading, final Date timestamp)
            throws UnknownTestbedException {
        if (nodeId.contains("\\000")) return null;
        if (capabilityName.contains("\\000")) return null;
        NodeReading reading = null;
        try {
            LOGGER.info("insertReading(" + nodeId + "," + capabilityName + "," + dReading + ","
                    + sReading + "," + timestamp + ")");

            Node node = NodeControllerImpl.getInstance().getByName(nodeId);

            NodeCapability nodeCapability;
            if (node == null) {
                LOGGER.debug("node==null");
                node = NodeControllerImpl.getInstance().prepareInsertNode(nodeId);
                nodeCapability = NodeCapabilityControllerImpl.getInstance().prepareInsertNodeCapability(capabilityName, node);
            } else {
                nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capabilityName);
                if (nodeCapability == null) {
                    nodeCapability = NodeCapabilityControllerImpl.getInstance().prepareInsertNodeCapability(capabilityName, node);
                    NodeControllerImpl.getInstance().update(node);
                }
            }

            // make a new node reading entity
            reading = new NodeReading();
            reading.setReading(dReading);
            reading.setStringReading(sReading);
            if (timestamp.getTime() == 0) {
                reading.setTimestamp(new Date());
            } else {
                reading.setTimestamp(timestamp);
            }
            reading.setCapability(nodeCapability);

            // add reading
            NodeReadingControllerImpl.getInstance().add(reading);

            LOGGER.info("nodeCapability id is : " + nodeCapability.getId());

            // get lastNodeReading if not found create one
            LastNodeReading lastNodeReading;
            if (nodeCapability.getLastNodeReading() == null) {

                LOGGER.info("created lastNodeReading for " + nodeCapability);
                lastNodeReading = new LastNodeReading();
                lastNodeReading.setReading(dReading);
                if (dReading != null) {
                    sReading = dReading.toString();
                }
                lastNodeReading.setStringReading(sReading);
                if (timestamp.getTime() == 0) {
                    lastNodeReading.setTimestamp(new Date());
                } else {
                    lastNodeReading.setTimestamp(timestamp);
                }
                lastNodeReading.setId(nodeCapability.getId());

                nodeCapability.setLastNodeReading(lastNodeReading);

                NodeCapabilityControllerImpl.getInstance().update(nodeCapability);

            } else {
                LOGGER.info("found lastNodeReading for " + nodeCapability);
                lastNodeReading = nodeCapability.getLastNodeReading();
                lastNodeReading.setReading(dReading);
                if (dReading != null) {
                    sReading = dReading.toString();
                }
                lastNodeReading.setStringReading(sReading);
                if (timestamp.getTime() == 0) {
                    lastNodeReading.setTimestamp(new Date());
                } else {
                    lastNodeReading.setTimestamp(timestamp);
                }
                lastNodeReading.setNodeCapability(nodeCapability);

                nodeCapability.setLastNodeReading(lastNodeReading);

                NodeCapabilityControllerImpl.getInstance().update(nodeCapability);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reading;
    }


    /**
     * Return list of readings for a selected node and capability.
     *
     * @param node       , node of a testbed.
     * @param capability , capability of a node
     * @return a list with nodereadings for a node/capability combination
     */
    @SuppressWarnings("unchecked")
    public List<NodeReading> listNodeReadings(final Node node, final Capability capability) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + ")");
        final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
        criteria.addOrder(Order.desc(TIMESTAMP));
        return (List<NodeReading>) criteria.list();
    }

    /**
     * Return a limited list of readings for a selected node and capability.
     *
     * @param node       , node of a testbed.
     * @param capability , capability of a node
     * @param limit      , an integer that express the number of records to be returned.
     * @return a list with nodereadings for a node/capability combination
     */
    @SuppressWarnings("unchecked")
    public List<NodeReading> listNodeReadings(final Node node, final Capability capability, final int limit) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + limit + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);

            return (List<NodeReading>) criteria.list();
        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);
            return (List<NodeReading>) criteria.list();
        }

    }

    @Override
    public Double maxIn(Node node, Capability capability, int limit) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + limit + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);
            criteria.setProjection(Projections.max("reading"));
            return (Double) criteria.uniqueResult();
        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);
            criteria.setProjection(Projections.max("reading"));
            return (Double) criteria.uniqueResult();
        }
    }
    @Override
    public Double minIn(Node node, Capability capability, int limit) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + limit + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);
            criteria.setProjection(Projections.min("reading"));
            return (Double) criteria.uniqueResult();
        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);
            criteria.setProjection(Projections.min("reading"));
            return (Double) criteria.uniqueResult();
        }
    }
    @Override
    public Double avgIn(Node node, Capability capability, int limit) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + limit + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);
            criteria.setProjection(Projections.avg("reading"));
            return (Double) criteria.uniqueResult();
        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setMaxResults(limit);
            criteria.setProjection(Projections.avg("reading"));
            return (Double) criteria.uniqueResult();
        }
    }

    /**
     * Return a limited list of readings for a selected node and capability.
     *
     * @param node       , node of a testbed.
     * @param capability , capability of a node
     * @param from       , a long number indicating the date from
     * @param to         , a long number indicating the date to
     * @return a list with nodereadings for a node/capability combination
     */
    @SuppressWarnings("unchecked")
    public List<NodeReading> listNodeReadings(final Node node, final Capability capability, final long from, final long to) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + from + " , " + to + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));

        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));
        }
        return (List<NodeReading>) criteria.list();
    }

    @Override
    public Double maxByDate(Node node, Capability capability, long from, long to) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + from + " , " + to + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setProjection(Projections.max(READING));

        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setProjection(Projections.max(READING));
        }
        return (Double) criteria.uniqueResult();
    }
    @Override
    public Double minByDate(Node node, Capability capability, long from, long to) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + from + " , " + to + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setProjection(Projections.min(READING));

        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setProjection(Projections.min(READING));
        }
        return (Double) criteria.uniqueResult();
    }
    @Override
    public Double avgByDate(Node node, Capability capability, long from, long to) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + from + " , " + to + ")");
        Criteria criteria = null;
        if (node.getName().contains("virtual")) {
            List<Link> links = LinkControllerImpl.getInstance().getBySource(node);

            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);


            final List<LinkCapability> lcap = LinkCapabilityControllerImpl.getInstance().getByIDs(links, "virtual");
            final List<Node> nodes = new ArrayList<Node>();

            for (LinkCapability linkCapability : lcap) {
                if (linkCapability.getLastLinkReading().getReading() == 1.0) {
                    nodes.add(linkCapability.getLink().getTarget());
                }
            }

            List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().getByIDs(nodes, capability);
            nodeCapabilities.add(nodeCapability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setProjection(Projections.avg(READING));

        } else {
            final NodeCapability nodeCapability = NodeCapabilityControllerImpl.getInstance().getByID(node, capability);

            final Session session = getSessionFactory().getCurrentSession();
            criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            if (to == 0) {
                criteria.add(Restrictions.ge(TIMESTAMP, new Date(from)));
            } else if (from == 0) {
                criteria.add(Restrictions.le(TIMESTAMP, new Date(to)));
            } else {
                criteria.add(Restrictions.between(TIMESTAMP, new Date(from), new Date(to)));
            }
            criteria.addOrder(Order.desc(TIMESTAMP));
            criteria.setProjection(Projections.avg(READING));
        }
        return (Double) criteria.uniqueResult();
    }

    /**
     * Returns the readings count for a node.
     *
     * @param node , a node .
     * @return the count of this node.
     */
    public Long count(final Node node) {
        LOGGER.info("count(" + node + ")");
        final List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(node);

        Integer result = 0;
        if (nodeCapabilities.size() > 0) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            criteria.setProjection(Projections.rowCount());
            final Long count = (Long) criteria.uniqueResult();
            result += count.intValue();
        }
        return result.longValue();
    }

    /**
     * Returns the readings count for a node per capability.
     *
     * @param node , a node .
     * @return a map containing readings of a node per capability
     */
    @SuppressWarnings("unchecked")
    public HashMap<Capability, Integer> getNodeReadingsCountMap(final Node node) {
        LOGGER.info("getNodeReadingsCountMap(" + node + ")");
        final HashMap<Capability, Integer> resultMap = new HashMap<Capability, Integer>();

        final List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(node);
        for (NodeCapability nodeCapability : nodeCapabilities) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.rowCount())
            );
            final Long count = (Long) criteria.uniqueResult();
            resultMap.put(nodeCapability.getCapability(), count.intValue());
        }
        return resultMap;
    }

    public NodeReading getByID(final int id) {
        LOGGER.info("getByID(" + id + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(ID, id));
        criteria.setMaxResults(1);
        return (NodeReading) criteria.uniqueResult();
    }

    @Cachable
    public Date getFirstReading() {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.addOrder(Order.asc(TIMESTAMP));
        criteria.setMaxResults(1);
        final NodeReading reading = (NodeReading) criteria.uniqueResult();
        return reading.getTimestamp();
    }

    public Date getLastReading() {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.addOrder(Order.desc(TIMESTAMP));
        criteria.setMaxResults(1);
        final NodeReading reading = (NodeReading) criteria.uniqueResult();
        return reading.getTimestamp();
    }
}
