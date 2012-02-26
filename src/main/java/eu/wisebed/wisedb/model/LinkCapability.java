package eu.wisebed.wisedb.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the object {@link LinkCapability} that has the properties of a capability and
 * refers to a {@link Link}.
 * In the class there are getter and setter methods for the properties.
 */
@Entity
@Table(name = "link_capabilities")
public class LinkCapability implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -3419203591130581062L;

    /**
     * The unique primary key of the object.
     */
    private int id;
    /**
     * The {@link Capability} the object refers to.
     */
    private Capability capability;
    /**
     * The {@link Link} the object refers to.
     */
    private Link link;
    /**
     * The {@link LastLinkReading} of the {@link LinkCapability}.
     */
    private LastLinkReading lastLinkReading;

    /**
     * Getter for the primary key.
     *
     * @return the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public int getId() {
        return id;
    }

    /**
     * Getter for the {@link Capability}.
     *
     * @return the {@link Capability}.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "capability_id", referencedColumnName = "capability_id")
    public Capability getCapability() {
        return capability;
    }

    /**
     * Getter for the {@link Link}.
     *
     * @return the {@link Link}.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    public Link getLink() {
        return link;
    }

    /**
     * Getter for the {@link LastLinkReading}.
     *
     * @return the {@link LastLinkReading}.
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "linkCapability", cascade = CascadeType.ALL)
    public LastLinkReading getLastLinkReading() {
        return lastLinkReading;
    }

    /**
     * Setter for the primary key.
     *
     * @param id the new id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for the {@link Capability}.
     *
     * @param capability the new {@link Capability}.
     */
    public void setCapability(Capability capability) {
        this.capability = capability;
    }

    /**
     * Setter for the {@link Link}.
     *
     * @param link the new {@link Link}.
     */
    public void setLink(Link link) {
        this.link = link;
    }

    /**
     * Setter for the {@link LastLinkReading}.
     *
     * @param lastLinkReading the new {@link LastLinkReading}.
     */
    public void setLastLinkReading(LastLinkReading lastLinkReading) {
        this.lastLinkReading = lastLinkReading;
    }

    @Override
    public String toString() {
        return "LinkCapability{" +
                "id=" + id +
                '}';
    }
}
