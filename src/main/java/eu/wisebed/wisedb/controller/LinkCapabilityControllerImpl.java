package eu.wisebed.wisedb.controller;

import eu.uberdust.caching.EvictCache;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CRUD operations for LinkCapabilites entities.
 */
@SuppressWarnings("unchecked")
public class LinkCapabilityControllerImpl extends AbstractController<LinkCapability> implements LinkCapabilityController {


    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkCapabilityController ourInstance = null;

    /**
     * Capabilities literal.
     */
    private static final String CAPABILITY = "capability";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkCapabilityControllerImpl.class);
    private static final String LINK = "link";


    /**
     * Public constructor .
     */
    public LinkCapabilityControllerImpl() {
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
    public static LinkCapabilityController getInstance() {
        synchronized (LinkCapabilityControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = (LinkCapabilityController) new LinkCapabilityControllerImpl();
            }
        }

        return ourInstance;
    }

    /**
     * Prepares and inserts a capability to the persistnce with the provided capability name.
     *
     * @param capabilityName , a capability name.
     * @return returns the inserted capability instance.
     */
    @EvictCache(cacheName = "eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl.list")
    public LinkCapability prepareInsertLinkCapability(final Link link, final String capabilityName) {
        LOGGER.info("prepareInsertLinkCapability(" + capabilityName + ")");

        Capability capability = CapabilityControllerImpl.getInstance().getByID(capabilityName);
        if (capability == null) {
            capability = CapabilityControllerImpl.getInstance().prepareInsertCapability(capabilityName);
        }

        LinkCapability linkCapability = new LinkCapability();

        linkCapability.setCapability(capability);
        linkCapability.setLink(link);

        final LastLinkReading lastLinkReading = new LastLinkReading();
//        linkCapability.setLastLinkReading(lastLinkReading);

        LinkCapabilityControllerImpl.getInstance().add(linkCapability);

        lastLinkReading.setLinkCapability(linkCapability);
        lastLinkReading.setTimestamp(new Date());
        lastLinkReading.setReading(null);
        lastLinkReading.setStringReading(null);
        LastLinkReadingControllerImpl.getInstance().add(lastLinkReading);

        return linkCapability;
    }

    public boolean isAssociated(final Link link, final Capability capability) {
        LOGGER.debug("isAssociated(" + link + "," + capability + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, link));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        criteria.setProjection(Projections.rowCount());
        try {
            if ((Long) criteria.uniqueResult() > 0) {
                return true;
            }
        } catch (java.lang.ClassCastException ex) {
            if ((Integer) criteria.uniqueResult() > 0) {
                return true;
            }
        }
        return false;

    }

    public void delete(final Link link, final Capability capability) {

        LOGGER.info("delete(" + link.getSource() + "--" + link.getTarget() + "," + capability.getName() + ")");

        final Session session = getSessionFactory().getCurrentSession();
        final LinkCapability linkCapabilities = new LinkCapability();
        linkCapabilities.setCapability(capability);
        linkCapabilities.setLink(link);
        session.delete(linkCapabilities);
    }

    @Override
    public void delete(int id) {
        super.delete(new LinkCapability(), id);
    }
//
//    public void add(final Link link, final Capability capability) {
//
//        LOGGER.info("add(" + link + "," + capability + ")");
//
//        final Session session = getSessionFactory().getCurrentSession();
//        final LinkCapability linkCapability = new LinkCapability();
//        linkCapability.setCapability(capability);
//        linkCapability.setLink(link);
//        add(linkCapability);
//    }

    public int count() {
        LOGGER.info("count()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.uniqueResult();
    }

    public List<Capability> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        final List<Capability> capabilities = new ArrayList<Capability>();
        for (Object obj : criteria.list()) {
            if (obj instanceof LinkCapability) {
                final LinkCapability cap = (LinkCapability) obj;
                final Capability capability = cap.getCapability();

                if (capability != null) {
                    capabilities.add(capability);
                }
            }
        }
        return capabilities;
    }

    public LinkCapability getByID(final int linkId) {
        LOGGER.info("getByID(" + linkId + ")");
        return super.getByID(new LinkCapability(), linkId);
    }

    public LinkCapability getByID(final Link link, final Capability capability) {
        LOGGER.info("getByID(" + link + "," + capability + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, link));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (LinkCapability) criteria.uniqueResult();
    }

    public LinkCapability getByID(final Link link, final String capabilityName) {
        final Capability capability = CapabilityControllerImpl.getInstance().getByID(capabilityName);
        LOGGER.debug("getByID(" + link + "," + capabilityName + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, link));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (LinkCapability) criteria.uniqueResult();
    }

    public List<LinkCapability> getByIDs(final List<Link> links, final String capabilityName) {
        final Capability capability = CapabilityControllerImpl.getInstance().getByID(capabilityName);
        LOGGER.debug("getByIDs(," + capabilityName + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.in(LINK, links));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (List<LinkCapability>) criteria.list();
    }

    public List<LinkCapability> list(final Link link) {
        LOGGER.debug("list(" + link.getSource() + "--" + link.getTarget() + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, link));
        final List<LinkCapability> capabilities = new ArrayList<LinkCapability>();
        for (Object obj : criteria.list()) {
            if (obj instanceof LinkCapability) {
                capabilities.add((LinkCapability) obj);
            }
        }
        return capabilities;
    }

    public List<LinkCapability> list(final Setup setup) {
        LOGGER.debug("list(" + setup + ")");
        final List<Link> links = LinkControllerImpl.getInstance().list(setup);
        if (!links.isEmpty()) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(LinkCapability.class);
            criteria.add(Restrictions.in(LINK, links));
            criteria.addOrder(Order.asc(LINK));
            return (List<LinkCapability>) criteria.list();
        }

        return null;
    }

}
