/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.webservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.my.berkah.webservice.dao.WSMapper;
import id.my.berkah.webservice.model.ListCity;
import id.my.berkah.webservice.model.ReturnListCity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
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
 * @author Administrator
 */
@WebServlet(name = "GetListCity", urlPatterns = {"/GetListCity"})
public class GetListCity extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, GeneralSecurityException 
    {
        String requestType = request.getMethod();
        String command = "";
        ReturnListCity collection = new ReturnListCity();
        String addr = request.getRemoteAddr();
        String cityName = "";
        String userApi = "";
        String pwdApi = "";
        String format =  "userApi=xx&pwdApi=xx&cityName=xxx";
        
        Boolean waras = true;
        
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
                            if (node[0].toUpperCase().equals("CITYNAME"))
                            {
                                if (node.length > 1)
                                {
                                    cityName = node[1].replace("\n", "").replace("\r", "").replace("null", "");
                                }
                            } 
                            else
                            {
                                waras = false;
                                collection.setOutError(1);
                                collection.setOutMessage("Salah format, seharusnya format adalah -> " + format + " Request dari server anda adalah [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
                            }
                        }
                    }
                }                   
            }
            else
            {
                waras = false;
                collection.setOutError(1);
                collection.setOutMessage("Salah format, seharusnya format adalah -> " + format + " Request dari server anda adalah [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
            }
        }
        else if (requestType.equals("GET"))
        {
            waras = false;
            collection.setOutError(1);
            collection.setOutMessage("Request Method hanya bisa POST dengan format " + format);
        }
       
        if (waras)
        {
          collection = getData(userApi, pwdApi, addr, cityName);
          PrintWriter out = response.getWriter();

          try 
          {
              Gson gson = new GsonBuilder().serializeNulls().create();
              String json = gson.toJson(collection);
              response.setContentType("application/json");   
              json = json.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "=").replace("\\u0026", "&");
              out.println(json);
          }
          finally 
          {            
              out.close();
          }
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
       
        try 
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(collection);
            json = json.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "=").replace("\\u0026", "&");
            out.println(json);
        } 
        finally 
        {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try {
            processRequest(request, response);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(GetListCity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try {
            processRequest(request, response);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(GetListCity.class.getName()).log(Level.SEVERE, null, ex);
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
    
    ReturnListCity getData(String userApi, String pwdApi, String addr, String cityName) 
    {
         ReturnListCity ret = new ReturnListCity();
         WSMapper model = new WSMapper();
         
         Map ua = model.goCheckUserAPI(userApi, pwdApi);
         if ((Integer)ua.get("outError") == 0)
         {
             Map wl = model.goCheckIPWhiteList(addr);
             if ((Integer)wl.get("outError") == 0)
             {
                List<ListCity>  list = model.GetListCity(cityName);
                ret.setOutError(0);
                ret.setList(list);
                ret.setOutMessage(wl.get("outMessage").toString());
             }
             else
             {
                ret.setOutError(1);
                ret.setOutMessage(wl.get("outMessage").toString());                 
             }
         }
         else
         {
            ret.setOutError(1);
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
