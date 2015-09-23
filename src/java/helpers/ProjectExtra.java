/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import database.Groups;
import java.util.List;

/**
 *
 * @author prima
 */
public class ProjectExtra {
    private int id;
    private String name;
    private String description;
    private String imageLocation;
    private List<GroupExtra> groups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public List<GroupExtra> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupExtra> groups) {
        this.groups = groups;
    }
    
    
    
}
