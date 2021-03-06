package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkReading;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:52 PM
 */
public interface LinkReadingController extends AbstractControllerInterface {

    public void add(final LinkReading reading);


    public void delete(final int readingId);

    public LinkReading getByID(final int id);

    public List<LinkReading> list();

    public List<LinkReading> list(final Link link);

    public List<LinkReading> list(final Link link, final Capability capability, final int limit);

    public void insertReading(final String sourceId, final String targetId, final String capabilityName,
                              final Double doubleReading, final String stringReading,
                              final Date timestamp) throws UnknownTestbedException;

    public Long count();

    public int count(final Link link);
}
