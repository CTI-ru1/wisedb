package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.User;
import eu.wisebed.wisedb.model.UserRole;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for NodeCapabilities entities.
 */
@SuppressWarnings("unchecked")
public class UserRoleControllerImpl extends AbstractController<UserRole> implements UserRoleController {


    /**
     * static instance(ourInstance) initialized as null.
     */
    private static UserRoleController ourInstance = null;

    private static final String USERNAME = "username";
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserRoleControllerImpl.class);

    /**
     * Public constructor .
     */
    public UserRoleControllerImpl() {
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
    public static UserRoleController getInstance() {
        synchronized (UserRoleControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new UserRoleControllerImpl();
            }
        }
        return ourInstance;
    }

    @Override
    public void delete(int id) {
        LOGGER.info("delete(" + id + ")");
        super.delete(new UserRole(), id);
    }

    @Override
    public boolean isAdmin(User user) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(UserRole.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("authority", "ROLE_ADMIN"));
        return criteria.list().size() > 0;
    }


    public List<UserRole> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(User.class);
        return criteria.list();
    }

    public List<UserRole> list(User user) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(UserRole.class);
        criteria.add(Restrictions.eq("user", user));
        return criteria.list();
    }


    public UserRole getByID(int entityId) {
        LOGGER.info("getByID(" + entityId + ")");
        return super.getByID(new UserRole(), entityId);
    }
}
