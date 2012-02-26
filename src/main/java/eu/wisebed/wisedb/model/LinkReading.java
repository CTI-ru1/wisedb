package eu.wisebed.wisedb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistent class for the object LinkReading that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "linkReadings")
public final class LinkReading implements Serializable {

    /**
     * Serial verison unique ID.
     */
    private static final long serialVersionUID = -8617855105682730969L;

    /**
     * Reading id.
     */
    private int id;

    /**
     * Capability.
     */
    private LinkCapability capability;

    /**
     * Timestamp of the reading.
     */
    private Date timestamp;

    /**
     * Numeric value of the reading.
     */
    private Double reading;


    /**
     * String value of the reading.
     */
    private String stringReading;

    /**
     * Constructor.
     */
    public LinkReading() {
        // empty constructor
    }

    /**
     * Returns this reading id.
     *
     * @return this reading id.
     */
    @Id
    @GeneratedValue
    @Column(name = "reading_id")
    public int getId() {
        return id;
    }

    /**
     * Sets this reading id.
     *
     * @param id , reading id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Returns capability reading.
     *
     * @return Link's capability reading
     */
    @Column(name = "reading")
    public Double getReading() {
        return reading;
    }

    /**
     * Set capability reading.
     *
     * @param reading , reading value.
     */
    public void setReading(final Double reading) {
        this.reading = reading;
    }

    /**
     * Returns timestamp value.
     *
     * @return Link's reading timestamp
     */
    @Column(name = "timestamp")
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the reading timestamp.
     *
     * @param timestamp , a Date instance.
     */
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the capability associated with this LinkReading.
     *
     * @return the capability associated with this LinkReading.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "capability_id", referencedColumnName = "id")
    public LinkCapability getCapability() {
        return capability;
    }

    /**
     * Sets the capability associated with this LinkReading.
     *
     * @param capability the associated capability.
     */
    public void setCapability(final LinkCapability capability) {
        this.capability = capability;
    }

    /**
     * Returns string reading.
     *
     * @return string reading.
     */
    @Column(name = "stringReading")
    public String getStringReading() {
        return stringReading;
    }

    /**
     * Sets string reading.
     *
     * @param stringReading string reading.
     */
    public void setStringReading(final String stringReading) {
        this.stringReading = stringReading;
    }

    @Override
    public String toString() {
        return "LinkReading{" +
                "capability=" + capability +
                ", timestamp=" + timestamp +
                ", reading=" + reading +
                ", stringReading='" + stringReading + '\'' +
                '}';
    }
}
