/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import database.Admins;
import database.Admins_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author prima
 */
@Stateless
public class AdminsFacade extends AbstractFacade<Admins> {
    @PersistenceContext(unitName = "ReviewerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminsFacade() {
        super(Admins.class);
    }
    
    public Admins getUser(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Admins> cq = cb.createQuery(Admins.class);
        Root<Admins> a = cq.from(Admins.class);
        Predicate uvjet = cb.equal(a.get(Admins_.username), username);
        cq.where(uvjet);
        TypedQuery<Admins> q = em.createQuery(cq);
        List<Admins> users = q.getResultList();
        if(!users.isEmpty()){
            Admins user = users.get(0);
            return user;
        }
        else{
            return null;
        } 
    }
    
}
