/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author prima
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usersgroups.findAll", query = "SELECT u FROM Usersgroups u"),
    @NamedQuery(name = "Usersgroups.findById", query = "SELECT u FROM Usersgroups u WHERE u.id = :id")})
public class Usersgroups implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Users user;
    @JoinColumn(name = "groupp", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Groups group1;

    public Usersgroups() {
    }

    public Usersgroups(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Groups getGroup1() {
        return group1;
    }

    public void setGroup1(Groups group1) {
        this.group1 = group1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usersgroups)) {
            return false;
        }
        Usersgroups other = (Usersgroups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Usersgroups[ id=" + id + " ]";
    }
    
}
