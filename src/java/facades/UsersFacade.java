/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import database.Users;
import database.Users_;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author prima
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "ReviewerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public int getUserId(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
        Root<Users> a = cq.from(Users.class);
        Predicate uvjet = cb.equal(a.get(Users_.username), username);
        cq.where(uvjet);
        TypedQuery<Users> q = em.createQuery(cq);
        List<Users> users = q.getResultList();
        if(!users.isEmpty()){
            Users user = users.get(0);
            return user.getId();
        }
        else{
            return -1;
        } 
    }
    
    public String getUsertypeName(String username){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
        Root<Users> a = cq.from(Users.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate uvjet = cb.equal(a.get(Users_.username), username);
        predicates.add(uvjet);
        cq.select(a).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Users> q = em.createQuery(cq);
        List<Users> users = q.getResultList();
        if(users.isEmpty()){
            return "NOUSER";
        }
        else{
            return "";
        } 
    }
    
    public Users getUser(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
        Root<Users> a = cq.from(Users.class);
        Predicate uvjet = cb.equal(a.get(Users_.username), username);
        cq.where(uvjet);
        TypedQuery<Users> q = em.createQuery(cq);
        List<Users> users = q.getResultList();
        if(!users.isEmpty()){
            Users user = users.get(0);
            return user;
        }
        else{
            return null;
        } 
    }

    
    public List<String> getUsernames(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
        Root<Users> a = cq.from(Users.class);
        cq.orderBy(cb.desc(a.get("username")));
        TypedQuery<Users> q = em.createQuery(cq);
        List<Users> users = q.getResultList();
        if(!users.isEmpty()){
            List<String> usernames = new ArrayList<>();
            for(Users user : users){
                usernames.add(user.getUsername());
            }
            return usernames;
        }
        else{
            return null;
        } 
    }
    
}
