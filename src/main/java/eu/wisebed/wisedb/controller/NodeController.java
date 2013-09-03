package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 11:58 AM
 */
public interface NodeController extends AbstractControllerInterface {

    public void update(final Node node);

    public void delete(final int nodeId);

    public Node getByID(final int nodeId);

    public Node getByName(final String name);

    public List<Node> list();

    public List<Node> list(final Setup setup);

    public Long count(final Setup setup);

    public List<Node> list(final Setup setup, final Capability capability);

    public String getDescription(final Node node);

    public Position getPosition(final Node node);

    public Position getAbsolutePosition(final Node node);

    public Origin getOrigin(final Node node);

    Node prepareInsertNode(final String nodeId) throws UnknownTestbedException;

    public List<Node> getRealNodes(final Node node);

    public void add(final Node node);


}
