package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.User;
import eu.wisebed.wisedb.model.UserRole;
import eu.wisebed.wisedb.model.VirtualNodeDescription;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:21 PM
 */
public interface VirtualNodeDescriptionController extends AbstractControllerInterface {
    public void add(final VirtualNodeDescription user);

    public void delete(final int id);

    public void update(final VirtualNodeDescription user);

    public List<VirtualNodeDescription> list();
    public String rebuild(int testbedId);

    public VirtualNodeDescription getByID(int entityId);

    public VirtualNodeDescription getByUsername(String username);

}
