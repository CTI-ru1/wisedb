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
public interface NodeReadingController {

    public void add(final NodeReading reading);

    public void delete(final String readingId);

    public NodeReading getByID(final int id);

    public List<NodeReading> list();

    public Long count();

    public Long count(final Node node);


    public void insertReading(final String nodeId, final String capabilityName, final int testbedId,
                              final Double doubleReading, final String stringReading, final Date timestamp) throws UnknownTestbedException;

    public List<NodeReading> listNodeReadings(final Node node, final Capability capability);

    public List<NodeReading> listNodeReadings(final Node node, final Capability capability, final int limit);

    public HashMap<Capability, Integer> getNodeReadingsCountMap(final Node node);

//    public void setSessionFactory(final SessionFactory factory);


}
