/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.webservice.model;

import java.util.List;

/**
 *
 * @author dadan
 */
public class ReturnListCity 
{
   List<ListCity> list;
   int         outError;
   String      outMessage;

    public List<ListCity> getList() {
        return list;
    }

    public void setList(List<ListCity> list) {
        this.list = list;
    }

    public int getOutError() {
        return outError;
    }

    public void setOutError(int outError) {
        this.outError = outError;
    }

    public String getOutMessage() {
        return outMessage;
    }

    public void setOutMessage(String outMessage) {
        this.outMessage = outMessage;
    }
   
}
