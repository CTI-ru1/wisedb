package eu.wisebed.wisedb.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a persistant class for the object capability that has the
 * properties of a capability. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "user_roles")
public class UserRole implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -3419203591130281063L;
    /**
     * unique id for each Schedule.
     */
    private int userRoleId;
    private User user;
    private String authority;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_role_id", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public int getUserRoleId() {
        return userRoleId;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    @Column(name = "authority", nullable = false)
    public String getAuthority() {
        return authority;
    }


    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (userRoleId != userRole.userRoleId) return false;
        if (!authority.equals(userRole.authority)) return false;
        if (!user.equals(userRole.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userRoleId;
        result = 31 * result + user.hashCode();
        result = 31 * result + authority.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userRoleId=" + userRoleId +
                '}';
    }
}
