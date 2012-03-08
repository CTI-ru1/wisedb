package eu.wisebed.wisedb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the {@link Link} object that has the
 * properties of a link. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "links")
public class Link implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -393203811928650579L;

    /**
     * The primary key id.
     */
    private int id;

    /**
     * The {@link Setup} object this {@link Link} refers to.
     */
    private Setup setup;


    /**
     * The {@link Node} object that serves as a source address for the {@link Link}.
     */
    private Node source;

    /**
     * The {@link Node} object that serves as a target address for the {@link Link}.
     */
    private Node target;

    /**
     * The primary key id.
     *
     * @return the id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    /**
     * The source {@link Node}.
     *
     * @return The source {@link Node}.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "link_source", referencedColumnName = "node_id")
    public Node getSource() {
        return source;
    }

    /**
     * The target {@link Node}.
     *
     * @return The target {@link Node}.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Node.class)
    @JoinColumn(name = "link_target", referencedColumnName = "node_id")
    public Node getTarget() {
        return target;
    }

    /**
     * The {@link Setup} this {@link Link} object belongs to.
     *
     * @return The {@link Setup} this {@link Link} object belongs to.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Setup.class)
    @JoinColumn(name = "setup_id", insertable = true, updatable = false, referencedColumnName = "setup_id")
    public Setup getSetup() {
        return setup;
    }

    /**
     * Setter for  the primary key.
     *
     * @param id the id to set as primary key.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the source {@link Node} object.
     *
     * @param source The {@link Node} to set as source.
     */
    public void setSource(final Node source) {
        this.source = source;
    }

    /**
     * Sets the target {@link Node} object.
     *
     * @param target The {@link Node} to set as target.
     */
    public void setTarget(final Node target) {
        this.target = target;
    }

    /**
     * Sets the {@link Setup} the {@link Link} belongs to.
     *
     * @param setup The {@link Setup} to set the {@link Link} to.
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Link{").append(id).append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (id != link.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
