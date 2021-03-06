package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeReadingControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Lists all testbeds on the database and provides additional information on them.
 */
public class ListTestbeds {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListTestbeds.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            // testbed list
            List<Testbed> testbeds = TestbedControllerImpl.getInstance().list();
            LOGGER.info("Testbeds :" + testbeds.size());
            testbeds = TestbedControllerImpl.getInstance().list();
            LOGGER.info("Testbeds :" + testbeds.size());

            final Map<String, Long> nodesCount = TestbedControllerImpl.getInstance().countNodes();
            LOGGER.info(nodesCount.size());
            LOGGER.info("Nodes :" + nodesCount.size());
            final Map<String, Long> linksCount = TestbedControllerImpl.getInstance().countLinks();
            LOGGER.info(linksCount.size());
            LOGGER.info("Links :" + linksCount.size());

            long milis = System.currentTimeMillis();
            Date first = NodeReadingControllerImpl.getInstance().getFirstReading();
//            Date last = NodeReadingControllerImpl.getInstance().getLastReading();
            LOGGER.info(first);
//            LOGGER.info(last);
            LOGGER.info("time:"+(System.currentTimeMillis()-milis));


            milis = System.currentTimeMillis();
            first = NodeReadingControllerImpl.getInstance().getFirstReading();
//            last = NodeReadingControllerImpl.getInstance().getLastReading();
            LOGGER.info(first);
//            LOGGER.info(last);
            LOGGER.info("time:"+(System.currentTimeMillis()-milis));



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
