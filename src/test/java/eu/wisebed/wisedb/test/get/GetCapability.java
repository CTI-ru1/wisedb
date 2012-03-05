package eu.wisebed.wisedb.test.get;

import org.apache.log4j.Logger;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetCapability {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetCapability.class);


    private static String decode(final String header) {
        String decodedProtocol = header;
        if (header.contains(".")) {
            decodedProtocol = decodedProtocol.replaceAll("\\.", "@");
        }
        if (header.contains("-")) {
            decodedProtocol = decodedProtocol.replaceAll("-", ":");
        }
        return decodedProtocol;
    }

    public static void main(final String[] args) {

        String skk = "urn-wisebed-ctitestbed-0x9979.urn-wisebed-node-capability-pir";

        System.out.println(decode(skk));

        /*  // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final int id = 1;
        try {
            final Capability capability = CapabilityControllerImpl.getInstance().getByID("urn:wisebed:node:capability:batterycharge");
            if (capability != null) {
                LOGGER.info("getName: " + capability.getName());
                LOGGER.info("getDatatype: " + capability.getDatatype());
                LOGGER.info("getDefaultvalue: " + capability.getDefaultvalue());
                LOGGER.info("getDescription: " + capability.getDescription());
                LOGGER.info("getUnit: " + capability.getUnit());
                //                LOGGER.info("Position :" + NodeControllerImpl.getInstance().getPosition(node));
//                LOGGER.info("Origin :" + NodeControllerImpl.getInstance().getOrigin(node));

            } else {
                LOGGER.error("capability " + id + " does not exist!");
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
        }*/
    }
}
