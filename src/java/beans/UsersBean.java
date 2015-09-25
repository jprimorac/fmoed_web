/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import database.Admins;
import database.Groups;
import database.Projects;
import database.Users;
import database.Usersgroups;
import facades.AdminsFacade;
import facades.ProjectsFacade;
import facades.UsersFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marko
 */
@ManagedBean
@RequestScoped
public class UsersBean {
    @EJB
    private ProjectsFacade projectsFacade;

    @EJB
    private AdminsFacade adminsFacade;
    @EJB
    private UsersFacade usersFacade;
    private List<Users> users;
    private List<Admins> admins;

    /**
     * Creates a new instance of UsersBean
     */
    public UsersBean() {
    }

    @PostConstruct
    public void afterConstructor() {
        System.out.println("TestUserConstruct");
        refreshList();
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public void refreshList() {
        
        this.users = usersFacade.findAll();
        this.admins = adminsFacade.findAll();

    }

    public List<Admins> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admins> admins) {
        this.admins = admins;
    }
    public List<Projects> getProjectsOfUser(Users user1){
        Users user = usersFacade.find(user1.getId());
        refreshList();
        List<Projects> projects = new ArrayList<>();
        for(Usersgroups usergroup:user.getUsersgroupsList()){
            if(!projects.contains(usergroup.getGroup1().getProject()))
                projects.add(usergroup.getGroup1().getProject());
        }
        return projects;
    }
}
