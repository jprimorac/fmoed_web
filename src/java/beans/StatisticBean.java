/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import database.Reviews;
import facades.GroupsFacade;
import facades.ProjectsFacade;
import facades.ReviewsFacade;
import facades.UsersFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Marko
 */
@ManagedBean
@RequestScoped
public class StatisticBean {
    @EJB
    private GroupsFacade groupsFacade;
    @EJB
    private ReviewsFacade reviewsFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private ProjectsFacade projectsFacade;
    
    
    /**
     * Creates a new instance of StatisticBean
     */
    public StatisticBean() {
    }
    
    public int getNumberOfProjects(){
        return projectsFacade.findAll().size();
    }
    public int getNumberOfUsers(){
        return usersFacade.findAll().size();
    }
    public int getNumberOfReviews(){
        return reviewsFacade.findAll().size();
    }
    public int getNumberOfGroups(){
        return groupsFacade.findAll().size();
    }
    public double getAverageGroupsPerProject(){
        return getNumberOfGroups()/getNumberOfProjects();
    }
    public double getAverageRating(){
        double totalRating=0;
        for(Reviews review:reviewsFacade.findAll()){
            totalRating+=review.getRating();
        }
        return totalRating/getNumberOfReviews();
    }
}
