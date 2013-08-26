package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeReadingControllerImpl;
import eu.wisebed.wisedb.controller.ScheduleControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Schedule;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Lists all testbeds on the database and provides additional information on them.
 */
public class ListSchedules {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListSchedules.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            // testbed list
            List<Schedule> schedules = ScheduleControllerImpl.getInstance().list();
            LOGGER.info("Testbeds :" + schedules.size());

            Schedule s = new Schedule();
            s.setMinute('*');
            s.setHour('*');
            s.setDow('*');
            s.setMonth('*');
            s.setDom('*');
            s.setNode("mynode");
            s.setCapability("other");
            s.setPayload("0");
            ScheduleControllerImpl.getInstance().add(s);
            schedules = ScheduleControllerImpl.getInstance().list();
            LOGGER.info("Testbeds :" + schedules.size());

            for (Schedule schedule : schedules) {
                LOGGER.info(schedule.toString());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e, e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
