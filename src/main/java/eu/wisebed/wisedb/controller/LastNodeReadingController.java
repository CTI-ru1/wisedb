package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 1:00 PM
 */
public interface LastNodeReadingController extends AbstractControllerInterface {
    public boolean delete(final int id);

    public LastNodeReading getByID(final NodeCapability nodeCapability);

    public List<LastNodeReading> getByCapability(final Setup setup, final Capability capability);

    public void add(final LastNodeReading lastNodeReading);

    public LastNodeReading getByNodeCapability(final Node node, final Capability capability);
}
