package eu.wisebed.wisedb.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistant class for the object node that has the
 * properties of a node. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "statistics")
public class Statistics implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -5422125775353598349L;

    /**
     * the id of an object Node.
     */
    private int id;
    private String url;
    private Long millis;
    private Date date;

    public Statistics() {
    }

    public Statistics(final String url, final Long millis) {
        this.url = url;
        this.millis = millis;
        this.date = new Date();
    }

    /**
     * this method returns the id of a node.
     *
     * @return the id of the node.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @Column(name = "url", unique = false, nullable = false)
    public String getUrl() {
        return url;
    }

    @Column(name = "millis", unique = false, nullable = false)
    public Long getMillis() {
        return millis;
    }

    @Column(name = "date", unique = false, nullable = false)
    public Date getDate() {
        return date;
    }

    /**
     * this method sets a number at the id of a node.
     *
     * @param id the id of the node.
     */
    public void setId(final int id) {
        this.id = id;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setMillis(Long millis) {
        this.millis = millis;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistics that = (Statistics) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (millis != null ? !millis.equals(that.millis) : that.millis != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (millis != null ? millis.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                '}';
    }
}
