package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeReadingController;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 * Adds a node reading
 */
public class AddOldNodeCoordinates {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddOldNodeCoordinates.class);


    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {


            String dbUrl = "jdbc:mysql://150.140.5.38/wisedb_test";
            String dbClass = "com.mysql.jdbc.Driver";
            String query = "SELECT * FROM nodes where node_id like 'urn:santa%' ";


            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbUrl, "wisedb", "wisedb123");
            final Statement stmt = con.createStatement();
            final Statement delStatement = con.createStatement();
            int count = 0;
            boolean foundAny = false;

            final ResultSet rs = stmt.executeQuery(query);


            while (rs.next()) {

                String nodeId = rs.getString(1);
                double x = rs.getDouble(2);
                double y = rs.getDouble(3);
                double z = rs.getDouble(4);
                double phi = rs.getDouble(5);
                double theta = rs.getDouble(6);

                LOGGER.info(nodeId + "," + x + "-" + y + "-" + z + ", " + phi + " , " + theta);

                int testbedId = 2;
                NodeReadingController.getInstance().insertReading(nodeId, "x", testbedId, x, null, new Date());
                NodeReadingController.getInstance().insertReading(nodeId, "y", testbedId, y, null, new Date());
                NodeReadingController.getInstance().insertReading(nodeId, "z", testbedId, z, null, new Date());
                NodeReadingController.getInstance().insertReading(nodeId, "phi", testbedId, phi, null, new Date());
                NodeReadingController.getInstance().insertReading(nodeId, "theta", testbedId, theta, null, new Date());


            } //end while


            con.close();

//
//
//            // a valid urnPrefix for the testbed
//            final String urnPrefix = "urn:prefix:";
//
//            final int testbedId = 18;
//            // a node id for the testbed
//            final String nodeId = urnPrefix + "NODETEST1";
//
//            // get that nodes capability name
//            final String capabilityName = "temp";
//
//
//
//
//
//            // reading value
//            final double readingValue = 24.0;
//            // string reading value
//            final String stringReading = null;
//
//            // Occured time
//            final Date timestamp = new Date();
//
//            LOGGER.info("Node : " + nodeId);
//            LOGGER.info("Capability : " + capabilityName);
//            LOGGER.info("Reading : " + readingValue);
//            LOGGER.info("StringReading : " + stringReading);
//            LOGGER.info("Timestamp : " + timestamp.toString());
//
//            // insert reading
//            NodeReadingController.getInstance().insertReading(nodeId, capabilityName, testbedId, readingValue, stringReading, timestamp);
//

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

