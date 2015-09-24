/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import database.Groups;
import database.Projects;
import database.Reviews;
import database.Users;
import database.Usersgroups;
import facades.ProjectsFacade;
import facades.UsersFacade;
import facades.UsersgroupsFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marko
 */
@ManagedBean
@RequestScoped
public class ViewProjectBean {
    @EJB
    private UsersgroupsFacade usersgroupsFacade;
    @EJB
    private UsersFacade usersFacade;

    

    @EJB
    private ProjectsFacade projectsFacade;
    private static Projects project;
    private static double rating;
    private boolean chosen = false;

    private Users chosenUser;
    private Groups chosenGroup;
    private List<Users> allOtherUsers = null;
    private List<Groups> groupsOfThisProject = null;

    /**
     * Creates a new instance of viewProject
     */
    public ViewProjectBean() {
        

    }
    
    @PostConstruct
    public void checkChosenCheck(){
        checkChosen();
        if (chosen) {
            refreshProjectGroups();
        }
    }

    public void checkChosen() {
        if (project != null && project.getId() != null) {
            chosen = true;
        }
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    private void refreshProjectGroups() {
        rating = 0;
        int counter = 0;
        for (Groups group : project.getGroupsList()) {
            for (Reviews rev : group.getReviewsList()) {
                rating += rev.getRating();
                counter++;
            }
        }
        rating /= counter;
        System.out.println("Test");
        System.out.println("Pr:" + project.getName());

        allOtherUsers = new ArrayList<>();
        List<Users> allUsers = usersFacade.findAll();
        
        if(allUsers!=null && !allUsers.isEmpty())
            allOtherUsers.addAll(allUsers);

        
        for (Users userAll : allUsers) {
            for (Users userInProject : getProjectUsers()) {
                if (userAll.getId().equals(userInProject.getId())) {
                    if (!allOtherUsers.isEmpty()) {
                        allOtherUsers.remove(userAll);
                    }
                }
            }
        }
    }

    public void viewProject(Projects project) {
        this.project = project;
        checkChosen();
        refreshProjectGroups();

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/faces/admin/viewProject.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addUserToGroup(){
        System.out.println("Prolazi addUserToGroup");
        Usersgroups newUsersGroup = new Usersgroups();
        System.out.println(chosenGroup.getName());
        System.out.println(chosenUser.getUsername());
        newUsersGroup.setGroup1(chosenGroup);
        newUsersGroup.setUser(chosenUser);
        usersgroupsFacade.create(newUsersGroup);
    }
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getGroupAvgRating(Groups g) {
        double groupRate = 0;
        for (Reviews r : g.getReviewsList()) {
            groupRate += r.getRating();
        }
        return groupRate / g.getReviewsList().size();
    }

    public List<Users> getProjectUsers() {
        List<Users> users = new ArrayList<>();
        for (Groups g : project.getGroupsList()) {
            for (Usersgroups ug : g.getUsersgroupsList()) {
                users.add(ug.getUser());
            }
        }
        return users;
    }

    public Users getChosenUser() {
        return chosenUser;
    }

    public void setChosenUser(Users chosenUser) {
        this.chosenUser = chosenUser;
    }

    public List<Users> getAllOtherUsers() {
        return allOtherUsers;
    }

    public void setAllOtherUsers(List<Users> allOtherUsers) {
        this.allOtherUsers = allOtherUsers;
    }

    public List<Groups> getGroupsOfThisProject() {
        return groupsOfThisProject;
    }

    public void setGroupsOfThisProject(List<Groups> groupsOfThisProject) {
        this.groupsOfThisProject = groupsOfThisProject;
    }

    public Groups getChosenGroup() {
        return chosenGroup;
    }

    public void setChosenGroup(Groups chosenGroup) {
        this.chosenGroup = chosenGroup;
    }

    
}
