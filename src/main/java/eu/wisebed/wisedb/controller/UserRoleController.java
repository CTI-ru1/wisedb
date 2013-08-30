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
public interface UserRoleController extends AbstractControllerInterface {
    public void add(final UserRole user);

    public void delete(final int id);

    public void update(final UserRole userRole);

    public List<UserRole> list();

    public List<UserRole> list(User user);

    public UserRole getByID(int entityId);
}
