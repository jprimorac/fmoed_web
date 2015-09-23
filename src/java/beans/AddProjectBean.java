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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author prima
 */
@ManagedBean
@SessionScoped
public class AddProjectBean {
    @EJB
    private GroupsFacade groupsFacade;
    @EJB
    private ProjectsFacade projectsFacade;

    /**
     * Creates a new instance of AddProjectBean
     */
    public AddProjectBean() {
        project = new Projects();
        group = new Groups();
        listGroups = new ArrayList<>();
    }
    
    private Projects project;
    private Groups group;
    private List<Groups> listGroups;
    
    public void goToThisPage(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/admin/addProject.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void addNewProject(){
        project.setImageLocation(" ");
        projectsFacade.create(project);
        project.setGroupsList(listGroups);
        projectsFacade.edit(project);
        clearData();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    context.redirect(context.getRequestContextPath() + "/faces/admin/projects.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                
    }
    
    public void addNewGroup(){
        group.setProject(project);
        listGroups.add(group);
        group = new Groups();
        group.setName("");
   }
    
    public void clearData(){
        project = new Projects();
        group = new Groups();
        listGroups = new ArrayList<>();
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

    public List<Groups> getListGroups() {
        return listGroups;
    }

    public void setListGroups(List<Groups> listGroups) {
        this.listGroups = listGroups;
    }
    
    
}
