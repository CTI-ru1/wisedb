package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkControllerImpl;
import eu.wisebed.wisedb.controller.LinkReadingControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class LinkReadingCount {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(LinkReadingCount.class);

    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            final String urnPrefix = "urn:wisebed:ctitestbed:";
            final Setup setup = TestbedControllerImpl.getInstance().getByUrnPrefix(urnPrefix).getSetup();
            final List<Link> links = LinkControllerImpl.getInstance().list(setup);
            LOGGER.info("Links size :  " + links.size());
            for (final Link link : links) {
                LOGGER.info("Selected Link : [" + link.getSource() + "," + link.getTarget() + "]");
                int readingsCount = LinkReadingControllerImpl.getInstance().count(link);
                LOGGER.info("Selected " + link + " readings count :" + readingsCount);

            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
