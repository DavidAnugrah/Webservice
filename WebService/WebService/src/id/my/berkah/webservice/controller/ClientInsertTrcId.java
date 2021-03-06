/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.webservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.my.berkah.webservice.dao.WSMapper;
import id.my.berkah.webservice.model.ResultInsertTrcId;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dadan
 */
@WebServlet(name = "ClientInsertTrcId", urlPatterns = {"/ClientInsertTrcId"})
public class ClientInsertTrcId extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, GeneralSecurityException 
    {
        String  command = "";
        String  clientId = "";
        String  userApi = "";
        String  pwdApi = "";
        
        String  format = "userApi=xx&pwdApi=xx&clientId=xxx";
            
        String addr = request.getRemoteAddr();
                
        String requestType = request.getMethod();

        Boolean waras = true;
        
        ResultInsertTrcId result = new ResultInsertTrcId();
        
        if (requestType.equals("POST"))
        {
            command = getPostData(request).trim().replace("\n", "").replace("\r", "").replace("null", "");
            
            String[] component = command.split("&");
            if (component.length == 3)
            {
                for (int i =0; i < component.length; i++)
                {   
                    
                    component[i] = component[i].trim();
                    String[] node = component[i].split("=");

                    if (node[0].toUpperCase().equals("USERAPI"))
                    {
                        if (node.length > 1)
                        {
                            userApi = node[1];
                        }
                    }
                    else
                    {
                        if (node[0].toUpperCase().equals("PWDAPI"))
                        {
                            if (node.length > 1)
                            {
                                pwdApi = node[1];
                            }
                        }
                        else
                        {
                            if (node[0].toUpperCase().equals("CLIENTID"))
                            {
                                if (node.length > 1)
                                {
                                    clientId = node[1].replace("\n", "").replace("\r", "").replace("null", "");
                                }
                            }
                            else
                            {
                                waras = false;
                                result.setOutError(1);
                                result.setOutMessage("Salah format, seharusnya format adalah -> " + format  + " Request dari server anda adalah [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
                            }
                        }
                    }
                }                   
            }
            else
            {
                waras = false;
                result.setOutError(1);
                result.setOutMessage("Salah format, seharusnya format adalah -> " + format + " Request dari server anda adalah [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
            }
        }
        else if (requestType.equals("GET"))
        {
            waras = false;
            result.setOutError(1);
            result.setOutMessage("Request Method hanya bisa POST dan kirim data dengan format " + format);
        }
       
        try
        {
            if (waras)
            {
                result = updateData(userApi,
                                    pwdApi,
                                    addr,
                                    clientId);
                
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
            result.setOutError(1);
            result.setOutMessage(ex.getMessage());
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ClientInsertTrcId.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(ClientInsertTrcId.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ClientInsertTrcId.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(ClientInsertTrcId.class.getName()).log(Level.SEVERE, null, ex);
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
    
    ResultInsertTrcId updateData(String userApi, String pwdApi, String addr, String clientId)
    {

        ResultInsertTrcId ret = new ResultInsertTrcId();
        
        WSMapper model = new WSMapper();
        
        Map ua = model.goCheckUserAPI(userApi, pwdApi);
        if ((Integer)ua.get("outError") == 0)
        {
             Map wl = model.goCheckIPWhiteList(addr);
             if ((Integer)wl.get("outError") == 0)
             {
                Map result = model.ClientInsertTrcId(clientId);
                if ((Integer)(result.get("outError")) == 0)
                {
                    ret.setTrcId(result.get("trcId").toString());
                    ret.setOutError(0);
                    ret.setOutMessage(result.get("outMessage").toString());
                }
                else
                {
                    ret.setTrcId("");
                    ret.setOutError(1);
                    ret.setOutMessage(result.get("outMessage").toString());
                }
             }
             else
             {
                ret.setTrcId("");
                ret.setOutError((Integer)(wl.get("outError")));
                ret.setOutMessage(wl.get("outMessage").toString());
             }
        }
        else
        {
            ret.setTrcId("");
            ret.setOutError((Integer)(ua.get("outError")));
            ret.setOutMessage(ua.get("outMessage").toString());
        }
        return ret;
        
    }

    private String converStringToUrlDecoded(String data) throws UnsupportedEncodingException 
    {
        String decodedString = java.net.URLDecoder.decode(data, "UTF-8");
        return decodedString;
    }

}
