/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import database.Tokens;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
