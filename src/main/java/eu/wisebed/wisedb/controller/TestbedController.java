package eu.wisebed.wisedb.controller;

import com.googlecode.ehcache.annotations.Cacheable;
import com.sun.syndication.feed.module.georss.GeoRSSModule;
import com.sun.syndication.feed.module.georss.SimpleModuleImpl;
import com.sun.syndication.feed.module.georss.geometries.Position;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import eu.wisebed.wisedb.Coordinate;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * CRUD operations for Testbed entities.
 */
public class TestbedController extends AbstractController<Testbed> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static TestbedController ourInstance = null;

    /**
     * UrnPrefix literal.
     */
    private static final String URN_PREFIX = "urnPrefix";

    /**
     * Name literal.
     */
    private static final String NAME = "name";
    /**
     * Setup literal.
     */
    private static final String SETUP = "setup";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TestbedController.class);


    /**
     * Public constructor .
     */
    public TestbedController() {
        // Does nothing
        super();
    }

    /**
     * TestbedController is loaded on the first execution of
     * TestbedController.getInstance() or the first access to
     * TestbedController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static TestbedController getInstance() {
        synchronized (TestbedController.class) {
            if (ourInstance == null) {
                ourInstance = new TestbedController();
            }
        }
        return ourInstance;
    }

    /**
     * get the Testbed from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    @Cacheable(cacheName = "getById")
    public Testbed getByID(final int entityID) {
        LOGGER.info("getByID(" + entityID + ")");
        return super.getByID(new Testbed(), entityID);
    }

    /**
     * Delete the input Testbed from the database.
     *
     * @param value the Testbed tha we want to delete
     */
    public void delete(final Testbed value) {
        LOGGER.info("delete(" + value + ")");
        super.delete(value, value.getId());
    }

    /**
     * Listing all the Testbeds from the database.
     *
     * @return a list of all the entries that exist inside the table Testbed.
     */
    public List<Testbed> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Testbed.class);
        final List z = criteria.list();
        return z;
    }

    /**
     * Return the Testbed from the database that has the given urn prefix.
     *
     * @param urnPrefix the Urn prefix of the Testbed object.
     * @return a Testbed object.
     */
    @SuppressWarnings("unchecked")
    @Cacheable(cacheName = "testbedbyprefix")
    public Testbed getByUrnPrefix(final String urnPrefix) {
        LOGGER.info("getByUrnPrefix(" + urnPrefix + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Testbed.class);
        criteria.add(Restrictions.like(URN_PREFIX, urnPrefix, MatchMode.START));
        criteria.addOrder(Order.asc(URN_PREFIX));
        criteria.setMaxResults(1);
        return (Testbed) criteria.uniqueResult();
    }


    /**
     * Return the Testbed from the database that has the given urn prefix.
     *
     * @param testbedName the name of a Testbed object.
     * @return a Testbed object.
     */
    public Testbed getByName(final String testbedName) {
        LOGGER.info("getByName(" + testbedName + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Testbed.class);
        criteria.add(Restrictions.eq(NAME, testbedName));
        Object obj = criteria.uniqueResult();
        return (Testbed) obj;
    }

    /**
     * Returns the number of nodes in database for each setup
     *
     * @return map containing the setups and node count
     */
    public Map<String, Long> countNodes() {
        LOGGER.info("countNodes()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        final ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty(SETUP));
        projList.add(Projections.rowCount());
        criteria.setProjection(projList);

        final List results = criteria.list();
        final Iterator iter = results.iterator();
        if (!iter.hasNext()) {
            LOGGER.debug("No objects to display.");
            return null;
        }
        final Map<String, Long> resultsMap = new HashMap<String, Long>();
        while (iter.hasNext()) {

            final Object[] obj = (Object[]) iter.next();
            final Setup setup = (Setup) obj[0];
            final long count = (Long) obj[1];
            resultsMap.put(getBySetup(setup).getName(), count);

        }

        return resultsMap;
    }

    /**
     * Returns the number of links in database for each setup
     *
     * @return map containing the setups and link count
     */
    public Map<String, Long> countLinks() {
        LOGGER.info("countLinks()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Link.class);
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty(SETUP));
        projList.add(Projections.rowCount());
        criteria.setProjection(projList);

        final List results = criteria.list();
        final Iterator iter = results.iterator();
        if (!iter.hasNext()) {
            LOGGER.debug("No objects to display.");
            return null;
        }
        final Map<String, Long> resultsMap = new HashMap<String, Long>();
        while (iter.hasNext()) {

            final Object[] obj = (Object[]) iter.next();
            final Setup setup = (Setup) obj[0];
            final long count = (Long) obj[1];
            resultsMap.put(getBySetup(setup).getName(), count);

        }

        return resultsMap;
    }

    /**
     * Get the testbed related to the given setup.
     *
     * @param setup the setup in question.
     * @return the testbed requested.
     */
    public Testbed getBySetup(final Setup setup) {
        return setup.getTestbed();
    }

    public String getGeoRssFeed(final Testbed testbed, final String baseUrl, final String syndFeedLink, final String syndEntryLink) {

        final SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle(testbed.getName() + " GeoRSS");
        feed.setLink(syndFeedLink);
        feed.setDescription(testbed.getDescription());
        final List<SyndEntry> entries = new ArrayList<SyndEntry>();

        // convert testbed origin from long/lat position to xyz if needed
        Coordinate properOrigin = null;
        if (!(testbed.getSetup().getCoordinateType().equals("Absolute"))) {
            // determine testbed origin by the type of coordinates given
            final Origin origin = testbed.getSetup().getOrigin();
            final Coordinate originCoordinate = new Coordinate((double) origin.getX(), (double) origin.getY(),
                    (double) origin.getZ(), (double) origin.getPhi(), (double) origin.getTheta());
            properOrigin = Coordinate.blh2xyz(originCoordinate);
        }

        // list of nodes
        final List<Node> nodes = NodeController.getInstance().list(testbed);

        // make an entry and it
        for (Node node : nodes) {
            final SyndEntry entry = new SyndEntryImpl();

            // set entry's title,link and publishing date
            entry.setTitle(node.getId());
            entry.setLink(new StringBuilder().append(baseUrl).append("/rest/testbed/")
                    .append(testbed.getId()).append("/node/").append(node.getId()).toString());
            entry.setPublishedDate(new Date());

            // set entry's description (HTML list)
            final SyndContent description = new SyndContentImpl();
            final StringBuilder descriptionBuffer = new StringBuilder();
            descriptionBuffer.append("<p>").append(NodeController.getInstance().getDescription(node)).append("</p>");
            descriptionBuffer.append("<p><a href=\"").append(baseUrl).append("/uberdust/rest/testbed/")
                    .append(testbed.getId()).append("/node/").append(node.getId()).append("/georss").append("\">")
                    .append("GeoRSS feed").append("</a></p>");
            descriptionBuffer.append("<ul>");
            for (NodeCapability capability : (List<NodeCapability>) NodeCapabilityController.getInstance().list(node)) {
                descriptionBuffer.append("<li>").append(capability.getCapability().getName())
                        .append(capability.getCapability().getName()).append("</li>");
            }
            descriptionBuffer.append("</ul>");
            description.setType("text/html");
            description.setValue(descriptionBuffer.toString());
            entry.setDescription(description);


            // set the GeoRSS module and add it to entry
            final GeoRSSModule geoRSSModule = new SimpleModuleImpl();
            if (!(testbed.getSetup().getCoordinateType().equals("Absolute"))) {
                // convert node position from xyz to long/lat

                final Origin npos = NodeController.getInstance().getOrigin(node);

                final Coordinate nodeCoordinate = new Coordinate((double) npos.getX(), (double) npos.getY(),
                        (double) npos.getZ());
                final Coordinate rotated = Coordinate.rotate(nodeCoordinate, properOrigin.getPhi());
                final Coordinate absolute = Coordinate.absolute(properOrigin, rotated);
                final Coordinate nodePosition = Coordinate.xyz2blh(absolute);
                geoRSSModule.setPosition(new Position(nodePosition.getX(), nodePosition.getY()));
            } else {
                geoRSSModule.setPosition(new Position(NodeController.getInstance().getOrigin(node).getX(), NodeController.getInstance().getOrigin(node).getY()));
            }
            entry.getModules().add(geoRSSModule);
            entries.add(entry);
        }

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
}
