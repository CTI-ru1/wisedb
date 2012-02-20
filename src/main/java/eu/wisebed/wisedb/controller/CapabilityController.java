package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:12 PM
 */
public interface CapabilityController {

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

    public void setSessionFactory(final SessionFactory factory);

    public void add(final Capability capability);
}
