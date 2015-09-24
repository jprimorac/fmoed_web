/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.Reviews;
import database.Tokens;
import database.Users;
import facades.UsersFacade;
import helpers.LoginData;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author prima
 */
@Stateless
@Path("reviews")
public class ReviewsFacadeREST extends AbstractFacade<Reviews> {
    @PersistenceContext(unitName = "ReviewerPU")
    private EntityManager em;

    public ReviewsFacadeREST() {
        super(Reviews.class);
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(@HeaderParam("token") String tokenString, Reviews entity) {
        LoginData login = new LoginData();
        Tokens token = login.checkToken(tokenString);
        if(token== null){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Users user = token.getUser();
        entity.setUser(user);

        super.create(entity);
        return Response.status(Response.Status.FORBIDDEN).build();
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
