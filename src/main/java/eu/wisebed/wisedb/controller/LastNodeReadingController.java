package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 1:00 PM
 */
public interface LastNodeReadingController {

    public LastNodeReading getByID(final NodeCapability nodeCapability);

    public List<LastNodeReading> getByCapability(final Setup setup, final Capability capability);

    public void add(final LastNodeReading lastNodeReading);

//    public void setSessionFactory(final SessionFactory factory);
}
