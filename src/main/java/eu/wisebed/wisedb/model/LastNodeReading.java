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
 * This is a persistent class for the object {@link LastNodeReading} that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "last_node_capability_readings")
public final class LastNodeReading implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = 2824765230014359545L;

    /**
     * The id of the {@link NodeCapability} referring to.
     */
    private int id;

    /**
     * the Timestamp of the {@link LastNodeReading}.
     */
    private Date timestamp;

    /**
     * the Reading of the {@link LastNodeReading}.
     */
    private Double reading;

    /**
     * the StringReading of the {@link LastNodeReading}.
     */
    private String stringReading;

    /**
     * the {@link NodeCapability} of the {@link LastNodeReading}.
     */
    private NodeCapability nodeCapability;

    /**
     * Constructor.
     */
    public LastNodeReading() {
        // empty constructor
    }

    /**
     * Returns the id of the {@link NodeCapability} referring to.
     *
     * @return the id.
     */
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "nodeCapability"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "node_capability")
    public int getId() {
        return id;
    }

    /**
     * Returns the timestamp that this reading occured.
     *
     * @return timestamp of the reading.
     */
    @Column(name = "timestamp")
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the reading of the {@link LastNodeReading}.
     *
     * @return the reading.
     */
    @Column(name = "reading")
    public Double getReading() {
        return reading;
    }

    /**
     * Returns the stringReading of the {@link LastNodeReading}.
     *
     * @return the stringReading.
     */
    @Column(name = "stringReading")
    public String getStringReading() {
        return stringReading;
    }

    /**
     * Returns the {@link NodeCapability} referring to.
     *
     * @return the {@link NodeCapability} object.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public NodeCapability getNodeCapability() {
        return nodeCapability;
    }

    /**
     * Sets the id of the {@link NodeCapability}.
     *
     * @param id the id.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp {@link LastNodeReading} timestamp.
     */
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets this reading value.
     *
     * @param reading {@link LastNodeReading} reading value.
     */
    public void setReading(final Double reading) {
        this.reading = reading;
    }

    /**
     * Sets the stringReading.
     *
     * @param stringReading {@link LastNodeReading} stringReading.
     */
    public void setStringReading(final String stringReading) {
        this.stringReading = stringReading;
    }

    /**
     * Sets the {@link NodeCapability}.
     *
     * @param nodeCapability the {@link NodeCapability}.
     */
    public void setNodeCapability(final NodeCapability nodeCapability) {
        this.nodeCapability = nodeCapability;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastNodeReading that = (LastNodeReading) o;

        if (id != that.id) return false;
        if (reading != null ? !reading.equals(that.reading) : that.reading != null) return false;
        if (stringReading != null ? !stringReading.equals(that.stringReading) : that.stringReading != null)
            return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (reading != null ? reading.hashCode() : 0);
        result = 31 * result + (stringReading != null ? stringReading.hashCode() : 0);
        return result;
    }
}
