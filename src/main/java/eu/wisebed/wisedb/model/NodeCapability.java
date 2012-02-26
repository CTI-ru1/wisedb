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
 * This is a persistant class for the object capability that has the
 * properties of a capability. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "node_capabilities")
public class NodeCapability implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -3419203591130581062L;
    /**
     * unique id for each NodeCapability.
     */
    private int id;
    /**
     * The Capability of the NodeCapability.
     */
    private Capability capability;
    /**
     * The Node of the NodeCapability.
     */
    private Node node;
    /**
     * The LastNodeReading of the NodeCapability.
     */
    private LastNodeReading lastNodeReading;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public int getId() {
        return id;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "capability_id", referencedColumnName = "capability_id")
    public Capability getCapability() {
        return capability;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "node_id", referencedColumnName = "node_id")
    public Node getNode() {
        return node;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nodeCapability", cascade = CascadeType.ALL)
    public LastNodeReading getLastNodeReading() {
        return lastNodeReading;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCapability(final Capability capability) {
        this.capability = capability;
    }

    public void setNode(final Node node) {
        this.node = node;
    }

    public void setLastNodeReading(final LastNodeReading lastNodeReading) {
        this.lastNodeReading = lastNodeReading;
    }

    @Override
    public String toString() {
        return "NodeCapability{" +
                "id=" + id +
                "capability=" + capability +
                ", node=" + node +
                '}';
    }

}
