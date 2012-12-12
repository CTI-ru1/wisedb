package eu.wisebed.wisedb.model;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "foi_structure")
public class FoiStructure implements Serializable {
    /**
     * Serial Unique Version ID.
     */

    private static final long serialVersionUID = 7638758352687803978L;

    /**
     * The Node of the NodeCapability.
     */
    private Node node;
    /**
     * The LastNodeReading of the NodeCapability.
     */
    private String structure;

    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @org.hibernate.annotations.Parameter(name = "property", value = "id"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public Node getNode() {
        return node;
    }

    @Column(name = "structure", nullable = true, length = 1000)
    public String getStructure() {
        return structure;
    }

    public JSONObject getStructureJSON() {
        try {
            return new JSONObject(structure);
        } catch (JSONException e) {
            return null;
        }
    }

    public void setNode(final Node node) {
        this.node = node;
    }

    public void setStructure(final String structure) {
        this.structure = structure;
    }

    public void setStructure(final JSONObject structure) {
        this.structure = structure.toString();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("FOI{").append(node.getId()).append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoiStructure that = (FoiStructure) o;

        if (node != that.node) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
