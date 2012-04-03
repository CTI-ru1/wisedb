package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Setup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:37 PM
 */
public interface LinkCapabilityController extends AbstractControllerInterface {

    public void add(final LinkCapability linkCapability);

    public void delete(final Link link, final Capability capability);

    public void delete(final int id);


    public LinkCapability getByID(int linkId);

    public LinkCapability getByID(final Link link, final Capability capability);

    public LinkCapability getByID(final Link link, final String capabilityName);

    public List<Capability> list();

    public List<LinkCapability> list(final Link link);

    public List<LinkCapability> list(final Setup setup);

    public int count();

    public boolean isAssociated(final Link link, final Capability capability);

    public LinkCapability prepareInsertLinkCapability(final Link link, final String capabilityName);

    public void update(final LinkCapability linkCapability);
}
