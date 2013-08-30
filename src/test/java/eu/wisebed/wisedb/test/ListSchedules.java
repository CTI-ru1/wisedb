package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.ScheduleControllerImpl;
import eu.wisebed.wisedb.model.Schedule;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

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
            s.setSecond("*");
            s.setMinute("*");
            s.setHour("*");
            s.setMonth("*");
            s.setDom("*");
            s.setDow("?");
            s.setNode("mynode");
            s.setUsername("qopbot");
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
