/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.Groups;
import database.Projects;
import database.Reviews;
import database.Usergroupreview;
import helpers.GroupExtra;
import helpers.IdKlasa;
import helpers.ProjectExtra;
import helpers.ReviewUser;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author prima
 */
@Stateless
@Path("projects")
public class ProjectsFacadeREST extends AbstractFacade<Projects> {
    @PersistenceContext(unitName = "ReviewerPU")
    private EntityManager em;

    public ProjectsFacadeREST() {
        super(Projects.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Projects entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Projects entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public ProjectExtra find(@PathParam("id") Integer id) {
        this.em.getEntityManagerFactory().getCache().evictAll();
        Projects project = super.find(id);
        ProjectExtra proExtra = new ProjectExtra();
        proExtra.setId(project.getId());
        proExtra.setName(project.getName());
        proExtra.setDescription(project.getDescription());
        proExtra.setImageLocation(project.getImageLocation());
        List<GroupExtra> groups = new ArrayList<>();
        for(Groups g : project.getGroupsList()){
            GroupExtra group = new GroupExtra();
            group.setId(g.getId());
            group.setName(g.getName());
            List<ReviewUser> reviews = new ArrayList<>();
            List<Usergroupreview> ugrs = g.getUsergroupreviewList();
            for(Usergroupreview ugr : ugrs){
                ReviewUser ru = new ReviewUser();
                ru.setId(ugr.getReview().getId());
                ru.setName(ugr.getReview().getName());
                ru.setComment(ugr.getReview().getComment());
                ru.setRating(ugr.getReview().getGrade());
                ru.setUser(ugr.getUser());
                reviews.add(ru); 
            }
            group.setReviews(reviews);
            groups.add(group);
        }
        proExtra.setGroups(groups);
        return proExtra;
    }

    @GET
    @Produces({ "application/json"})
    public List<IdKlasa> findIds() {
        List<Projects> projects = super.findAll();
        List<IdKlasa> ids = new ArrayList<>();
        for(Projects p : projects){
            IdKlasa id = new IdKlasa();
            id.setId(p.getId());
            ids.add(id);
        }
        return ids;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Projects> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
