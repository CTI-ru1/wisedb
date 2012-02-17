package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkReadingController;
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
public class AddOldLinkReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddOldLinkReading.class);


    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {


            String readingId;
            Timestamp timestamp;
            double reading;
            String source;
            String target;
            String capabilityId;
            String stringReading;

            String dbUrl = "jdbc:mysql://150.140.5.11/wisedb";
            String dbClass = "com.mysql.jdbc.Driver";
            String query = "SELECT * FROM link_readings where link_source like 'urn:wisebed:cti%' limit 10;";


            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbUrl, "wisedb", "wisedb123");
            final Statement stmt = con.createStatement();
            final Statement delStatement = con.createStatement();
            int count = 0;
            boolean foundAny = false;
            do {
                final ResultSet rs = stmt.executeQuery(query);
                foundAny = false;

                while (rs.next()) {
                    foundAny = true;
                    readingId = rs.getString(1);
                    timestamp = rs.getTimestamp(2);
                    reading = rs.getDouble(3);

                    source= rs.getString(5);
                    target= rs.getString(6);
                    capabilityId = rs.getString(7);
                    stringReading = rs.getString(8);
                    LOGGER.info(readingId + "," + timestamp + "," + reading + "," + source+","+target + "," + capabilityId + "," + stringReading);

                    LinkReadingController.getInstance().insertReading(source,target,capabilityId,1,reading, stringReading,timestamp);

                    String deleteQuery = "delete FROM link_readings where reading_id=" + readingId + " limit 1;";

                    final boolean result = delStatement.execute(deleteQuery);

                    LOGGER.info(readingId + "-- deleted " + delStatement.getUpdateCount() + " rows, migrated " + (++count) + " readings so far");


                } //end while
                Thread.sleep(3);
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

