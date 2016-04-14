/*

Copyright 2011 LinkedIn Corporation

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package com.criteo.hackathon;

import com.criteo.hackathon.utils.UserProfile;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class LinkedinApiMain {

    private static String API_KEY = "75hboprbpjfv1c";
    private static String API_SECRET = "xtPrmscbaCpEqn8D";

    public static void main(String[] args) {


        /*
        we need a OAuthService to handle authentication and the subsequent calls.
        Since we are going to use the REST APIs we need to generate a request token as the first step in the call.
        Once we get an access toke we can continue to use that until the API key changes or auth is revoked.
        Therefore, to make this sample easier to re-use we serialize the AuthHandler (which stores the access token) to
        disk and then reuse it.

        When you first run this code please insure that you fill in the API_KEY and API_SECRET above with your own
        credentials and if there is a service.dat file in the code please delete it.

         */

        //The Access Token is used in all Data calls to the APIs - it basically says our application has been given access
        //to the approved information in LinkedIn
        Token accessToken = null;

        //Using the Scribe library we enter the information needed to begin the chain of Oauth2 calls.
        OAuthService service = new ServiceBuilder()
                .provider(LinkedInApi.class)
                .apiKey(API_KEY)
                .apiSecret(API_SECRET)
                .build();

        /*************************************
         * This first piece of code handles all the pieces needed to be granted access to make a data call
         */

        try {
            File file = new File("service.dat");

            if (file.exists()) {
                //if the file exists we assume it has the AuthHandler in it - which in turn contains the Access Token
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                AuthHandler authHandler = (AuthHandler) inputStream.readObject();
                accessToken = authHandler.getAccessToken();
            } else {
                System.out.println("There is no stored Access token we need to make one");
                //In the constructor the AuthHandler goes through the chain of calls to create an Access Token
                AuthHandler authHandler = new AuthHandler(service);
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("service.dat"));
                outputStream.writeObject(authHandler);
                outputStream.close();
                accessToken = authHandler.getAccessToken();
            }

        } catch (Exception e) {
            System.out.println("Threw an exception when serializing: " + e.getClass() + " :: " + e.getMessage());
        }

        /*
         * We are all done getting access - time to get busy getting data
         *************************************/

        UserProfile userProfile = new UserProfile(service, accessToken);

//        userProfile.getProfile("~");
        System.out.println(userProfile.searchProfile());

    }

}
