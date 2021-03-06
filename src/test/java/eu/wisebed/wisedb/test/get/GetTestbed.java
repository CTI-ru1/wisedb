package eu.wisebed.wisedb.test.get;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetTestbed.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

//        LOGGER.info("CacheManagersCount:" + CacheManager.ALL_CACHE_MANAGERS.size());

        try {
//            LOGGER.info("nodeslist cache exists : " + CacheManager.getInstance().cacheExists("nodeslist"));
//            if (CacheManager.getInstance().cacheExists("nodeslist")) {
//                LOGGER.info("nodeslist cache size : " + CacheManager.getInstance().getCache("nodeslist").getKeys().size());
//            }
//            String[] caches = CacheManager.getInstance().getCacheNames();

//            LOGGER.info("TotalCaches : " + caches.length);
//            for (String cach : caches) {
//                LOGGER.info("Cache: " + cach + " contains : " + CacheManager.getInstance().getCache(cach).getKeys().size());
//            }

            //A testbed name
            final String name = "dsafg";
            Testbed testbed = null;
            testbed = TestbedControllerImpl.getInstance().getByID(1);
            for (int i = 0; i < 100; i++) {
                testbed = TestbedControllerImpl.getInstance().getByID(1);
            }

//            LOGGER.info("nodeslist cache exists : " + CacheManager.getInstance().cacheExists("nodeslist"));
//            if (CacheManager.getInstance().cacheExists("nodeslist")) {
//                LOGGER.info("nodeslist cache size : " + CacheManager.getInstance().getCache("nodeslist").getKeys().size());
//            }
//            String[] caches2 = CacheManager.getInstance().getCacheNames();
//
//            LOGGER.info("TotalCaches : " + caches2.length);
//            for (String cach : caches2) {
//                LOGGER.info("Cache: " + cach + " contains : " + CacheManager.getInstance().getCache(cach).getKeys().size());
//            }
            if (testbed != null) {
                LOGGER.info("name: " + testbed.getName());
                LOGGER.info("id: " + testbed.getId());
                LOGGER.info("description: " + testbed.getDescription());
                LOGGER.info("urnPrefix: " + testbed.getUrnPrefix());
                LOGGER.info("urnCapabilityPrefix: " + testbed.getUrnCapabilityPrefix());
                LOGGER.info("timeZone: " + testbed.getTimeZone().getDisplayName());
                LOGGER.info("setupId: " + testbed.getSetup().getId());
            } else {
                LOGGER.error("testbed " + name + " does not exist!");
            }


//            LOGGER.info(CacheManager.getInstance().getActiveConfigurationText());


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
