package eu.wisebed.wisedb.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a persistant class for the object node that has the
 * properties of a node. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "virtual_node_descriptions")
public class VirtualNodeDescription implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -5422125775653598349L;

    /**
     * the id of an object Node.
     */
    private int id;

    private User user;

    private Node node;

    private String description;


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
     * returns the setup this node belongs to.
     *
     * @return the setup this node belongs to.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.MERGE}, targetEntity = User.class)
    @JoinColumn(name = "user_id", insertable = true, updatable = true, nullable = false,referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    /**
     * returns the setup this node belongs to.
     *
     * @return the setup this node belongs to.
     */
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.MERGE}, targetEntity = Node.class)
    @JoinColumn(name = "node_id", insertable = true, updatable = true, nullable = false,referencedColumnName = "node_id")
    public Node getNode() {
        return node;
    }
    @Column(name = "description", unique = false, nullable = false)
    public String getDescription() {
        return description;
    }

    /**
     * this method sets a number at the id of a node.
     *
     * @param id the id of the node.
     */
    public void setId(final int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VirtualNodeDescription that = (VirtualNodeDescription) o;

        if (id != that.id) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!node.equals(that.node)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + node.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VirtualNodeDescription{" +
                "id=" + id +
                '}';
    }
}
