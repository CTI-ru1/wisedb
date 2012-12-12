package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.FoiStructure;
import eu.wisebed.wisedb.model.Node;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/19/12
 * Time: 1:00 PM
 */
public interface FoiStructureController extends AbstractControllerInterface {

    public FoiStructure getByNode(final Node node);

    public void add(final FoiStructure foiStructure);

//    public void add(final Node foi, final JSONObject structure);
//
//    public void add(final String foiName, final JSONObject structure);
//
//    public void add(final Node foi, final String structure);
//
//    public void add(final String foiName, final String structure);

//    public void delete(final FoiStructure foiStructure);

    public void delete(final Node foi);

    public void delete(final String foiName);

    /**
     * Updates the structure of a foi in the storage.
     *
     * @param foiStructure the structure of the foi (the name is included in the structure).
     */
    public void update(final FoiStructure foiStructure);

//    public void update(final Node foi, final JSONObject structure);
//
//    public void update(final String foiName, final JSONObject structure);
//
//    public void update(final Node foi, final String structure);
//
//    public void update(final String foiName, final String structure);

}
