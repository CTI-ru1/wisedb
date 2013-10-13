package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Schedule;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for NodeCapabilities entities.
 */
@SuppressWarnings("unchecked")
public class ScheduleControllerImpl extends AbstractController<Schedule> implements ScheduleController {


    /**
     * static instance(ourInstance) initialized as null.
     */
    private static ScheduleController ourInstance = null;

    /**
     * Source literal.
     */
    private static final String NODE = "node";

    private static final String USERNAME = "username";
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ScheduleControllerImpl.class);

    /**
     * Public constructor .
     */
    public ScheduleControllerImpl() {
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
    public static ScheduleController getInstance() {
        synchronized (ScheduleControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new ScheduleControllerImpl();
            }
        }
        return ourInstance;
    }

    @Override
    public void delete(int id) {
        LOGGER.info("delete(" + id + ")");
        super.delete(new Schedule(), id);
    }


    public List<Schedule> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Schedule.class);
        return criteria.list();
    }

    @Override
    public List<Schedule> list(Setup setup, String username) {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Schedule.class);
        criteria.add(Restrictions.eq(USERNAME, username));
        return criteria.list();
    }

    @Override
    public List<Schedule> list(String username) {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Schedule.class);
        criteria.add(Restrictions.eq(USERNAME, username));
        return criteria.list();
    }

    public Schedule getByID(int entityId) {
        LOGGER.info("getByID(" + entityId + ")");
        return super.getByID(new Schedule(), entityId);
    }

}
