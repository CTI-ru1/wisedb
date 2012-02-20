package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Position;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 11:58 AM
 */
public interface NodeController  {

    public void update(final Node node);

    public void delete(final String nodeId);

    public Node getByID(final String nodeId);

    public List<Node> list();

    public List<Node> list(final Setup setup);

    public List<String> listNames(final Setup setup);

    public Long count(final Setup setup);

    public List<Node> list(final Setup setup, final Capability capability);

    public String getDescription(final Node node);

    public Position getPosition(final Node node);

    public Origin getOrigin(final Node node);

    Node prepareInsertNode(final Testbed testbed, final String nodeId);

    public void setSessionFactory(final SessionFactory factory);

    public void add(final Node node);


}
