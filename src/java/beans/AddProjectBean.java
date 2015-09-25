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
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.security.AccessController.getContext;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
    private UploadedFile picture;
    private UploadedFile thumbnail;

    public void goToThisPage() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/faces/admin/addProject.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewProject() {
        project.setImageLocation(" ");
        project.setThumbnailLocation(" ");
        project.setLatitude(" ");
        project.setLongitude(" ");
        //kreirat grupu
        projectsFacade.create(project);
        Groups defaultGroup = new Groups();
        defaultGroup.setName("default");
        defaultGroup.setProject(project);
        groupsFacade.create(defaultGroup);
        listGroups.add(defaultGroup);
        project.setGroupsList(listGroups);
        defaultGroup = groupsFacade.findAll().get(groupsFacade.count()-1);
        project.setDefaultGroup(defaultGroup.getId());
        projectsFacade.edit(project);
        savePicture();

        clearData();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/faces/admin/projects.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void savePicture() {
        if (picture != null) {
            try {
                ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String fullPath = context.getRealPath("/images/");
                InputStream is = picture.getInputstream();
                File yourFile = new File(fullPath + "/" + project.getId() + ".jpg");
                if (!yourFile.exists()) {
                    yourFile.createNewFile();
                }
                OutputStream outputStream = new FileOutputStream(yourFile);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                
                project.setImageLocation("http://128.199.32.207/Reviewer/images/" + project.getId() + ".jpg");
                
                projectsFacade.edit(project);

                if(thumbnail != null){
                    
                    is = thumbnail.getInputstream();
                    
                    yourFile = new File(fullPath + "/" + project.getId() + "_t.jpg");
                if (!yourFile.exists()) {
                    yourFile.createNewFile();
                }
                outputStream = new FileOutputStream(yourFile);

                read = 0;
                bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                    
                    project.setThumbnailLocation("http://128.199.32.207/Reviewer/images/" + project.getId() + "_t.jpg");
                    projectsFacade.edit(project);
                }

                System.out.println("Done!");
            } catch (IOException ex) {
                Logger.getLogger(AddProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addNewGroup() {
        group.setProject(project);
        listGroups.add(group);
        group = new Groups();
        group.setName("");
    }

    public void clearData() {
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

    public UploadedFile getPicture() {
        return picture;
    }

    public void setPicture(UploadedFile picture) {
        this.picture = picture;
    }

    public UploadedFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(UploadedFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    
}
