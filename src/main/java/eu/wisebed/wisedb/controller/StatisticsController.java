package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Statistics;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 12:21 PM
 */
public interface StatisticsController extends AbstractControllerInterface {
    public void add(final Statistics statistics);

    public void delete(final int id);

    public List<Statistics> list();

    public Statistics getByID(int entityId);

    public List<Statistics> list(String url);

    public List<Statistics> list(Date from, Date to);

    List<Statistics> list(String url, Date from, Date to);
}
