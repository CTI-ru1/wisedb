package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.controller.NodeReadingControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.HashMap;

public class NodeReadingCount {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(NodeReadingCount.class);

    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            final NodeReading nr = NodeReadingControllerImpl.getInstance().list().iterator().next();
            LOGGER.info("Reading : " + nr.getReading() + ", node : " + nr.getCapability().getNode().getId());

            final String urnPrefix = "urn:qopbot:";
            final Testbed testbed = TestbedControllerImpl.getInstance().getByUrnPrefix(urnPrefix);
            final Node node = NodeControllerImpl.getInstance().getByID(urnPrefix + "destiny");
            LOGGER.info("Selected Node : " + node.getId());
            long now1 = System.currentTimeMillis();
            Long readingsCount = NodeReadingControllerImpl.getInstance().count(node);
            long now2 = System.currentTimeMillis();
            LOGGER.info("Selected Node : " + node.getId() + " readings count :" + readingsCount.intValue() + " (" + (now2 - now1) + ")");
            now1 = System.currentTimeMillis();
            HashMap<Capability, Integer> map = NodeReadingControllerImpl.getInstance().getNodeReadingsCountMap(node);
            now2 = System.currentTimeMillis();
            for (Capability key : map.keySet()) {
                LOGGER.info("Selected Node : " + node.getId() + " Selected Capability : " + key.getName() + " readings count :" + map.get(key));
            }

            LOGGER.info("it took (" + (now2 - now1) + ").");
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
