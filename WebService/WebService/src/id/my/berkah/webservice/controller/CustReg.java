/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.webservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import id.my.berkah.rps.implement.ProcedureRPSImpl;

import id.my.berkah.rps.model.Reply;
import id.my.berkah.webservice.implement.WSImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author azec
 */
@WebServlet(name = "CustReg", urlPatterns = {"/CustReg"})
public class CustReg extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = "";
        String status = "";
        String msisdn = "";
        String userapi = "";
        String password = "";
        String trcid = "";
        String imei = "";
        String name = "";
        String address = "";
        String city = "";
        String bithrdate = "";
        String birthplace = "";
        String msisdnfrom = "";
        String msisdncust = "";
        String idType = "";
        String idNo = "";
        String outlet = "";
        String format = "USERAPI=XXX"
                + "&PWDAPI=XXX"
                + "&NAME=XX"
                + "&ADDRESS=XX"
                + "&CITY=XX"
                + "&BITRHDATE=XXX"
                + "&BIRTHPLACE=XXX"
                + "&MSISDNFROM=XXX"
                + "&MSISDNCUST=XXX"
                + "&IDTYPE=XX"
                + "&IDNO=XX"
                + "&OUTLET=XXX";

        String requestType = request.getMethod();
        String addr = request.getRemoteAddr();

        Boolean waras = true;
        Reply result = new Reply();

        if (requestType.equals("POST")) {
            command = getPostData(request).trim().replace("\n", "").replace("\r", "").replace("null", "");

            String[] component = command.split("&");
            if (component.length == 12) {
                for (int i = 0; i < component.length; i++) {

                    component[i] = component[i].trim();
                    String[] node = component[i].split("=");

                    if (node[0].toUpperCase().equals("USERAPI")) {
                        userapi = node[1];
                    } else {
                        if (node[0].toUpperCase().equals("PWDAPI")) {
                            password = node[1];
                        } else {
                            if (node[0].toUpperCase().equals("NAME")) {
                                name = node[1];
                            } else {
                                if (node[0].toUpperCase().equals("ADDRESS")) {
                                    address = node[1];
//                                    .replace("\n",  "").replace("\r", "").replace("null", "")
                                } else {
                                    if (node[0].toUpperCase().equals("CITY")) {
                                        city = node[1];
                                    } else {
                                        if (node[0].toUpperCase().equals("BITRHDATE")) {
                                            bithrdate = node[1];
                                        } else {
                                            if (node[0].toUpperCase().equals("BIRTHPLACE")) {
                                                birthplace = node[1];
                                            } else {
                                                if (node[0].toUpperCase().equals("MSISDNFROM")) {
                                                    msisdnfrom = node[1];
                                                } else {
                                                    if (node[0].toUpperCase().equals("MSISDNCUST")) {
                                                        msisdncust = node[1];
                                                    } else {
                                                        if (node[0].toUpperCase().equals("IDTYPE")) {
                                                            idType = node[1];
                                                        } else {
                                                            if (node[0].toUpperCase().equals("IDNO")) {
                                                                idNo = node[1];
                                                            } else {
                                                                if (node[0].toUpperCase().equals("OUTLET")) {
                                                                    outlet = node[1];
                                                                } else {
                                                                    waras = false;
                                                                    result.setStatus("1");
                                                                    result.setMessage("Salah format,  seharusnya format adalah -> " + format + " Request dari server anda adalah  [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            } else {
                waras = false;
                result.setStatus("1");
                result.setMessage("Salah format, seharusnya format adalah  -> " + format + " Request dari server anda adalah [" + command.replace("\n", "").replace("\r", "").replace("null", "") + "]");
            }
        } else if (requestType.equals("GET")) {
            waras = false;
            result.setStatus("1");
            result.setMessage("Request Method hanya bisa POST dan kirim  data dengan format " + format);
        }

        try {
            if (waras) {
                //doUpdateProvesioning(String addr, String userapi, String  password, String persoid, String status)
                result = doHit(addr, userapi, password, name, address, city, bithrdate, birthplace, msisdnfrom, msisdncust, idType, idNo, outlet);
                PrintWriter out = response.getWriter();
                try {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String json = gson.toJson(result);
                    response.setContentType("text/json;charset=UTF-8");
                    json = json.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "=").replace("\\u0026", "&");
                    out.println(json);
                } finally {
                    out.close();
                }

            }
        } catch (Exception ex) {
            result.setStatus("1");
            result.setMessage(ex.getMessage());
        }

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(result);
            json = json.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "=").replace("\\u0026", "&");
            out.println(json);
        } finally {
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    String getPostData(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            reader.mark(10000);

            String line;
            do {
                line = reader.readLine();
                sb.append(line).append("\n");
            } while (line != null);

            reader.reset();
            // do NOT close the reader here, or you won't be able to get  the post data twice
        } catch (IOException e) {
            //logger.warn("getPostData couldn't.. get the post data", e);   // This has happened if the request's reader is closed
        }

        return sb.toString();
    }

    Reply doHit(String addr, String userApi, String password, String NAME, String ADDRESS, String CITY, String BITRHDATE, String BIRTHPLACE, String MSISDNFROM, String MSISDNCUST, String IDTYPE, String IDNO, String OUTLET) {
//        ProcedureRPSImpl rps = new ProcedureRPSImpl();
        WSImpl dpa = new WSImpl();
        Reply result = new Reply();

//        ParamLogProvisioningEAI param = new ParamLogProvisioningEAI();
//        //Log Send
////        param.setPersoId(msisdn);
//        param.setCategory("WebOptinRequestToParadise");
        String input = "USERAPI=" + userApi + "&PWDAPI=" + password + "&NAME=" + NAME + "&ADDRESS=" + ADDRESS + "&CITY=" + CITY + "&BIRTHDATE=" + BITRHDATE + "&BIRTHPLACE=" + BIRTHPLACE + "&MSISDNFROM=" + MSISDNFROM + "&MSISDNCUST=" + MSISDNCUST + "&IDTYPE=" + IDTYPE + "&IDNO=" + IDNO + "&OUTLET=" + OUTLET;
//        System.out.println(input);
//        param.setMessage(input);
////        rps.LogProvisioningEAI(param);

        Map cekUser = new HashMap<>();
        cekUser.put("userApi", userApi);
        cekUser.put("password", password);
        cekUser.put("outError", 0);
        cekUser.put("outMessage", "");
        dpa.goCheckUserAPI(cekUser);

//        param.setPersoId(msisdn);
//        param.setCategory("WebOptinRequestToParadise");
//        param.setMessage(cekUser.get("outMessage").toString());
//        rps.LogProvisioningEAI(param);
        if (cekUser.get("outError").toString().equals("0")) {

            Map cekIP = new HashMap<>();
            cekIP.put("ip", addr);
            cekIP.put("outError", 0);
            cekIP.put("outMessage", "");
            dpa.goCheckIPWhiteList(cekIP);

//            param.setPersoId(msisdn);
//            param.setCategory("WebOptinRequestToParadise");
//            param.setMessage(cekIP.get("outMessage").toString());
//            rps.LogProvisioningEAI(param);
            if (cekIP.get("outError").toString().equals("0")) {

                Map<String, Object> map = new HashMap<>();
                map.put("NAME", NAME);
                map.put("ADDRESS", ADDRESS);
                map.put("CITY", CITY);
                map.put("BITRHDATE", BITRHDATE);
                map.put("BIRTHPLACE", BIRTHPLACE);
                map.put("MSISDNFROM", MSISDNFROM);
                map.put("MSISDNCUST", MSISDNCUST);
                map.put("IDTYPE", IDTYPE);
                map.put("IDNO", IDNO);
                map.put("OUTLET", OUTLET);
                dpa.goSendSMS(map);

                result.setStatus(map.get("outError").toString());
                result.setMessage(map.get("outMessage").toString());

            } else {
                result.setStatus(cekIP.get("outError").toString());
                result.setMessage(cekIP.get("outMessage").toString());
            }
        } else {
            result.setStatus(cekUser.get("outError").toString());
            result.setMessage(cekUser.get("outMessage").toString());
        }

        return result;
    }

}
