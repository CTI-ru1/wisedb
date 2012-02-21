package eu.wisebed.wisedb.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a persistant class for the object node that has the
 * properties of a node. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "nodes")
public class Node implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -5422125775653598399L;

    /**
     * the id of an object Node.
     */
    private int id;

    private String name;

    /**
     * this node belongs to a setup.
     */
    private Setup setup;

    /**
     * this method returns the id of a node.
     *
     * @return the id of the node.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(fetch = FetchType.LAZY)
    public int getId() {
        return id;
    }

    /**
     * this method sets a number at the id of a node.
     *
     * @param id the id of the node.
     */
    public void setId(final int id) {
        this.id = id;
    }

    @Column(name = "node_id")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the setup this node belongs to.
     *
     * @return the setup this node belongs to.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Setup.class)
    @JoinColumn(name = "setup_id", insertable = true, updatable = false, referencedColumnName = "setup_id")
    public Setup getSetup() {
        return setup;
    }

    /**
     * sets the setup this node belongs to.
     *
     * @param setup setup instance.
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
