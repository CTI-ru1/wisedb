package eu.wisebed.wisedb.model;

import javax.persistence.*;
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


    private int id;

    private Capability capability;

    private Node node;

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

    public void setCapability(Capability capability) {
        this.capability = capability;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setLastNodeReading(LastNodeReading lastNodeReading) {
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
