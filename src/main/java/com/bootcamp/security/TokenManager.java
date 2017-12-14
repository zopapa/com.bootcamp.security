/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.security;

import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.crud.UserCRUD;
import com.bootcamp.entities.User;
import org.springframework.http.HttpRequest;

import java.util.List;

/**
 *
 * @author Bello
 */
public class TokenManager {

    private static boolean verifyUser(String login, String pass){
        Criterias criterias = new Criterias();

        Criteria criteria = new Criteria();
        Rule rule = new Rule();
        rule.setEntityClass(User.class);
        rule.setColumn("login");
        rule.setOperator("=");
        rule.setValue(login);
        criteria.setRule(rule);
        criteria.setLinkOperator("AND");

        Criteria criteria1 = new Criteria();
        Rule rule1 = new Rule();
        rule1.setEntityClass(User.class);
        rule1.setColumn("password");
        rule1.setOperator("=");
        rule1.setValue(pass);
        criteria1.setRule(rule);

        criterias.addCriteria(criteria);

        criterias.addCriteria(criteria1);
        List<User> users = UserCRUD.read(criterias);
        
        if (users.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    public static String generate(User user){
        String token=null;
        if(verifyUser(user.getLogin(), user.getPassword())){
             token = JwtAuthentification.addAuthentication(user);
        }
        return token;
    }

    public static boolean verifyToken(HttpRequest httpRequest){
    String userInString = JwtAuthentification.tokenAuthentication(httpRequest);
    if (!userInString.isEmpty()){
    String[] infos = userInString.split("-");
        return verifyUser(infos[0],infos[1]) ?
                true :
                false;
    }

    return false;
    }

    
}
