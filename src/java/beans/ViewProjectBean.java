/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import database.Projects;
import facades.ProjectsFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@SessionScoped
public class ViewProjectBean {
    @EJB
    private ProjectsFacade projectsFacade;
    private Projects project;
    private boolean chosen=false;

    /**
     * Creates a new instance of viewProject
     */
    public ViewProjectBean() {
        checkChosen();
    }
    public void checkChosen(){
        if(project !=null && project.getId()!=null)
            chosen=true;
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
    
    public void viewProject(Projects project){
        this.project=project;
        checkChosen();
        System.out.println("Test");
        System.out.println("Pr:"+project.getName());
        
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/admin/viewProject.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
        
}
