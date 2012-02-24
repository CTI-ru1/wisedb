package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD operations for Link entities.
 */
@SuppressWarnings("unchecked")
public class LinkControllerImpl extends AbstractController<Link> implements LinkController {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkController ourInstance = null;

    /**
     * Source literal.
     */
    private static final String SOURCE = "source";
    /**
     * Target literal.
     */
    private static final String TARGET = "target";
    /**
     * Setup literal.
     */
    private static final String SETUP = "setup";

    /**
     * Capabilities literal.
     */
    private static final String CAPABILITIES = "capabilities";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkControllerImpl.class);


    /**
     * Public constructor .
     */
    public LinkControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * LinkController is loaded on the first execution of
     * LinkController.getInstance() or the first access to
     * LinkController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LinkController getInstance() {
        synchronized (LinkControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new LinkControllerImpl();
            }
        }

        return ourInstance;
    }

    /**
     * Prepares and inserts a link to the testbed setup  with the provided ids as source and target.
     *
     * @param setup    , a setup instance.
     * @param sourceId , a source node id.
     * @param targetId , a target node id.
     * @return returns the inserted link instance.
     */
    public Link prepareInsertLink(final Setup setup, final String sourceId, final String targetId) {
        LOGGER.info("prepareInsertLink(" + setup + "," + sourceId + "," + targetId + ")");

        final Link link = new Link();
        link.setSource(NodeControllerImpl.getInstance().getByName(sourceId));
        link.setTarget(NodeControllerImpl.getInstance().getByName(targetId));
        LOGGER.info(setup);
        link.setSetup(setup);
        add(link);

        return link;
    }

    /**
     * Get the entry from the link that corresponds to the input id, Source & Target node ids.
     *
     * @param sourceId , The node id of the link's source.
     * @param targetId , The node id of the link's target.
     * @return the link object persisted with the specific id
     */
    public Link getByID(final String sourceId, final String targetId) {

        LOGGER.info("getByID(" + sourceId + "," + targetId + ")");

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Link.class);
        final Node source = NodeControllerImpl.getInstance().getByName(sourceId);
        final Node target = NodeControllerImpl.getInstance().getByName(targetId);
        criteria.add(Restrictions.eq("source", source));
        criteria.add(Restrictions.eq("target", target));

        return (Link) criteria.uniqueResult();
    }

    /**
     * Get the entry from the link that corresponds to the input id, Source & Target node ids.
     *
     * @param id, The link id.
     * @return the link object persisted with the specific id
     */
    public Link getByID(final int id) {

        LOGGER.info("getByID(" + id + ")");
        return super.getByID(new Link(), id);
    }

    /**
     * Deleting a link entry from the database.
     *
     * @param sourceId , The node id of the link's source.
     * @param targetId , The node id of the link's target.
     */
    public void delete(final String sourceId, final String targetId) {

        LOGGER.info("delete(" + sourceId + "," + targetId + ")");

        final Session session = getSessionFactory().getCurrentSession();
        final Link linkWithId = new Link();
        linkWithId.setSource(NodeControllerImpl.getInstance().getByName(sourceId));
        linkWithId.setTarget(NodeControllerImpl.getInstance().getByName(targetId));
        session.delete(linkWithId);
    }

    /**
     * Listing all the Links from the database.
     *
     * @return a list of all the entries that exist inside the table Link.
     */
    public List<Link> list() {
        LOGGER.info("list()");
        return super.list(new Link());
    }

    public List<Link> list(final Setup setup) {
        LOGGER.debug("list(" + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Link.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        return (List<Link>) criteria.list();
    }

    /**
     * Count all the links from the database belonging to a selected setup.
     *
     * @param setup , a selected setup.
     * @return the number of links.
     */
    public Long count(final Setup setup) {
        LOGGER.debug("count(" + setup + ")");
        final org.hibernate.classic.Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Link.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    /**
     * Listing all links that have the given capability.
     *
     * @param capability a capability.
     * @param setup      a setup.
     * @return a list of links that share the given capability belonging to the same setup.
     */
    public List<Link> listCapabilityLinks(final Capability capability, final Setup setup) {
        LOGGER.info("listCapabilityLinks(" + capability + "," + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Link.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.createAlias(CAPABILITIES, "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc(SOURCE));
        return (List<Link>) criteria.list();
    }

//    public boolean isAssociated(Capability capability, Testbed testbed, Link link) {
//        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Link.class);
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
//        criteria.createAlias(CAPABILITIES, "caps").add(Restrictions.eq("caps.name", capability.getName()));
//        criteria.add(Restrictions.eq(SOURCE, link.getSource()));
//        criteria.add(Restrictions.eq(TARGET, link.getTarget()));
//        return criteria.list().size() > 0;
//    }

    public List<String> listLinkCapabilities(final Link link) {
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(SOURCE, link.getSource()));
        criteria.add(Restrictions.eq(TARGET, link.getTarget()));
        criteria.setProjection(Projections.property("capability_id"));
        return criteria.list();
    }


    public List<Link> list(final Setup setup, final Capability capability) {
        final List<Link> links = list(setup);
        final List<Link> result = new ArrayList<Link>();
        for (final Link link : links) {
            if (LinkCapabilityControllerImpl.getInstance().isAssociated(link, capability)) {
                result.add(link);
            }
        }
        return result;
    }
}
