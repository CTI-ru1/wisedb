package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkReadingControllerImpl;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;

/**
 * Adds a new Link Reading to the database.
 * If the Link does not exist it is generated.
 * If the Capability does not exist it is generated.
 * If the LinkCapability does not exist it is generated.
 */
public class AddLinkReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(AddLinkReading.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {

//            // an id for testbed
//            final int testbedId = 1;
//            final Testbed testbed = TestbedControllerImpl.getInstance().getByID(testbedId);
//
//            // source node id
//            final String sourceId = "urn:testbed2:5";
//
//
//            // target node id
//            final String targetId = "urn:testbed1:9";
//
//            // link capability name
//            final String capabilityName = "quality";
//
//            // reading value
//            final double reading = 23.0;
//            final String stringReading = null;
//
//            // timestamp
//            final Date timestamp = new Date();
//
//            LOGGER.debug("Selected node : " + sourceId);
//            LOGGER.debug("Selected node : " + targetId);
//            LOGGER.debug("Capability for link : " + capabilityName);

            // insert reading
            LinkReadingControllerImpl.getInstance().insertReading("urn:wisebed:ctitestbed:virtual:0.I.9",
                    "urn:wisebed:ctitestbed:0xf042",
                    "virtual",
                    1.0,
                    null,
                    new Date());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            e.printStackTrace();
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
