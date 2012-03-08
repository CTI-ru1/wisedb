package eu.wisebed.wisedb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the object capability that has the
 * properties of a {@link Capability} In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "capabilities")
public class Capability implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -3419203591130581062L;

    /**
     * the Name of the object {@link Capability}
     */
    private String name;

    /**
     * the Datatype of the {@link Capability}
     */
    private String datatype;

    /**
     * the Unit of the {@link Capability}
     */
    private String unit;

    /**
     * the DefaultValue of the {@link Capability}
     */
    private String defaultvalue;

    /**
     * the Description of the {@link Capability}
     */
    private String description;

    /**
     * this method returns the name of the {@link Capability}
     *
     * @return the name of the {@link Capability}
     */
    @Id
    @Column(name = "capability_id")
    public String getName() {
        return name;
    }

    /**
     * this method returns the datatype of the {@link Capability}
     *
     * @return the datatype of the {@link Capability}
     */
    @Column(name = "datatype", nullable = true)
    public String getDatatype() {
        return datatype;
    }

    /**
     * this method returns the unit of the {@link Capability}
     *
     * @return the unit of the {@link Capability}
     */
    @Column(name = "unit", nullable = true)
    public String getUnit() {
        return unit;
    }

    /**
     * Returns default value.
     *
     * @return default value.
     */
    @Column(name = "defaultvalue", nullable = true)
    public String getDefaultvalue() {
        return defaultvalue;
    }


    /**
     * Returns this capability's description.
     *
     * @return this capability's description.
     */
    @Column(name = "description", nullable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    /**
     * this method sets the name of the {@link Capability}
     *
     * @param name the name of the {@link Capability}
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * this method sets the datatype of the {@link Capability}
     *
     * @param datatype the datatype of the {@link Capability}
     */
    public void setDatatype(final String datatype) {
        this.datatype = datatype;
    }


    /**
     * this method sets the unit of the {@link Capability}
     *
     * @param unit the unit of the {@link Capability}
     */
    public void setUnit(final String unit) {
        this.unit = unit;
    }


    /**
     * Sets default value.
     *
     * @param defaultvalue default value
     */
    public void setDefaultvalue(final String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }


    /**
     * Set this capability's description.
     *
     * @param description description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Capability{").append(name).append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Capability that = (Capability) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
