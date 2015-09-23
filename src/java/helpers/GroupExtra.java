/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import database.Reviews;
import java.util.List;

/**
 *
 * @author prima
 */
public class GroupExtra {
    private int id;
    private String name;
    private List<ReviewUser> reviews;

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

    public List<ReviewUser> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewUser> reviews) {
        this.reviews = reviews;
    }
    
}
