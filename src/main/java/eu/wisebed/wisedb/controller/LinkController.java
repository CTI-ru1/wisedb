package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Setup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:06 PM
 */
public interface LinkController {

    public Link getByID(final int id);

    public Link getByID(final String sourceId, final String targetId);

    public void delete(final String sourceId, final String targetId);

    public List<Link> list();

    public List<Link> list(final Setup setup);

    public Long count(final Setup setup);

    public List<Link> listCapabilityLinks(final Capability capability, final Setup setup);

    public List<String> listLinkCapabilities(final Link link);

    public List<Link> list(final Setup setup, final Capability capability);

    public Link prepareInsertLink(final Setup setup, final String sourceId, final String targetId);

//    public void setSessionFactory(final SessionFactory factory);

    public void update(final Link link);

}
