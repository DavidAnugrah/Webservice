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
public class ResultInsertTrcId {
    int outError;
    String outMessage, trcId;

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

    public String getTrcId() {
        return trcId;
    }

    public void setTrcId(String trcId) {
        this.trcId = trcId;
    }

}
