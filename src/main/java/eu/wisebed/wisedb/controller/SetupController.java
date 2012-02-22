package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Setup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 11:48 AM
 */
public interface SetupController  extends AbstractControllerInterface{

    public void delete(final int id);

    public Setup getByID(final int id);

    public List<Setup> list();

    public void add(final Setup setup);
}
