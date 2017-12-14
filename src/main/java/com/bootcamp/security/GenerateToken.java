/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.security;

import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.UserCRUD;
import com.bootcamp.entities.User;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Bello
 */
public class GenerateToken {
    private String token = "";
    
    private boolean verifyUser(String login, String pass){
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("login", "=", login));
        criterias.addCriteria(new Criteria("password", "=", pass));
        List<User> users = UserCRUD.read(criterias);
        
        if (users.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    public String generate(User user){
        if(verifyUser(user.getLogin(), user.getPassword())){
            token = JwtAuthentification.addAuthentication(user);  
        }
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
