/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import database.Files;
import database.Reviews;
import facades.FilesFacade;
import facades.ReviewsFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author prima
 */
@Path("image")
public class ImageResource {
    FilesFacade filesFacade = lookupFilesFacadeBean();
    ReviewsFacade reviewsFacade = lookupReviewsFacadeBean();

    @Context
    private ServletContext context;

    /**
     * Creates a new instance of ImageResource
     */
    public ImageResource() {
    }

    /**
     * Retrieves representation of an instance of service.ImageResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        return "blablabla";
    }

    /**
     * PUT method for updating or creating an instance of ImageResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addImage(@FormDataParam("file") InputStream inputStream, @FormDataParam("reviewId") int reviewId)
    {
        Reviews review = reviewsFacade.find(reviewId);
        if(review!= null){
            savePicture(inputStream, review);
        }

        return Response.ok(reviewId, MediaType.APPLICATION_JSON).build();
    }
    
    private void savePicture(InputStream inputStream, Reviews review) {
        if (inputStream != null) {
            try {
                String fullPath = context.getRealPath("/images/reviews/");
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
                
                Files fileRecord = new Files();
                fileRecord.setLocation(fullPath + "/" + review.getId() + ".jpg");
                fileRecord.setReview(review);
                filesFacade.create(fileRecord);
                System.out.println("Done!");
            } catch (IOException ex) {
                Logger.getLogger(ReviewsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ReviewsFacade lookupReviewsFacadeBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewsFacade) c.lookup("java:global/Reviewer/ReviewsFacade!facades.ReviewsFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private FilesFacade lookupFilesFacadeBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (FilesFacade) c.lookup("java:global/Reviewer/FilesFacade!facades.FilesFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
