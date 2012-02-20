package eu.wisebed.wisedb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the object link that has the
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

    public Link() {
    }

    /**
     * The link primary key.
     */

    private int id;

    /**
     * this link belongs to a setup.
     */
    private Setup setup;


    /**
     * the source of an object Link.
     */
    private Node source;

    /**
     * the target of an object Link.
     */
    private Node target;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "link_source", referencedColumnName = "node_id")
    public Node getSource() {
        return source;
    }

    public void setSource(final Node source) {
        this.source = source;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Node.class)
    @JoinColumn(name = "link_target", referencedColumnName = "node_id")
    public Node getTarget() {
        return target;
    }

    public void setTarget(final Node target) {
        this.target = target;
    }

//    /**
//     * this method returns the boolean value encrypted of the link.
//     *
//     * @return the encrypted of the link.
//     */
//    public Boolean isEncrypted() {
//        return encrypted;
//    }
//
//    /**
//     * this method returns the boolean value encrypted of the link.
//     *
//     * @return the encrypted of the link.
//     */
//    public Boolean getEncrypted() {
//        return isEncrypted();
//    }
//
//    /**
//     * this method sets the boolean value encrypted of the link.
//     *
//     * @param encrypted the encrypted of the link.
//     */
//    public void setEncrypted(final Boolean encrypted) {
//        this.encrypted = encrypted;
//    }
//
//    /**
//     * this method returns the boolean value virtual of the link.
//     *
//     * @return the virtual of the link.
//     */
//    public Boolean isVirtual() {
//        return virtual;
//    }
//
//    /**
//     * this method returns the boolean value virtual of the link.
//     *
//     * @return the virtual of the link.
//     */
//    public Boolean getVirtual() {
//        return isVirtual();
//    }
//
//    /**
//     * this method sets the boolean value virtual of the link.
//     *
//     * @param virtual the virtual of the link.
//     */
//    public void setVirtual(final Boolean virtual) {
//        this.virtual = virtual;
//    }

    /**
     * Returns a collection of setups.
     *
     * @return the related setup instance
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Setup.class)
    @JoinColumn(name = "setup_id", insertable = false, updatable = false, referencedColumnName = "setup_id")
    public Setup getSetup() {
        return setup;
    }

    /**
     * sets the setup this link belongs to setups.
     *
     * @param setup , a setup instance
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }
}
