package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.User;
import eu.wisebed.wisedb.model.UserRole;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * CRUD operations for NodeCapabilities entities.
 */
@SuppressWarnings("unchecked")
public class UserControllerImpl extends AbstractController<User> implements UserController {


    /**
     * static instance(ourInstance) initialized as null.
     */
    private static UserController ourInstance = null;

    private static final String USERNAME = "username";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserControllerImpl.class);

    private Set<Integer> formStrings;
    private Random rand;

    /**
     * Public constructor .
     */
    public UserControllerImpl() {
        // Does nothing
        super();
        formStrings = new HashSet<Integer>();
    }

    /**
     * NodeCapabilityController is loaded on the first execution of
     * NodeCapabilityController.getInstance() or the first access to
     * NodeCapabilityController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static UserController getInstance() {
        synchronized (UserControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new UserControllerImpl();
            }
        }
        return ourInstance;
    }

    @Override
    public void add(User user, int uuid) throws Exception {
        if (formStrings.contains(uuid)) {
            formStrings.remove(uuid);
            super.add(user);

            UserRole role = new UserRole();
            role.setAuthority("ROLE_USER");
            role.setUser(user);
            UserRoleControllerImpl.getInstance().add(role);
        } else {
            throw new Exception();
        }
    }

    @Override
    public int getFormInt() {
        if (rand == null) {
            rand = new Random();
        }
        final int value = (new Integer(rand.nextInt())).hashCode();
        formStrings.add(value);
        return value;
    }

    @Override
    public void delete(int id) {
        LOGGER.info("delete(" + id + ")");
        super.delete(new User(), id);
    }


    public List<User> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(User.class);
        return criteria.list();
    }


    public User getByID(int entityId) {
        LOGGER.info("getByID(" + entityId + ")");
        return super.getByID(new User(), entityId);
    }

    @Override
    public User getByUsername(String username) {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq(USERNAME, username));
        return (User) criteria.uniqueResult();
    }

    @Override
    public List<UserRole> listRoles(User user) {
        return UserRoleControllerImpl.getInstance().list(user);
    }
}
