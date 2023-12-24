package com.jobbud.ws.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api/v1.0/microtransactions")
public class MicroTransactionController {
    @GetMapping("/oauth2/youtube/clientUrl")
    public String getClientUrl(){
      //we will return url that will redirect user to youtube consent screen
        //Also redirect uri will change to our frontend url like localhost:3000/oAuth2/initCall/
        //Related this page will sent code to the endpoint that "/oauth2/youtube/callback"
        return "";
    }

    @GetMapping("/oauth2/youtube/callback")
    public String getAccessTokenViaCode() {
      /*
      Sent post request to 	https://oauth2.googleapis.com/token with body that contains code and other required fields
      get access token from response and send it to checkIfUserSubscribed() method
       */

       return "";
    }
    public String checkIfUserSubscribed(String accessToken) throws IOException, URISyntaxException {
        //we will send get request to youtube api with access token and get user info

        //then we will save necessary info to our database


        //NOTE FOR MYSELF: DONT FORGET TO CHANGE API SETTINGS ABOUT ALLOWED USERS AND PUBLISHING STATUS
        return "";
    }

}