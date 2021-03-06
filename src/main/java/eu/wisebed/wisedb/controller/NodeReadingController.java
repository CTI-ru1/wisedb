package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeReading;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:42 PM
 */
public interface NodeReadingController extends AbstractControllerInterface {

    public void add(final NodeReading reading);

    public void delete(final int readingId);

    public NodeReading getByID(final int id);

    public List<NodeReading> list();

    public Long count();

    public Long count(final Node node);


    public NodeReading insertReading(final String node, final String capability, final Double dReading,
                                     final String sReading, final Date time) throws UnknownTestbedException;

    public List<NodeReading> listNodeReadings(final Node node, final Capability capability);

    public List<NodeReading> listNodeReadings(final Node node, final Capability capability, final int limit);
    public Double maxIn(final Node node, final Capability capability, final int limit);
    public Double minIn(final Node node, final Capability capability, final int limit);
    public Double avgIn(final Node node, final Capability capability, final int limit);


    public List<NodeReading> listNodeReadings(final Node node, final Capability capability, final long from, final long to);
    public Double maxByDate(final Node node, final Capability capability, final long from, final long to);
    public Double minByDate(final Node node, final Capability capability, final long from, final long to);
    public Double avgByDate(final Node node, final Capability capability, final long from, final long to);


    public HashMap<Capability, Integer> getNodeReadingsCountMap(final Node node);

    public Date getLastReading();

    public Date getFirstReading();
}