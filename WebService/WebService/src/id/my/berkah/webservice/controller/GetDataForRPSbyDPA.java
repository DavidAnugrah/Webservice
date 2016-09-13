/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.webservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.my.berkah.rps.background.AKI;
import id.my.berkah.rps.controller.EncryptDecryptEAS128;
import id.my.berkah.rps.controller.EncryptDecryptOPandTransfortKEY;
import id.my.berkah.webservice.dao.WSMapper;
import id.my.berkah.webservice.model.ResultPerso;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetDataForRPSbyDPA", urlPatterns = {"/GetDataForRPSbyDPA"})
public class GetDataForRPSbyDPA extends HttpServlet 
{
    //String addr;
    //String command;
    //String buid;
    //String userid;
    //String reserveNo;
    //String serialNo; 
    //String aki;
    //String pwd;
    //String userapi;
    //String url;
    
    //Collection<ResultPerso> collection = new ArrayList();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String command = null;
        String buid = null;
        String userid = null;
        String reserveNo = null; 
        String aki = null;
        String pwd = null;
        String userapi = null;
        String url = null;
        String serialNo = "";
        String msisdn = "";
        String  format = "userApi=xxx&pwdApi=xxx&userId=xxx&buId&xxx&reserveNo=xx&msisdn=xxx&serialNo=xx";
        
        //collection.clear();
        url = request.getScheme() + "://" +   // "http" + "://
              request.getServerName() +       // "myhost"
              ":" +                           // ":"
              request.getServerPort() +       // "8080"
              //request.getRequestURI();
              //request.getServletPath();
                request.getContextPath();
                
        String requestType = request.getMethod();
        String addr = request.getRemoteAddr();

        Boolean waras = true;
        
        ResultPerso result = new ResultPerso();
        
        if (requestType.equals("POST"))
        {
            command = getPostData(request).trim();

            String[] component = command.split("&");
            if (component.length == 7)
            {
                for (int i =0; i < component.length; i++)
                {   
                    
                    component[i] = component[i].trim();
                    String[] node = component[i].split("=");

                    if (node[0].toUpperCase().equals("USERAPI"))
                    {
                        userapi = node[1];
                    }
                    else
                    {
                        if (node[0].toUpperCase().equals("PWDAPI"))
                        {
                            pwd = node[1];
                        } 
                        else
                        {
                            if (node[0].toUpperCase().equals("USERID"))
                            {
                                userid = node[1];
                            }
                            else
                            {
                                if (node[0].toUpperCase().equals("BUID"))
                                {
                                    buid = node[1];
                                }
                                else
                                {
                                    if (node[0].toUpperCase().equals("RESERVENO"))
                                    {
                                        reserveNo = node[1];
                                    } 
                                    else
                                    {
                                        if (node[0].toUpperCase().equals("MSISDN"))
                                        {
                                            msisdn = node[1];
                                        } 
                                        else
                                        {
                                            if (node[0].toUpperCase().equals("SERIALNO"))
                                            {
                                                serialNo = node[1].replace("\n", "").replace("\r", "").replace("null", "");
                                            } 
                                            else
                                            {
                                                waras = false;
                                                result.setStatus(1);
                                                result.setMessage("Salah format, seharusnya format adalah -> " + format + " Request dari server anda adalah [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
                                                result.setSn(serialNo);
                                                result.setReservno(reserveNo); 
                                                result.setMsisdn(msisdn);
                                                result.setPersoId("");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }                   
            }
            else
            {
                waras = false;
                result.setStatus(1);
                result.setMessage("Salah format, seharusnya format adalah -> " + format + " Request dari server anda adalah [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
                result.setSn(serialNo);
                result.setReservno(reserveNo); 
                result.setMsisdn(msisdn);
                result.setPersoId("");
            }
        }
        else if (requestType.equals("GET"))
        {
            waras = false;
            result.setStatus(1);
            result.setMessage("Request Method hanya bisa POST dan kirim data dengan format " + format);
            result.setSn(serialNo);
            result.setReservno(reserveNo); 
            result.setMsisdn(msisdn);
            result.setPersoId("");
        }
       
        try
        {
            if (waras)
            {
                result = getData(buid, userid, userapi, pwd, addr, url, serialNo, reserveNo, msisdn);
                PrintWriter out = response.getWriter();
            
                try 
                {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String json = gson.toJson(result);
                    response.setContentType("text/json;charset=UTF-8");
                    json = json.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "=").replace("\\u0026", "&");
                    out.println(json);
                }
                finally 
                {            
                    out.close();
                }
            }
        }
        catch (Exception ex)
        {
            result.setStatus(1);
            result.setMessage(ex.getMessage());
            result.setSn(serialNo);
            result.setReservno(reserveNo); 
            result.setMsisdn(msisdn);
            result.setPersoId("");
        }
        
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
       
        try 
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(result);
            json = json.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "=").replace("\\u0026", "&");
            out.println(json);
        } 
        finally 
        {            
            out.close();
        }
    }

    String getPostData(HttpServletRequest req) throws UnsupportedEncodingException 
    {
        StringBuilder sb = new StringBuilder();
        try 
        {
            BufferedReader reader = req.getReader();
            reader.mark(10000);

            String line;
            do 
            {
                line = reader.readLine();
                sb.append(line).append("\n");
            } 
            while (line != null);
            
            reader.reset();
            // do NOT close the reader here, or you won't be able to get the post data twice
        } 
        catch(IOException e) 
        {
            //logger.warn("getPostData couldn't.. get the post data", e);  // This has happened if the request's reader is closed    
        }

        return converStringToUrlDecoded(sb.toString());
    }
    
    ResultPerso getData(String buid, String userid, String userapi, String pwd, String addr, String url, String serialNo, String reserveNo, String msisdn)
    {
        String c_messsage = "";
        Boolean lanjut = true;            

        ResultPerso result = new ResultPerso();
        try
        {            
            WSMapper model = new WSMapper();            
            //whitelist
            Map wl = model.goCheckIPWhiteList(addr);
            if ((Integer)wl.get("outError") == 0)
            {
                //user api
                Map ua = model.goCheckUserAPI(userapi, pwd);
                if ((Integer)ua.get("outError") == 0)
                {
                    Map key = model.goOPKeyAndTKey();
                    if ((Integer)key.get("outError") == 0)
                    {
                        EncryptDecryptOPandTransfortKEY en = new EncryptDecryptOPandTransfortKEY();
                        String transportkey = en.DecryptText((String)key.get("tKey"));
                        String opkey = en.DecryptText((String)key.get("opKey"));
                        
                        if (transportkey.equals("") ||opkey.equals(""))
                        {
                            result.setStatus(1);
                            result.setMessage("Decrypt TransportKey dan OPKey Gagal, Mungkin salah Key.");
                            result.setSn(serialNo);
                            result.setReservno(reserveNo);  
                            result.setMsisdn(msisdn);
                            result.setPersoId("");
                        }
                        else
                        {
                            AKI a = new AKI();
                            String clear_ki = a.genClearText();
                            //String encrypt_ki = a.Encrypt4KI(clear_ki, transportkey);

                            Map data = model.ClientGetDataPersoATM(buid, serialNo, reserveNo, userid, msisdn);
                            if ((Integer)data.get("outError") == 0)
                            {
                                 
                                 clear_ki = a.Decrypt4KIWithTransfortKey(data.get("aki").toString() ,transportkey);
                                 
                                 if (data.get("simType").toString().equals("1"))
                                 {
                                       url = url + "/UpdateHasilPersoDPA?";
                                       
                                       c_messsage = data.get("persoId").toString()  + ";" +
                                                    data.get("dllName").toString()  + ";" +
                                                    data.get("version").toString()  + ";" +
                                                    data.get("profile").toString()   + ";" +
                                                    data.get("authKey").toString()   + ";" +
                                                    data.get("iccid").toString().substring(0, 18) + ";" +
                                                    data.get("imsi").toString() + ";" +
                                                    clear_ki.toUpperCase()  + ";" +
                                                    data.get("smpp").toString()    + ";" +
                                                    data.get("pin1").toString()   + ";" +
                                                    data.get("pin2").toString()    + ";" +
                                                    data.get("puk1").toString()   + ";" +
                                                    data.get("puk2").toString()    + ";" +
                                                    ""   + ";" +
                                                    url + ";" + 
                                                    data.get("status").toString()   + ";" +
                                                    userid;

                                }
                                else
                                {
                                    if (data.get("simType").toString().equals("2"))
                                    {
                                        EncryptDecryptEAS128 eas = new EncryptDecryptEAS128();
                                        int opc = eas.encryptOPC(clear_ki, opkey);

                                        if (opc == 1)
                                        {
                                           url = url + "/UpdateHasilPersoEAIDPA?";
                                           
                                           c_messsage = data.get("persoId").toString()  + ";" +
                                                        data.get("dllName").toString()  + ";" +
                                                        data.get("version").toString()  + ";" +
                                                        data.get("profile").toString()   + ";" +
                                                        data.get("authKey").toString()   + ";" +
                                                        data.get("iccid").toString().substring(0, 18) + ";" +
                                                        data.get("imsi").toString() + ";" +
                                                        clear_ki.toUpperCase()  + ";" +
                                                        data.get("smpp").toString()    + ";" +
                                                        data.get("pin1").toString()   + ";" +
                                                        data.get("pin2").toString()    + ";" +
                                                        data.get("puk1").toString()   + ";" +
                                                        data.get("puk2").toString()    + ";" +
                                                        ""   + ";" +
                                                        url + ";" + 
                                                        data.get("status").toString()   + ";" +
                                                        userid + ";" +                                     
                                                        eas.getOPC();

                                        }
                                        else
                                        {
                                            result.setStatus(1);
                                            result.setMessage(eas.getMessage());
                                            result.setSn(serialNo);
                                            result.setReservno(reserveNo);   
                                            result.setMsisdn(msisdn);
                                            lanjut = false;
                                        }
                                    }
                                    else
                                    { 
                                        if (data.get("simType").toString().equals("3"))                                    
                                        {
                                                url = url + "/UpdateHasilPersoEAIDPA?";
                                               
                                                c_messsage = data.get("persoId").toString()  + ";" +
                                                             data.get("dllName").toString()  + ";" +
                                                             data.get("version").toString()  + ";" +
                                                             data.get("profile").toString()   + ";" +
                                                             data.get("authKey").toString()   + ";" +
                                                             data.get("iccid").toString().substring(0, 18) + ";" +
                                                             data.get("imsi").toString() + ";" +
                                                             clear_ki.toUpperCase()  + ";" +
                                                             data.get("smpp").toString()    + ";" +
                                                             data.get("pin1").toString()   + ";" +
                                                             data.get("pin2").toString()    + ";" +
                                                             data.get("puk1").toString()   + ";" +
                                                             data.get("puk2").toString()    + ";" +
                                                             ""   + ";" +
                                                             url + ";" + 
                                                             data.get("status").toString()   + ";" +
                                                             userid;
                                        }
                                    }
                            }
                                 
                            if (lanjut)
                            {
                                //System.out.println(c_messsage);
                                String p_messsage = en.encryptText2(c_messsage);
                                result.setStatus(0);
                                result.setMessage(p_messsage);
                                result.setSn(serialNo);
                                result.setReservno(reserveNo);    
                                result.setMsisdn(msisdn);
                                result.setPersoId(data.get("persoId").toString());
                            }

                        }
                        else
                        {
                            result.setStatus(1);
                            result.setMessage((String)data.get("outMessage"));
                            result.setSn(serialNo);
                            result.setReservno(reserveNo);  
                            result.setMsisdn(msisdn);
                            result.setPersoId("");
                        }
                    }
                }
                else
                {
                    result.setStatus(1);
                    result.setMessage((String)key.get("outMessage"));
                    result.setSn(serialNo);
                    result.setReservno(reserveNo);    
                    result.setMsisdn(msisdn);
                    result.setPersoId("");
                }
            }
            else
            {
                result.setStatus(1);
                result.setMessage((String)ua.get("outMessage"));
                result.setSn(serialNo);
                result.setReservno(reserveNo);  
                result.setMsisdn(msisdn);
                result.setPersoId("");
            }
        }
        else
        {
            result.setStatus(1);
            result.setMessage((String)wl.get("outMessage"));
            result.setSn(serialNo);
            result.setReservno(reserveNo); 
            result.setMsisdn(msisdn);
            result.setPersoId("");
        }
        return result;
    }
    catch (Exception e)
    {
        System.out.println(e.getStackTrace());

        result.setStatus(1);
        result.setMessage(e.getMessage());
        result.setSn(serialNo);
        result.setReservno(reserveNo); 
        result.setMsisdn(msisdn);
        result.setPersoId("");
        return result;
    }        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        processRequest(request, response);
    }
    
    private String converStringToUrlDecoded(String data) throws UnsupportedEncodingException 
    {
        String decodedString = java.net.URLDecoder.decode(data, "UTF-8");
        return decodedString;
    }
    
}
