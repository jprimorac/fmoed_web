/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import database.Admins;
import database.Users;
import facades.AdminsFacade;
import facades.ProjectsFacade;
import facades.UsersFacade;
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

}
