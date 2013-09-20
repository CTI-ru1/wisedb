package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Statistics;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * CRUD operations for NodeCapabilities entities.
 */
@SuppressWarnings("unchecked")
public class StatisticsControllerImpl extends AbstractController<Statistics> implements StatisticsController {


    /**
     * static instance(ourInstance) initialized as null.
     */
    private static StatisticsController ourInstance = null;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(StatisticsControllerImpl.class);
    private static final String URL = "url";
    private static final String DATE = "date";

    private Random rand;

    /**
     * Public constructor .
     */
    public StatisticsControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * NodeCapabilityController is loaded on the first execution of
     * NodeCapabilityController.getInstance() or the first access to
     * NodeCapabilityController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static StatisticsController getInstance() {
        synchronized (StatisticsControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new StatisticsControllerImpl();
            }
        }
        return ourInstance;
    }

    @Override
    public void delete(int id) {
        LOGGER.info("delete(" + id + ")");
        super.delete(new Statistics(), id);
    }


    public List<Statistics> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Statistics.class);
        return criteria.list();
    }


    public Statistics getByID(int entityId) {
        LOGGER.info("getByID(" + entityId + ")");
        return super.getByID(new Statistics(), entityId);
    }

    @Override
    public List<Statistics> list(String url) {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Statistics.class);
        criteria.add(Restrictions.eq(URL, url));
        return criteria.list();
    }

    @Override
    public List<Statistics> list(Date from, Date to) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Statistics.class);
        criteria.add(Restrictions.le(DATE, to));
        criteria.add(Restrictions.ge(DATE, from));
        return criteria.list();
    }

    @Override
    public List<Statistics> list(String url, Date from, Date to) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Statistics.class);
        criteria.add(Restrictions.le(URL, url));
        criteria.add(Restrictions.le(DATE, to));
        criteria.add(Restrictions.ge(DATE, from));
        return criteria.list();
    }
}
