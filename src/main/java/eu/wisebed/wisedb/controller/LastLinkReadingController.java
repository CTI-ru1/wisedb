package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Setup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:57 PM
 */
public interface LastLinkReadingController extends AbstractControllerInterface{

    public LastLinkReading getByID(final LinkCapability linkCapability);

    public List<LastLinkReading> getByCapability(final Setup setup, Capability capability);

    public void add(LastLinkReading lastLinkReading);
}
