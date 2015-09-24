/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controllers.PasswordHash;
import database.Tokens;
import database.Users;
import facades.TokensFacade;
import facades.UsersFacade;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author prima
 */
@Stateless
@Path("users")
public class UsersFacadeREST extends AbstractFacade<Users> {
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private TokensFacade tokensFacade;
    @PersistenceContext(unitName = "ReviewerPU")
    private EntityManager em;

    public UsersFacadeREST() {
        super(Users.class);
    }

    @POST
    @Consumes({"application/json"})
    public Response createUser(Users entity){
            Users old = usersFacade.getUser(entity.getUsername());
            if(old != null){
                return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.CONFLICT).build();
            }
            String password = entity.getPassword();
            String saltAndHash;
        try {
            saltAndHash = PasswordHash.createHash(password);
            String[] nameSaltHash = saltAndHash.split(":");
            entity.setPassword(nameSaltHash[2]);
            entity.setSalt(nameSaltHash[1]);
            super.create(entity);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }       
            Tokens token = new Tokens();
            token.setUser(entity);
            SecureRandom random = new SecureRandom();
            token.setToken(new BigInteger(130, random).toString(32));
            token.setDate(new Date(System.currentTimeMillis()));
            tokensFacade.create(token);
            return javax.ws.rs.core.Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Users entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Users find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Users> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
