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
public class ResultPerso 
{
    int    status;
    String message, reservno, sn, msisdn, persoId;
 
    public String getMessage() 
    {
        return message;
    }

    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getReservno() 
    {
        return reservno;
    }

    public void setReservno(String msisdn) 
    {
        this.reservno = msisdn;
    }

    public String getSn() 
    {
        return sn;
    }

    public void setSn(String sn) 
    {
        this.sn = sn;
    }

    public int getStatus() 
    {
        return status;
    }

    public void setStatus(int status) 
    {
        this.status = status;
    } 

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPersoId() {
        return persoId;
    }

    public void setPersoId(String persoId) {
        this.persoId = persoId;
    }
    
    
}
