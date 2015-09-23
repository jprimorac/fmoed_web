/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import database.Projects;
import facades.ProjectsFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Marko
 */
@ManagedBean
@RequestScoped
public class ProjectsBean {
    private List<Projects> projects;
    
    @EJB
    private ProjectsFacade projectsFacade;

    /**
     * Creates a new instance of ProjectsBean
     */
    public ProjectsBean() {
    }
    
    @PostConstruct
    public void afterConstructor(){
        refreshList();
    }
    public List<Projects> getProjects() {
        return projects;
    }

    public void setProjects(List<Projects> projects) {
        this.projects = projects;
    }
     public void refreshList(){
        this.projects = projectsFacade.findAll();
        
    }
    
}
