/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.PasswordHash;
import database.Admins;
import facades.AdminsFacade;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author prima
 */
@ManagedBean
@SessionScoped
public class LoginBean {
    @EJB
    private AdminsFacade adminsFacade;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    private String username;
    private String password;
    private String usernameError;
    private String passwordError;

    @PostConstruct
    public void onView() {
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession();

        String usertype = (String) session.getAttribute("userTypeName");
        if (usertype != null) {
            if (usertype.equals("admin")) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/admin/adminIndex.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (usertype.equals("user")) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/user/userIndex.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String loginUser() {
        if (username != null) {
            if (username.equals("")) {
                usernameError = "Insert username.";
                return "LOGIN";
            } else if (password.equals("")) {
                passwordError = "Insert password.";
                return "LOGIN";
            }
        }

        Admins user = adminsFacade.getUser(username);
        if (user == null) {
            usernameError = "Wrong data entered.";
            return "LOGIN";
        } else {
            boolean checkPassword = checkPassword(user, password);
            if (!checkPassword) {
                usernameError = "Wrong data entered.";
                return "LOGIN";
            }
        }

        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession();
        String returnString = "";
            session.setAttribute("user", username);
            session.setAttribute("userTypeName", "admin");
            session.setAttribute("userObject", user);
            returnString = "ADMIN";
        
        return returnString;
    }

    private boolean checkPassword(Admins user, String password) {
        String iterSaltPass = PasswordHash.PBKDF2_ITERATIONS + ":" + user.getSalt() + ":" + user.getPassword();
        try {
            return PasswordHash.validatePassword(password, iterSaltPass);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void redirectToLogin(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/login.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void logOut(){
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession();
        session.invalidate();
        redirectToLogin();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

}
