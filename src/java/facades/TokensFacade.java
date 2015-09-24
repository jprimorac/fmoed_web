/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import database.Tokens;
import database.Tokens_;
import database.Users;
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
public class TokensFacade extends AbstractFacade<Tokens> {
    @PersistenceContext(unitName = "ReviewerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokensFacade() {
        super(Tokens.class);
    }
    
    public Tokens getTokenByUser(Users user){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tokens> cq = cb.createQuery(Tokens.class);
        Root<Tokens> a = cq.from(Tokens.class);
        Predicate uvjet = cb.equal(a.get(Tokens_.user), user);
        cq.where(uvjet);
        TypedQuery<Tokens> q = em.createQuery(cq);
        List<Tokens> tokens = q.getResultList();
        if(tokens.isEmpty()){
            return null;
        }
        else{
            return tokens.get(0);
        }
    }
    
    public Tokens getTokenByToken(String tokenString){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tokens> cq = cb.createQuery(Tokens.class);
        Root<Tokens> a = cq.from(Tokens.class);
        Predicate uvjet = cb.equal(a.get(Tokens_.token), tokenString);
        cq.where(uvjet);
        TypedQuery<Tokens> q = em.createQuery(cq);
        List<Tokens> tokens = q.getResultList();
        if(tokens.isEmpty()){
            return null;
        }
        else{
            return tokens.get(0);
        }
    }
}
