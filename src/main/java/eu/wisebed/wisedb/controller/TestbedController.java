package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 11:29 AM
 */
public interface TestbedController {

    public void delete(final int id);

    public Testbed getByID(final int id);

    public List<Testbed> list();

    public Testbed getByUrnPrefix(final String urnPrefix);

    public Testbed getByName(final String name);

    public Map<String, Long> countNodes();

    public Map<String, Long> countLinks();

    public void setSessionFactory(final SessionFactory factory);

    public void add(final Testbed testbed);

    public void update(final Testbed testbed);
}
