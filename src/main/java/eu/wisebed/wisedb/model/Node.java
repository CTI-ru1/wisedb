package eu.wisebed.wisedb.model;


import javax.persistence.*;

/**
 * This is a persistant class for the object node that has the
 * properties of a node. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "nodes")
public class Node {

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

    @Column(name = "node_id", unique = true, nullable = false)
    public String getName() {
        return name;
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
     * this method sets a number at the id of a node.
     *
     * @param id the id of the node.
     */
    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
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
        return new StringBuilder().append("Node{").append(name).append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
