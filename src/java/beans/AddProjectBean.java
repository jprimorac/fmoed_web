/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import database.Groups;
import database.Projects;
import facades.GroupsFacade;
import facades.ProjectsFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author prima
 */
@ManagedBean
@RequestScoped
public class AddProjectBean {
    @EJB
    private GroupsFacade groupsFacade;
    @EJB
    private ProjectsFacade projectsFacade;

    /**
     * Creates a new instance of AddProjectBean
     */
    public AddProjectBean() {
    }
    
    private Projects project;
    private Groups group;
    
    public void goToThisPage(){
        projectsFacade.create(project);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/admin/addProjects.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void AddNewProject(){
        projectsFacade.create(project);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/admin/projects.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void AddNewGroup(){
        project.getGroupsList().add(group);
        group = new Groups();
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }
    
    
}
