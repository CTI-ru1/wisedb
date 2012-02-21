package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.controller.AbstractController;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD operations for LastLinkReading entities.
 */
@SuppressWarnings("unchecked")
public class LastLinkReadingControllerImpl extends AbstractController<LastLinkReading> implements LastLinkReadingController {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastLinkReadingController ourInstance = null;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LastLinkReadingControllerImpl.class);

    /**
     * Public constructor .
     */
    public LastLinkReadingControllerImpl() {
        // Does nothing
        super();
    }

    /**
     * LastNodeReadingController is loaded on the first execution of
     * LastNodeReadingController.getInstance() or the first access to
     * LastNodeReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LastLinkReadingController getInstance() {
        synchronized (LastLinkReadingControllerImpl.class) {
            if (ourInstance == null) {
                ourInstance = (LastLinkReadingController) new LastLinkReadingControllerImpl();
            }
        }

        return ourInstance;
    }

    /**
     * Returns the last reading inserted in the persistence for a specific link & capability.
     *
     * @param linkCapability , a link capability.
     * @return the last node reading.
     */
    public LastLinkReading getByID(final LinkCapability linkCapability) {
        LOGGER.info("add(" + linkCapability + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final LastLinkReading lastLinkReading = new LastLinkReading();
        lastLinkReading.setLinkCapability(linkCapability);
        return (LastLinkReading) session.get(LastLinkReading.class, lastLinkReading);
    }
//
//    /**
//     * Returns a list of last node reading entries for the nodes of a testbed.
//     *
//     * @param testbed , a testbed instance.
//     * @return a list last node readings from a testbed's nodes
//     */
//    public List<LastLinkReading> getByTestbed(final Testbed testbed) throws NotImplemented {
//        LOGGER.info("getByTestbed(" + testbed + ")");
//        throw new NotImplemented();
////        // retrieve testbed nodes from setup
////        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());
////        List<Link> links = LinkController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
////        if (links == null || links.isEmpty()) {
////            return null;
////        }
////
////        final Session session = this.getSessionFactory().getCurrentSession();
////        final Criteria criteria = session.createCriteria(LastLinkReading.class);
////        criteria.add(Restrictions.in(LINK, links));
////        return (List<LastLinkReading>) criteria.list();
//    }
//
//    /**
//     * Returns a list of last reading rows inserted in the persistence for a specific capability.
//     *
//     * @param testbed    ,  a testbed.
//     * @param capability , a capability.
//     * @return a list of last reading rows for each capability.
//     */
//    public List<LastLinkReading> getByCapability(final Testbed testbed, final Capability capability) {
//        LOGGER.info("getByCapability(" + testbed + "," + capability + ")");
//        // retrieve testbed nodes from setup
//        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());
//        List<Link> links = LinkController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
//        if (links == null || links.isEmpty()) {
//            return null;
//        }
//
//        final Session session = this.getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(LastLinkReading.class);
//        criteria.add(Restrictions.in(LINK, links));
//        criteria.add(Restrictions.eq(CAPABILITY, capability));
//        return (List<LastLinkReading>) criteria.list();
//    }

    public List<LastLinkReading> getByCapability(final Setup setup, Capability capability) {
        LOGGER.info("getByCapability(" + setup + "," + capability + ")");

        final List<LinkCapability> linkCapabilities = CapabilityControllerImpl.getInstance().listLinkCapabilities(setup, capability);

        List<LastLinkReading> result = new ArrayList<LastLinkReading>();

        for (final LinkCapability linkCapability : linkCapabilities) {
            result.add(linkCapability.getLastLinkReading());
        }
        return result;
    }
}
