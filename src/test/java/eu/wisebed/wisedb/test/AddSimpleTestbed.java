package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.SetupControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wisedb.model.TimeInfo;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimeZone;

/**
 * Adds a simple Testbed (Not running testbed-runtime).
 */
public class AddSimpleTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddSimpleTestbed.class);


    public static void main(final String[] args) throws IOException {


        // read from keyboard.
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Initialize hibernate and begin transaction
            HibernateUtil.connectEntityManagers();
            Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

            Testbed testbed = new Testbed();
            LOGGER.info("Provide Testbed Name");
//            final String testbedName = br.readLine();

            testbed.setName("theTestbed2");

            LOGGER.info("Provide Testbed Description");
//            final String testbedDescription = br.readLine();
            testbed.setDescription("description2");

            LOGGER.info("Provide Testbed's web page URL");
//            final String testbedWebPageUrl = br.readLine();

            LOGGER.info("Provide Testbed's urnPrefix");
//            final String urnPrefix = br.readLine();
            testbed.setUrnPrefix("urn:testbed2:");

            LOGGER.info("Using your default TimeZone : " + TimeZone.getDefault().getDisplayName());
            testbed.setTimeZone(TimeZone.getDefault());

            // set the testbed of the setup to be imported
            Setup setup = new Setup();

            testbed.setSetup(setup);

            // import to db
            TestbedControllerImpl.getInstance().add(testbed);
//            tImp.convert();

            // commmit transaction
            tx.commit();


            // begin transaction
            LOGGER.info("For testbed : " + testbed.getName() + " the default setup will be added");
            tx = HibernateUtil.getInstance().getSession().beginTransaction();
            Testbed theTestbed = TestbedControllerImpl.getInstance().getByUrnPrefix(testbed.getUrnPrefix());

            LOGGER.info(theTestbed.getId());

            Origin origin = new Origin();
            origin.setPhi((float) 0);
            origin.setTheta((float) 0);
            origin.setX((float) 0);
            origin.setY((float) 0);
            origin.setZ((float) 0);
            setup.setOrigin(origin);
            setup.setTimeinfo(new TimeInfo());
            setup.setCoordinateType("Absolute");
            setup.setDescription("description");

            setup.setTestbed(theTestbed);
            //update testbed
//            TestbedControllerImpl.getInstance().update(testbed);
            // import by the convert method
            SetupControllerImpl.getInstance().add(setup);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
