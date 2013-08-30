package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.User;
import eu.wisebed.wisedb.model.UserRole;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:21 PM
 */
public interface UserController extends AbstractControllerInterface {
    public void add(final User user);

    public void add(final User user, int uuid) throws Exception;

    public void delete(final int id);

    public void update(final User user);

    public List<User> list();

    public User getByID(int entityId);

    public User getByUsername(String username);

    public List<UserRole> listRoles(User user);

    public int getFormInt();
}
