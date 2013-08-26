package eu.wisebed.wisedb.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a persistant class for the object capability that has the
 * properties of a capability. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "schedules")
public class Schedule implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -3419203591130281063L;
    /**
     * unique id for each Schedule.
     */
    private int id;
    private char minute;
    private char hour;
    private char dom;
    private char month;
    private char dow;
    private String payload;
    private String node;
    private String capability;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public int getId() {
        return id;
    }

    @Column(name = "node", nullable = false)
    public String getNode() {
        return node;
    }

    @Column(name = "capability", nullable = false)
    public String getCapability() {
        return capability;
    }


    @Column(name = "minute", nullable = false)
    public char getMinute() {
        return minute;
    }

    @Column(name = "hour", nullable = false)
    public char getHour() {
        return hour;
    }

    @Column(name = "dom", nullable = false)
    public char getDom() {
        return dom;
    }

    @Column(name = "month", nullable = false)
    public char getMonth() {
        return month;
    }

    @Column(name = "dow", nullable = false)
    public char getDow() {
        return dow;
    }

    @Column(name = "payload", nullable = false)
    public String getPayload() {
        return payload;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public void setMinute(char minute) {
        this.minute = minute;
    }

    public void setHour(char hour) {
        this.hour = hour;
    }

    public void setDom(char dom) {
        this.dom = dom;
    }

    public void setMonth(char month) {
        this.month = month;
    }

    public void setDow(char dow) {
        this.dow = dow;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (dom != schedule.dom) return false;
        if (dow != schedule.dow) return false;
        if (hour != schedule.hour) return false;
        if (minute != schedule.minute) return false;
        if (month != schedule.month) return false;
        if (payload != schedule.payload) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) minute;
        result = 31 * result + (int) hour;
        result = 31 * result + (int) dom;
        result = 31 * result + (int) month;
        result = 31 * result + (int) dow;
        result = 31 * result + payload.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                '}';
    }
}
