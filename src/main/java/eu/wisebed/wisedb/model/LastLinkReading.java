package eu.wisebed.wisedb.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistent class for the object {@link LastLinkReading} that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "last_link_capability_readings")
public final class LastLinkReading implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = 8748551395278795210L;

    /**
     * The id of the {@link LinkCapability} referring to.
     */
    private int id;

    /**
     * the Timestamp of the {@link LastLinkReading}.
     */
    private Date timestamp;

    /**
     * the Reading of the {@link LastLinkReading}.
     */
    private Double reading;

    /**
     * the StringReading of the {@link LastLinkReading}.
     */
    private String stringReading;
    /**
     * the {@link LinkCapability} of the {@link LastLinkReading}.
     */
    private LinkCapability linkCapability;

    /**
     * Constructor.
     */
    public LastLinkReading() {
        // empty constructor
    }

    /**
     * Returns the id of the {@link LinkCapability} referring to.
     *
     * @return the id.
     */
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "linkCapability"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "link_capability")
    public int getId() {
        return id;
    }

    /**
     * Returns the timestamp of the {@link LastLinkReading}.
     *
     * @return the timestamp.
     */
    @Column(name = "timestamp")
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the reading of the {@link LastLinkReading}.
     *
     * @return the reading.
     */
    @Column(name = "reading")
    public Double getReading() {
        return reading;
    }

    /**
     * Returns the stringReading of the {@link LastLinkReading}.
     *
     * @return the stringReading.
     */
    @Column(name = "stringReading")
    public String getStringReading() {
        return stringReading;
    }

    /**
     * Returns the {@link LinkCapability} referring to.
     *
     * @return the {@link LinkCapability} object.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public LinkCapability getLinkCapability() {
        return linkCapability;
    }

    /**
     * Sets the id of the {@link LinkCapability}.
     *
     * @param id the id.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp {@link LastLinkReading} timestamp.
     */
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Sets this reading value.
     *
     * @param reading {@link LastLinkReading} reading value.
     */
    public void setReading(final Double reading) {
        this.reading = reading;
    }


    /**
     * Sets the stringReading.
     *
     * @param stringReading {@link LastLinkReading} stringReading.
     */
    public void setStringReading(final String stringReading) {
        this.stringReading = stringReading;
    }

    /**
     * Sets the {@link LinkCapability}.
     *
     * @param linkCapability the {@link LinkCapability}.
     */
    public void setLinkCapability(final LinkCapability linkCapability) {
        this.linkCapability = linkCapability;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastLinkReading that = (LastLinkReading) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}


