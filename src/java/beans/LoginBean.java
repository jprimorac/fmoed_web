/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facades.UsersFacade;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marko
 */
@ManagedBean
@SessionScoped
public class LoginBean {
    
    @EJB
    private UsersFacade usersFacade;
    
    
    
    public LoginBean() {
    }
    private String username;
    private String password;
    private String usernameError;
    private String passwordError;

    @PostConstruct
    public void onView() {
        /*HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
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
         }*/
    }

    public String loginUser() {

        return "";
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
