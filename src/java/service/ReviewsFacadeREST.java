/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import helpers.Konstante;
import database.Files;
import database.Groups;
import database.Projects;
import database.Reviews;
import database.Tokens;
import database.Users;
import database.Usersgroups;
import facades.FilesFacade;
import facades.GroupsFacade;
import facades.ProjectsFacade;
import facades.UsersgroupsFacade;
import helpers.IdKlasa;
import helpers.LoginData;
import helpers.ReviewGet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author prima
 */
@Stateless
@Path("reviews")
public class ReviewsFacadeREST extends AbstractFacade<Reviews> {
    @EJB
    private GroupsFacade groupsFacade;
    @EJB
    private ProjectsFacade projectsFacade;

    
    

    @PersistenceContext(unitName = "ReviewerPU")
    private EntityManager em;
    
    @Context
    private ServletContext context;

    public ReviewsFacadeREST() {
        super(Reviews.class);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({"application/json"})
    public Response create( @FormDataParam("file") InputStream inputStream, @FormDataParam("token") String tokenString,
                            @FormDataParam("projectId") int projectId, @FormDataParam("comment") String comment, @FormDataParam("rating") double rating,
                            @FormDataParam("time") String time, @FormDataParam("latitude") String latitude, @FormDataParam("longitude") String longitude) {
        LoginData login = new LoginData();
        Tokens token = login.checkToken(tokenString);
        if(token== null){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Users user = token.getUser();
        Reviews entity = new Reviews();
        entity.setName(" ");
        entity.setComment(comment);
        entity.setRating(rating);
        //entity.setTime(time);
        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
        Projects project = projectsFacade.find(projectId);
        for(Groups g : project.getGroupsList()){
            for(Usersgroups ug : g.getUsersgroupsList()){
                if(ug.getUser() == user){
                    entity.setGroupp(g);
                }
            }
        }
        if(entity.getGroupp() == null){
            entity.setGroupp(groupsFacade.find(project.getDefaultGroup()));
        }
        
        entity.setUser(user);
        super.create(entity);
        em.getEntityManagerFactory().getCache().evictAll();
        Reviews last = super.findAll().get(count()-1);
        IdKlasa reviewId = new IdKlasa();
        reviewId.setId(last.getId());
        
        savePicture(inputStream, entity);
        
        //reviewId.setId(1);
        return Response.ok(reviewId, MediaType.APPLICATION_JSON).build();
    }
    
    private void savePicture(InputStream inputStream, Reviews review) {
        if (inputStream != null) {
            try {
                String fullPath = context.getRealPath("/" + Konstante.imageFolder + "/" + Konstante.reviewsFolder);
                File yourFile = new File(fullPath + "/" + review.getId() + ".jpg");
                if (!yourFile.exists()) {
                    yourFile.createNewFile();
                }
                OutputStream outputStream = new FileOutputStream(yourFile);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                
                review.setImageLocation(Konstante.ipAddress + "/" +  Konstante.appName + "/" + Konstante.imageFolder + "/" + Konstante.reviewsFolder + "/"+review.getId() + ".jpg");
                super.edit(review);
                System.out.println("Done!");
            } catch (IOException ex) {
                Logger.getLogger(ReviewsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    @GET
    @Path("{id}")
    @Produces({ "application/json"})
    public Reviews find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Reviews> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Reviews> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
