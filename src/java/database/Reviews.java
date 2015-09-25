/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prima
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reviews.findAll", query = "SELECT r FROM Reviews r"),
    @NamedQuery(name = "Reviews.findById", query = "SELECT r FROM Reviews r WHERE r.id = :id"),
    @NamedQuery(name = "Reviews.findByName", query = "SELECT r FROM Reviews r WHERE r.name = :name"),
    @NamedQuery(name = "Reviews.findByComment", query = "SELECT r FROM Reviews r WHERE r.comment = :comment"),
    @NamedQuery(name = "Reviews.findByRating", query = "SELECT r FROM Reviews r WHERE r.rating = :rating"),
    @NamedQuery(name = "Reviews.findByType", query = "SELECT r FROM Reviews r WHERE r.type = :type"),
    @NamedQuery(name = "Reviews.findByLatitude", query = "SELECT r FROM Reviews r WHERE r.latitude = :latitude"),
    @NamedQuery(name = "Reviews.findByLongitude", query = "SELECT r FROM Reviews r WHERE r.longitude = :longitude"),
    @NamedQuery(name = "Reviews.findByTime", query = "SELECT r FROM Reviews r WHERE r.time = :time")})
public class Reviews implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String name;
    @Size(max = 255)
    @Column(length = 255)
    private String comment;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double rating;
    private Integer type;
    @Size(max = 255)
    @Column(length = 255)
    private String latitude;
    @Size(max = 255)
    @Column(length = 255)
    private String longitude;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "image_location", nullable = true, length = 255)
    private String imageLocation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review")
    private List<Usergroupreview> usergroupreviewList;
    @JoinColumn(name = "groupp", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Groups groupp;
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Users user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review")
    private List<Files> filesList;

    public Reviews() {
    }

    public Reviews(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @XmlTransient
    public List<Usergroupreview> getUsergroupreviewList() {
        return usergroupreviewList;
    }

    public void setUsergroupreviewList(List<Usergroupreview> usergroupreviewList) {
        this.usergroupreviewList = usergroupreviewList;
    }

    public Groups getGroupp() {
        return groupp;
    }

    public void setGroupp(Groups groupp) {
        this.groupp = groupp;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    @XmlTransient
    public List<Files> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<Files> filesList) {
        this.filesList = filesList;
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
        if (!(object instanceof Reviews)) {
            return false;
        }
        Reviews other = (Reviews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Reviews[ id=" + id + " ]";
    }
    
}
