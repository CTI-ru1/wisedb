package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.LastLinkReadingControllerImpl;
import eu.wisebed.wisedb.controller.LastNodeReadingControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Lists All readings of the database.
 */
public class ListLastReadings {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListLastReadings.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            final Testbed testbed = TestbedControllerImpl.getInstance().getByID(1);

            final Capability capability = CapabilityControllerImpl.getInstance().getByID("temp2");

            final List<LastNodeReading> lastNodeReadings = LastNodeReadingControllerImpl.getInstance().getByCapability(testbed.getSetup(), capability);

            LOGGER.info("Total Last Node Readings : " + lastNodeReadings.size());

            for (final LastNodeReading lastNodeReading : lastNodeReadings) {
                LOGGER.info("LastNodeReading : " + lastNodeReading.getNodeCapability().getCapability().getName()
                        + "," + lastNodeReading.getNodeCapability().getNode().getId()
                        + "," + lastNodeReading.getReading()
                        + "," + lastNodeReading.getStringReading()
                );
            }

            final Capability capability2 = CapabilityControllerImpl.getInstance().getByID("blah3");

            final List<LastLinkReading> lastLinkReadings = LastLinkReadingControllerImpl.getInstance().getByCapability(testbed.getSetup(), capability2);

            LOGGER.info("Total Last Link Readings : " + lastLinkReadings.size());

            for (final LastLinkReading lastLinkReading : lastLinkReadings) {
                LOGGER.info("LastNodeReading : " + lastLinkReading.getLinkCapability().getCapability().getName()
                        + "," + lastLinkReading.getLinkCapability().getLink().getSource()
                        + "--" + lastLinkReading.getLinkCapability().getLink().getTarget()
                        + "," + lastLinkReading.getReading()
                        + "," + lastLinkReading.getStringReading()
                );
            }


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
