package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * CRUD operations for LinkReading entities.
 */
@SuppressWarnings("unchecked")
public class LinkReadingControllerImpl extends AbstractController<LinkReading> implements LinkReadingController {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkReadingController ourInstance = null;

    /**
     * Source literal.
     */
    private static final String SOURCE = "link_source";
    /**
     * Target literal.
     */
    private static final String TARGET = "link_target";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkReadingControllerImpl.class);
    private static final String CAPABILITY = "capability";

    /**
     * Public constructor .
     */
    public LinkReadingControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * LinkReadingController is loaded on the first execution of
     * LinkReadingController.getInstance() or the first access to
     * LinkReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LinkReadingController getInstance() {
        synchronized (LinkReadingControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = (LinkReadingController) new LinkReadingControllerImpl();
            }
        }

        return ourInstance;
    }

    public LinkReading getByID(final int id) {
        return super.getByID(new LinkReading(), id);
    }

    /**
     * Listing all the LinkReadings from the database.
     *
     * @return a list of all the entries that exist inside the table LinkReadings.
     */
    public List<LinkReading> list() {
        LOGGER.info("list()");
        return super.list(new LinkReading());
    }

    public List<LinkReading> list(final Link link) {
        LOGGER.info("list(" + link.getSource() + "--" + link.getTarget() + ")");
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.eq(SOURCE, link.getSource()));
        criteria.add(Restrictions.eq(TARGET, link.getTarget()));
        return criteria.list();

    }

    /**
     * Deleting an entry into the database.
     *
     * @param readingId , id of a reading entry
     */
    public void delete(final int readingId) {
        LOGGER.info("delete(" + readingId + ")");
        super.delete(new LinkReading(), readingId);
    }


    /**
     * Insert a links's reading from it's capabilities and make the appropriate  associations.
     *
     * @param sourceId       , link's source id.
     * @param targetId       , target's source id.
     * @param capabilityName , capability's id.
     * @param testbedId      , a testbed id.
     * @param stringReading  , value of a string reading.
     * @param doubleReading  , value of a sensor reading.
     * @param timestamp      , a timestamp.
     * @throws UnknownTestbedException exception that occurs when the urnPrefix is unknown
     */
    public void insertReading(final String sourceId, final String targetId, final String capabilityName,
                              final int testbedId, final Double doubleReading, final String stringReading,
                              final Date timestamp) throws UnknownTestbedException {

        LOGGER.info("insertReading(" + sourceId + "," + targetId + "," + capabilityName + "," + testbedId
                + "," + doubleReading + "," + stringReading + "," + timestamp + ")");

        // look for testbed
        final Testbed testbed = TestbedControllerImpl.getInstance().getByID(testbedId);
        if (testbed == null) {
            throw new UnknownTestbedException(Integer.toString(testbedId));
        }

        // look for source
        final Node source = NodeControllerImpl.getInstance().getByName(sourceId);
        if (source == null) {
            // if source node not found in db make it and store it
            LOGGER.info("Node [" + sourceId + "] was not found in db . Storing it");
            NodeControllerImpl.getInstance().prepareInsertNode(testbed, sourceId);
        }

        // look for target
        final Node target = NodeControllerImpl.getInstance().getByName(targetId);
        if (target == null) {
            // if target node not found in db make it and store it
            LOGGER.info("Node [" + targetId + "] was not found in db . Storing it");
            NodeControllerImpl.getInstance().prepareInsertNode(testbed, targetId);
        }

        Link link = LinkControllerImpl.getInstance().getByID(sourceId, targetId);

        if (link == null) {
            LOGGER.debug("link==null");
            link = LinkControllerImpl.getInstance().prepareInsertLink(testbed.getSetup(), sourceId, targetId);
        }

        LinkCapability linkCapability = LinkCapabilityControllerImpl.getInstance().getByID(link, capabilityName);

        if (linkCapability == null) {
            linkCapability = LinkCapabilityControllerImpl.getInstance().prepareInsertLinkCapability(link, capabilityName);
        }

        // make a new link reading entity
        final LinkReading reading = new LinkReading();
        reading.setCapability(linkCapability);
        reading.setReading(doubleReading);
        reading.setStringReading(stringReading);
        reading.setTimestamp(timestamp);

        // add reading
        LinkReadingControllerImpl.getInstance().add(reading);


        // get last link reading for link and capability if not found create one
        LastLinkReading lastLinkReading;
        if (linkCapability.getLastLinkReading() == null) {
            // if last link reading was not found
            LOGGER.info("Last link reading for LinkCapability [" + linkCapability.toString() + "] created");
            lastLinkReading = new LastLinkReading();
            lastLinkReading.setReading(doubleReading);
            lastLinkReading.setStringReading(stringReading);
            lastLinkReading.setTimestamp(timestamp);
            lastLinkReading.setId(linkCapability.getId());

            linkCapability.setLastLinkReading(lastLinkReading);

            LinkCapabilityControllerImpl.getInstance().update(linkCapability);
        } else {
            lastLinkReading = linkCapability.getLastLinkReading();
            lastLinkReading.setReading(doubleReading);
            lastLinkReading.setStringReading(stringReading);
            lastLinkReading.setTimestamp(timestamp);
            lastLinkReading.setLinkCapability(linkCapability);

            linkCapability.setLastLinkReading(lastLinkReading);

            LinkCapabilityControllerImpl.getInstance().update(linkCapability);
        }

    }

    /**
     * Returns the readings count for a link.
     *
     * @param link , a link .
     * @return the count of this link.
     */
    public int count(final Link link) {
        LOGGER.info("count(" + link + ")");
        final List<LinkCapability> linkCapabilities = LinkCapabilityControllerImpl.getInstance().list(link);
        Integer result = 0;
        if (!linkCapabilities.isEmpty()) {
            final org.hibernate.Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(LinkReading.class);
            criteria.add(Restrictions.in(CAPABILITY, linkCapabilities));
            criteria.setProjection(Projections.rowCount());
            final Long count = (Long) criteria.uniqueResult();
            result += count.intValue();
        }

        return result.intValue();

    }

    public Long count() {
        LOGGER.info("count()");
        Criteria criteria = null;
        final Session session = getSessionFactory().getCurrentSession();
        criteria = session.createCriteria(LinkReading.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
