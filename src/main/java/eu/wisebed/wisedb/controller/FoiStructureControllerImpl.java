package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.FoiStructure;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;

/**
 * CRUD operations for LastNodeReading entities.
 */
@SuppressWarnings("unchecked")
public class FoiStructureControllerImpl extends AbstractController<LastNodeReading> implements FoiStructureController {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static FoiStructureControllerImpl ourInstance = null;
    /**
     * ID literal.
     */
    private static final String ID = "id";
    private static final String STRUCTURE = "structure";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FoiStructureControllerImpl.class);


    /**
     * Public constructor .
     */
    public FoiStructureControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * FoiStructureController is loaded on the first execution of
     * FoiStructureController.getInstance() or the first access to
     * FoiStructureController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static FoiStructureControllerImpl getInstance() {
        synchronized (FoiStructureControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = new FoiStructureControllerImpl();
            }
        }
        return ourInstance;
    }


    @Override
    public FoiStructure getByNode(Node node) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(FoiStructure foiStructure) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Node foi) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(String foiName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(FoiStructure foiStructure) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
