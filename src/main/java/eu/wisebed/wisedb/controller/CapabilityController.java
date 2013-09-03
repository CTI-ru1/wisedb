package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:12 PM
 */
public interface CapabilityController extends AbstractControllerInterface {

    public Capability getByID(final String entityID);

    public void delete(final String entityID);

    public List<Capability> list();

    public List<Capability> list(final Setup setup);

    public List<Capability> list(final Node node);

    public List<Capability> list(final Link link);

    public List<Capability> listNodeCapabilities(final Setup setup);

    public List<Capability> listLinkCapabilities(final Setup setup);

    public List<NodeCapability> listNodeCapabilities(final Setup setup, final Capability capability);

    public List<LinkCapability> listLinkCapabilities(final Setup setup, final Capability capability);

    public Capability prepareInsertCapability(final String capabilityName);

    public boolean hasCapability(final Node node, final Capability capability);

    public void add(final Capability capability);
}
