/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.webservice.model;

/**
 *
 * @author dadan
 */
public class ParamUserAPI 
{
    String userApi, password, outMessage;
    int    outError;

    public String getUserApi() {
        return userApi;
    }

    public void setUserApi(String userApi) {
        this.userApi = userApi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOutMessage() {
        return outMessage;
    }

    public void setOutMessage(String outMessage) {
        this.outMessage = outMessage;
    }

    public int getOutError() {
        return outError;
    }

    public void setOutError(int outError) {
        this.outError = outError;
    }
    
    
}
