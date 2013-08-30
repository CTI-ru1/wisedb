package eu.wisebed.wisedb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private String second;
    private String minute;
    private String hour;
    private String dom;
    private String month;
    private String dow;
    private String payload;
    private String node;
    private String capability;
    private String username;
    private Date last;

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

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }


    @Column(name = "second", length = 5, nullable = false)
    public String getSecond() {
        return second;
    }

    @Column(name = "minute", length = 5, nullable = false)
    public String getMinute() {
        return minute;
    }

    @Column(name = "hour", length = 5, nullable = false)
    public String getHour() {
        return hour;
    }

    @Column(name = "dom", length = 5, nullable = false)
    public String getDom() {
        return dom;
    }

    @Column(name = "month", length = 5, nullable = false)
    public String getMonth() {
        return month;
    }

    @Column(name = "dow", length = 5, nullable = false)
    public String getDow() {
        return dow;
    }

    @Column(name = "payload", nullable = false)
    public String getPayload() {
        return payload;
    }

    @Column(name = "last", nullable = false)
    public Date getLast() {
        return last;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setLast(Date last) {
        this.last = last;
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
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                '}';
    }
}
