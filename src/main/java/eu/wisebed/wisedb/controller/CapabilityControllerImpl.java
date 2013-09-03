package eu.wisebed.wisedb.controller;

import eu.uberdust.caching.Cachable;
import eu.uberdust.caching.EvictCache;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


/**
 * CRUD operations for Capability entities.
 */
@SuppressWarnings("unchecked")
public class CapabilityControllerImpl extends AbstractController<Capability> implements CapabilityController {
    /**
     * Unit literal.
     */
    private static final String UNIT = "UNIT";

    /**
     * Datatype literal.
     */
    private static final String DATATYPE = "DATATYPE";

    /**
     * Default value literal.
     */
    private static final String DEFAULT_VALUE = "DEFAULT_VALUE";
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static CapabilityController ourInstance = null;

    /**
     * Capability name literal.
     */
    private static final String CAPABILITY_NAME = "name";
    /**
     * Link literal.
     */
    private static final String LINK = "link";
    /**
     * Node literal.
     */
    private static final String NODE = "node";
    /**
     * Capability literal.
     */
    private static final String CAPABILITY = "capability";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CapabilityControllerImpl.class);

    /**
     * Public constructor .
     */
    public CapabilityControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * CapabilityController is loaded on the first execution of
     * CapabilityController.getInstance() or the first access to
     * CapabilityController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static CapabilityController getInstance() {
        synchronized (CapabilityControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new CapabilityControllerImpl();
            }
        }
        return ourInstance;
    }

    /**
     * Prepares and inserts a capability to the persistence with the provided capability name.
     *
     * @param capabilityName , a capability name.
     * @return returns the inserted capability instance.
     */
    @EvictCache(cacheName = "eu.wisebed.wisedb.controller.CapabilityControllerImpl.list")
    public Capability prepareInsertCapability(final String capabilityName) {

        LOGGER.info("prepareInsertCapability(" + capabilityName + ")");
        final Capability capability = new Capability();
        capability.setName(capabilityName);
        capability.setDatatype(DATATYPE);
        capability.setDefaultvalue(DEFAULT_VALUE);
        capability.setUnit(UNIT);
        add(capability);

        return capability;
    }

    /**
     * Stores the capability provided in the parameters.
     *
     * @param entity a Capability object.
     */
    public void add(final Capability entity) {
        LOGGER.info("add(" + entity + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Capability entity2 = (Capability) session.get(Capability.class, entity.getName());
        if (entity2 == null) {
            session.save(entity);
        } else {
            session.merge(entity2);
        }
    }

    /**
     * Retrieve the Capabilities from the database that corresponds to the input id.
     *
     * @param entityID the id of the entity instance.
     * @return an Entity object.
     */
    public Capability getByID(final String entityID) {
        LOGGER.debug("getByID(" + entityID + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Capability.class);
        criteria.add(Restrictions.eq(CAPABILITY_NAME, entityID));

        final List list = criteria.list();
        if (!list.isEmpty()) {
            return (Capability) list.get(0);
        }
        return null;
    }

    /**
     * Deleting a capability entry from the database.
     *
     * @param entityID the id of the Entity object.
     */
    public void delete(final String entityID) {
        LOGGER.info("delete(" + entityID + ")");
        super.delete(new Capability(), entityID);
    }

    /**
     * Listing all capabilities from the database.
     *
     * @return a list of all capabilities persisted.
     */
    @Cachable
    public List<Capability> list() {
        LOGGER.info("list()");
        return super.list(new Capability());
    }

    /**
     * Listing all the capabilities from the database belonging to a selected setup.
     *
     * @param setup a selected setup instance.
     * @return a list of setup capabilities.
     */
    @Cachable
    public List<Capability> list(final Setup setup) {
        LOGGER.info("list(" + setup + ")");
        final List<Capability> capabilities = listNodeCapabilities(setup);
        capabilities.addAll(listLinkCapabilities(setup));
        return capabilities;
    }


    /**
     * Listing all the capabilities from the database belonging to a selected node.
     *
     * @param node a selected node instance.
     * @return a list of capabilities.
     */
    @Cachable
    public List<Capability> list(final Node node) {
        LOGGER.info("list(" + node + ")");

        final List<Capability> result = new ArrayList<Capability>();

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        criteria.add(Restrictions.eq(NODE, node));
        final List resList = criteria.list();
        for (Object item : resList) {
            if (item instanceof NodeCapability) {
                result.add(((NodeCapability) item).getCapability());
            }
        }
        return result;
    }

    @Override
    public boolean hasCapability(Node node, Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return criteria.uniqueResult() != null;
    }

    /**
     * Listing all the capabilities from the database belonging to a selected link.
     *
     * @param link a selected link instance.
     * @return a list of capabilities.
     */
    @Cachable
    public List<Capability> list(final Link link) {
        LOGGER.info("list(" + link + ")");

        final List<Capability> result = new ArrayList<Capability>();

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, link));
        final List resList = criteria.list();
        for (Object item : resList) {
            if (item instanceof LinkCapability) {
                result.add(((LinkCapability) item).getCapability());
            }
        }
        return result;
    }

    /**
     * Listing all the nodeCapabilities as Capabilities from the database belonging to a selected setup.
     *
     * @param setup a selected setup instance.
     * @return a list of capabilities.
     */
    @Cachable
    public List<Capability> listNodeCapabilities(final Setup setup) {
        LOGGER.info("listNodeCapabilities(" + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        String hql = "SELECT new eu.wisebed.wisedb.model.Capability(name,datatype,unit,defaultvalue,description,maxvalue,minvalue) FROM eu.wisebed.wisedb.model.Capability WHERE capability_id in ( select capability from eu.wisebed.wisedb.model.NodeCapability where node_id in (select name from eu.wisebed.wisedb.model.Node where setup_id=:setupid) )";
        Query query = session.createQuery(hql);
        query.setParameter("setupid", setup.getId());

        return query.list();
    }

    /**
     * Listing all the linkCapabilities as Capabilities from the database belonging to a selected setup.
     *
     * @param setup a selected setup instance.
     * @return a list of capabilities.
     */
    @Cachable
    public List<Capability> listLinkCapabilities(final Setup setup) {
        LOGGER.info("listNodeCapabilities(" + setup + ")");
        final Session session = getSessionFactory().getCurrentSession();
        String hql = "SELECT new eu.wisebed.wisedb.model.Capability(name,datatype,unit,defaultvalue,description,maxvalue,minvalue) FROM eu.wisebed.wisedb.model.Capability WHERE capability_id in ( select capability from eu.wisebed.wisedb.model.LinkCapability where link in (select id from eu.wisebed.wisedb.model.Link where source in ( select name from eu.wisebed.wisedb.model.Node where setup_id=:setupid ) ) )";
        Query query = session.createQuery(hql);
        query.setParameter("setupid", setup.getId());

        return query.list();
    }

    /**
     * Listing all the nodeCapabilities from the database belonging to a selected setup.
     *
     * @param setup a selected setup instance.
     * @return a list of nodeCapabilities.
     */
    @Cachable
    public List<NodeCapability> listNodeCapabilities(final Setup setup, final Capability capability) {
        LOGGER.info("listNodeCapabilities(" + setup + "," + capability + ")");
        final List<Node> nodes = NodeControllerImpl.getInstance().list(setup);
        final List<NodeCapability> result = new ArrayList<NodeCapability>();

        if (!nodes.isEmpty()) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeCapability.class);
            criteria.add(Restrictions.in(NODE, nodes));
            criteria.add(Restrictions.eq(CAPABILITY, capability));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof NodeCapability) {
                    result.add((NodeCapability) item);
                }
            }
        }
        return result;
    }

    /**
     * Listing all the linkCapabilities as Capabilities from the database belonging to a selected setup.
     *
     * @param setup a selected setup instance.
     * @return a list of linkCapabilities.
     */
    @Cachable
    public List<LinkCapability> listLinkCapabilities(final Setup setup, final Capability capability) {
        LOGGER.info("listLinkCapabilities(" + setup + "," + capability + ")");
        final List<Link> links = LinkControllerImpl.getInstance().list(setup);
        final List<LinkCapability> result = new ArrayList<LinkCapability>();

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.in(LINK, links));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        final List resList = criteria.list();
        LOGGER.info("nodesC=" + resList.size());

        for (Object item : resList) {
            if (item instanceof LinkCapability) {
                result.add((LinkCapability) item);
            }
        }

        return result;
    }
}
