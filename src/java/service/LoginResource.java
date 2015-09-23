/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controllers.PasswordHash;
import database.Projects;
import database.Tokens;
import database.Users;
import facades.UsersFacade;
import helpers.LoginData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author prima
 */
@Path("login")
public class LoginResource {
    UsersFacade usersFacade = lookupUsersFacadeBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginResource
     */
    public LoginResource() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response login(LoginData login) {
        Users user =  usersFacade.getUser(login.getUsername());
        if(user == null)
            return Response.status(Response.Status.FORBIDDEN).build();
        if(login.checkPassword(user)==false){
            return Response.status(Response.Status.FORBIDDEN).build();
        }else{
            Tokens token = login.checkToken(user);
            return Response.ok(token, MediaType.APPLICATION_JSON).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of LoginResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    private UsersFacade lookupUsersFacadeBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UsersFacade) c.lookup("java:global/Reviewer/UsersFacade!facades.UsersFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
