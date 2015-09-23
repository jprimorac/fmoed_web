/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import beans.LoginBean;
import controllers.PasswordHash;
import database.Tokens;
import database.Users;
import facades.TokensFacade;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author prima
 */
public class LoginData {

    TokensFacade tokensFacade = lookupTokensFacadeBean();
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(Users user) {
        String iterSaltPass = PasswordHash.PBKDF2_ITERATIONS + ":" + user.getSalt() + ":" + user.getPassword();
        try {
            return PasswordHash.validatePassword(password, iterSaltPass);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Tokens checkToken(Users user) {
        Tokens token = tokensFacade.getTokenByUser(user);
        if (token != null) {
            Date date = token.getDate();
            Date twoHours = new Date(System.currentTimeMillis() - 7200000);
            if (date.before(twoHours)) {
                SecureRandom random = new SecureRandom();
                token.setToken(new BigInteger(130, random).toString(32));
                token.setDate(new Date(System.currentTimeMillis()));
                tokensFacade.edit(token);
            }
        } else {
            token = new Tokens();
            token.setUser(user);
            SecureRandom random = new SecureRandom();
            token.setToken(new BigInteger(130, random).toString(32));
            token.setDate(new Date(System.currentTimeMillis()));
            tokensFacade.create(token);
        }
        return token;
    }

    private TokensFacade lookupTokensFacadeBean() {
        try {
            Context c = new InitialContext();
            return (TokensFacade) c.lookup("java:global/Reviewer/TokensFacade!facades.TokensFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
