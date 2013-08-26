package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:21 PM
 */
public interface ScheduleController extends AbstractControllerInterface {
    public void add(final Schedule schedule);

    public void delete(final int id);

    public void update(final Schedule schedule);

    public List<Schedule> list();

    public Schedule getByID(int entityId);
}
