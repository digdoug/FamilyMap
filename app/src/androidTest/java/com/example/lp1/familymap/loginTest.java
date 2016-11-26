package com.example.lp1.familymap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import com.example.lp1.familymap.model.Login;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by lp1 on 4/13/16.
 */
public class loginTest {

    LoginFragment loginFragment;
    LoginFragment.DownloadTask downloadTask;
    Login loginModel;

    public void testLogin(){
        // how the heck am I supposed to verify this data?
        loginModel = Login.getInstance();
        try {
            URL badUrl1 = new URL("nothing");
            URL badUrl2 = new URL("");
            URL badUrl3 = new URL("assdfasdfasdf");

            // bad test 1
            downloadTask.execute(badUrl1);
            assertEquals(true, loginModel.isDidLoginIn());

            // bad test2
            downloadTask.execute(badUrl2);
            assertEquals(false,loginModel.isDidLoginIn());

            // bad test3
            downloadTask.execute(badUrl3);
            assertEquals(false,loginModel.isDidLoginIn());

            URL goodUrl1 = new URL("http://192.168.0.11:8080/user/login");
            URL goodUrl2 = new URL("http://10.14.35.163:8080/user/login");

            loginModel.setPostData("{username:\"lpinto1\",password:\"password\"}");

            // good test 1
            assertEquals(true,goodUrl1);

            // good test 2
            assertEquals(true,goodUrl2);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
