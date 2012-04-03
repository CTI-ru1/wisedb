package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:21 PM
 */
public interface NodeCapabilityController extends AbstractControllerInterface {
    public void add(final NodeCapability nodeCapability);

    public void delete(final int id);

    public void update(final NodeCapability nodeCapability);

    public long count();

    public List<Capability> list();

    public NodeCapability getByID(int entityId);

    public NodeCapability getByID(final Node node, final String capabilityName);

    public NodeCapability getByID(final Node node, final Capability capability);

    public List<NodeCapability> list(final Node node);

    public List<NodeCapability> list(final Setup setup);

    public List<NodeCapability> list(final Setup setup, final Capability capability);

    public boolean isAssociated(final Node node, final Capability capability);

    public NodeCapability prepareInsertNodeCapability(final String capabilityName, final Node node);

}
