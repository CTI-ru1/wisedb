package eu.wisebed.wisedb.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.TimeZone;

/**
 * This is a persistent class for the object Testbed that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "testbeds")
public final class Testbed implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -2786542310266504137L;
    /**
     * Identity of the network.
     */
    private int id;

    /**
     * Name of the testbed.
     */
    private String name;

    /**
     * Description of the testbed.
     */
    private String description;

    /**
     * URN prefix.
     */
    private String urnPrefix;

    /**
     * URN Capability prefix.
     */
    private String urnCapabilityPrefix;

    /**
     * Set of Setups belonging in Testbed.
     */
    private transient Setup setup;

    /**
     * Testbed timezone.
     */
    private TimeZone timeZone;

    /**
     * Get the Identity of the testbed.
     *
     * @return Identity of the testbed.
     */
    @Id
    @GeneratedValue
    @Column(name = "testbed_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Get the Name of the testbed.
     *
     * @return Name of the testbed.
     */
    @Basic
    @Column(name = "name", unique = false, nullable = false)
    public String getName() {
        return name;
    }

    /**
     * Get the urn prefix of the testbed.
     *
     * @return urn prefix of the testbed.
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "urnPrefix", unique = true, nullable = false)
    public String getUrnPrefix() {
        return urnPrefix;
    }

    /**
     * Get the urn prefix of the testbed.
     *
     * @return urn prefix of the testbed.
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "urnCapabilityPrefix", unique = true, nullable = false)
    public String getUrnCapabilityPrefix() {
        return urnCapabilityPrefix;
    }

    /**
     * Get the Description of the testbed.
     *
     * @return Description of the testbed.
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description", unique = false, nullable = false, length = 1000)
    public String getDescription() {
        return description;
    }

    /**
     * Returns the testbed setup.
     *
     * @return the testbed setup.
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "testbed", cascade = CascadeType.ALL)
    public Setup getSetup() {
        return setup;
    }

    /**
     * Returns testbed's timezone.
     *
     * @return testbed's timezone.
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "timeZone", unique = false, nullable = false)
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Set the id of the testbed.
     *
     * @param id the new Identity of the testbed.
     */
    public void setId(final int id) {
        this.id = id;
    }


    /**
     * Set the Name of the testbed.
     *
     * @param name the new Name of the testbed.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set the urn prefix of the testbed.
     *
     * @param urnPrefix the urn prefix of the testbed.
     */
    public void setUrnPrefix(final String urnPrefix) {
        this.urnPrefix = urnPrefix;
    }

    /**
     * Set the urn prefix of the testbed.
     *
     * @param urnCapabilityPrefix the urn prefix of the testbed.
     */
    public void setUrnCapabilityPrefix(final String urnCapabilityPrefix) {
        this.urnCapabilityPrefix = urnCapabilityPrefix;
    }

    /**
     * Set the Description of the testbed.
     *
     * @param description the new Description of the testbed.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets the testbed setup.
     *
     * @param setup , a setup instance.
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }

    /**
     * Sets testbed's timezone.
     *
     * @param timeZone testbed's timezeone.
     */
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Testbed{").append(id).append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Testbed testbed = (Testbed) o;

        if (id != testbed.id) return false;

        return true;
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
