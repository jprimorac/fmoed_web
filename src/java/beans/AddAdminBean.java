/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.PasswordHash;
import database.Admins;
import database.Projects;
import facades.AdminsFacade;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marko
 */
@ManagedBean
@RequestScoped
public class AddAdminBean {

    @EJB
    private AdminsFacade adminsFacade;

    private Admins admin;

    /**
     * Creates a new instance of addAdmin
     */
    public AddAdminBean() {
        admin = new Admins();
    }

    public Admins getAdmin() {
        return admin;
    }

    public void setAdmin(Admins admin) {
        this.admin = admin;
    }

    public void viewAddAdmin() {

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/faces/admin/addAdmin.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewAdmin() {
        System.out.println("Prolazi");
        try {
            String pass = PasswordHash.createHash(admin.getPassword());
            String[] hashed = pass.split(":");
            admin.setPassword(hashed[2]);
            admin.setSalt(hashed[1]);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AddAdminBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AddAdminBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        adminsFacade.create(admin);

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/faces/admin/users.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
