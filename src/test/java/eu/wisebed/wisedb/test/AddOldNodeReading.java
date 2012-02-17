package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeReadingController;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * Adds a node reading
 */
public class AddOldNodeReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddOldNodeReading.class);


    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {


            String readingId;
            Timestamp timestamp;
            double reading;
            String nodeId;
            String capabilityId;
            String stringReading;

            String dbUrl = "jdbc:mysql://150.140.5.11/wisedb";
            String dbClass = "com.mysql.jdbc.Driver";
            String query = "SELECT * FROM node_readings where node_id like 'urn:santa%' limit 100;";


            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbUrl, "wisedb", "wisedb123");
            final Statement stmt = con.createStatement();
            final Statement delStatement = con.createStatement();
            int count = 0;
            boolean foundAny = false;

            long start = System.currentTimeMillis();
            do {
                final ResultSet rs = stmt.executeQuery(query);
                foundAny = false;

                while (rs.next()) {
                    foundAny = true;
//                    LOGGER.info(readingId + "," + timestamp + "," + reading + "," + nodeId + "," + capabilityId + "," + stringReading);

                    NodeReadingController.getInstance().insertReading(rs.getString(4), rs.getString(5), 2, rs.getDouble(3), rs.getString(6), rs.getTimestamp(2));

                    String deleteQuery = "delete FROM node_readings where reading_id=" + rs.getString(1) + " limit 1;";


                    delStatement.execute(deleteQuery);

                    ++count;
                    LOGGER.info(count);


                } //end while

                LOGGER.info("-- deleted " + " rows, migrated " + count + " readings so far");
                long diff = System.currentTimeMillis() - start;
                diff = diff / 1000;


                LOGGER.info("Rate is : " + (count / diff));
//                delStatement.executeBatch();

            } while (foundAny);

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
