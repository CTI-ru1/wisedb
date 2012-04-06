package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Setup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:06 PM
 */
public interface LinkController extends AbstractControllerInterface {
    public void add(final Link link);

    public void delete(final int id);

    public Link getByID(final int linkId);

    public List<Link> getBySource(final Node source);

    public List<Link> getByTarget(final Node target);

    public Link getByID(final String sourceId, final String targetId);

    public void delete(final String sourceId, final String targetId);

    public List<Link> list();

    public List<Link> list(final Setup setup);

    public Long count(final Setup setup);

    public List<Link> listCapabilityLinks(final Capability capability, final Setup setup);

    public List<String> listLinkCapabilities(final Link link);

    public List<Link> list(final Setup setup, final Capability capability);

    public Link prepareInsertLink(final String sourceId, final String targetId) throws UnknownTestbedException;

    public void update(final Link link);

}
