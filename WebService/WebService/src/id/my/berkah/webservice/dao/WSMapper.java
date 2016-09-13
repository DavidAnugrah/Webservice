/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.webservice.dao;

import id.my.berkah.webservice.implement.WSImpl;
import id.my.berkah.webservice.model.ListCity;
import id.my.berkah.webservice.model.ListMSISDN;
import id.my.berkah.webservice.model.ListPackage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dadan
 */
public class WSMapper {
 
    public static int logNumb = 0;
    
    public Map insertCustomer(
            String  trcId, 
            String  firstName, 
            String  lastName,
            String  gender,
            String  birthPlace,
            String  birthDate,
            String  religion,
            String  phone,
            String  address,
            String  mailAddress,
            String  country,
            String  province,
            String  city,
            String  kodepos,
            String  typeId,
            String  numberId,
            String  expiredDate,
            String  deliverType,
            String  deliverAddress) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("trcId", trcId);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("gender", gender);
        map.put("birthPlace", birthPlace);
        map.put("birthDate", birthDate);
        map.put("religion", religion);
        map.put("phone", phone);
        map.put("address", address);
        map.put("mailAddress", mailAddress);
        map.put("country", country);
        map.put("province", province);
        map.put("city", city);
        map.put("kodepos", kodepos);
        map.put("typeId", typeId);
        map.put("numberId", numberId);
        map.put("expiredDate", expiredDate);
        map.put("deliverType", deliverType);
        map.put("deliverAddress", deliverAddress);
        map.put("outError", 0);
        map.put("outMessage","");
        
        //showLog(map, "insertCustomer");
        new WSImpl().insertCustomer(map);
        //showLog(map, "insertCustomer");
        return map;
    }

    public List<ListMSISDN> GetListMSISDN(String clientId, String packageId, String cityId, String msisdn, int pageNumber, int pageSize) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", clientId);
        map.put("packageId", packageId);
        map.put("cityId", cityId);
        map.put("msisdn", msisdn);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        //showLog(map, "GetListMSISDN");
        List<ListMSISDN> list = new WSImpl().GetListMSISDN(map);
        return list;
    }

    public List<Integer> GetCountMSISDN(String clientId, String packageId, String cityId, String msisdn) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", clientId);
        map.put("packageId", packageId);
        map.put("cityId", cityId);
        map.put("msisdn", msisdn);
        //showLog(map, "GetCountMSISDN");
        List<Integer> list = new WSImpl().GetCountMSISDN(map);
        return list;
    }

    public Map updateCustomer(String trcId, String packageId, String msisdn) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("trcId", trcId);
        map.put("packageId", packageId);
        map.put("msisdn", msisdn);
        map.put("reserveNo", "");
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "updateCustomer");
        new WSImpl().updateCustomer(map);
        //showLog(map, "updateCustomer");
        return map;
    }

    public Map updatePayment(String gateWay, String paymentCode) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("reserveNo", gateWay);
        map.put("paymentCode", paymentCode);
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "updatePayment");
        new WSImpl().updatePayment(map);
        //showLog(map, "updatePayment");
        return map;
    }

    public List<ListPackage> GetListPackage(String code) 
    {
        List <ListPackage> list = new WSImpl().GetListPackage(code);
        return list;
    }

    public List<ListCity> GetListCity(String code) 
    {
        List <ListCity> list = new WSImpl().GetListCity(code);
        return list;
    }

   public Map goCheckUserAPI(String userApi, String password) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("userApi", userApi);
        map.put("password", password);
        map.put("outError", 0);
        map.put("outMessage","");
        
        //showLog(map, "goCheckUserAPI");
        new WSImpl().goCheckUserAPI(map);
        //showLog(map, "goCheckUserAPI");
        return map;
    }

   public Map goCheckIPWhiteList(String ip) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("ip", ip);
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "goCheckIPWhiteList");
        new WSImpl().goCheckIPWhiteList(map);
        //showLog(map, "goCheckIPWhiteList");
        return map;
    }
   
    public Map ClientAttribute(String reserveNo, String attrCode, String attrValue) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("reserveNo", reserveNo);
        map.put("attrCode", attrCode);
        map.put("attrValue", attrValue);
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "ClientAttribute");
        new WSImpl().ClientAttribute(map);
        //showLog(map, "ClientAttribute");
        return map;
    }

    public Map ClientInsertTrcId(String clientId) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", clientId);
        map.put("trcId", 0);
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "ClientInsertTrcId");
        new WSImpl().ClientInsertTrcId(map);
        //showLog(map, "ClientInsertTrcId");
        return map;
    }

    public Map ClientGetDataPersoATM(String buId, String serialNo, String reserveNo, String userId, String msisdn) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("buId", buId);
        map.put("msisdn", msisdn);
        map.put("serialNo", serialNo);
        map.put("reserveNo", reserveNo);
        map.put("userId", userId);
        map.put("persoId", 0);
        map.put("reserveId", 0);
        map.put("imsi", "0");
        map.put("iccid", "0");
        map.put("pin1", "0");
        map.put("pin2", "0");
        map.put("puk1", "0");
        map.put("puk2", "0");
        map.put("aki", "0");
        map.put("smpp", "0");
        map.put("simType", "0");
        map.put("dllName", "0");
        map.put("profile", "0");
        map.put("authKey", "0");
        map.put("version", "0");
        map.put("subscriberType", "0");
        map.put("status", "0");
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "ClientGetDataPersoATM");
        new WSImpl().ClientGetDataPersoATM(map);
        //showLog(map, "ClientGetDataPersoATM");
        return map;
    }

    public Map PersoUpdateStatus(String module, String persoId, String status, String userId) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("module", module);
        map.put("persoId", persoId);
        map.put("status", status);
        map.put("userId", userId);
        map.put("isAutoProv", 0);
        map.put("persoType", "");
        map.put("simType", "");
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "PersoUpdateStatus");
        new WSImpl().PersoUpdateStatus(map);
        //showLog(map, "PersoUpdateStatus");
        return map;
    }
    
    public Map goOPKeyAndTKey() 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("tKey", "");
        map.put("opKey", "");
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "goOPKeyAndTKey");
        new WSImpl().goOPKeyAndTKey(map);
        //showLog(map, "goOPKeyAndTKey");
        return map;
    }

    public Map goAutoPOSbyATM(String userId, String responsibilityId, String reserveNo, String serialNo) 
    {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("responsibilityId", responsibilityId);
        map.put("reserveNo", reserveNo);
        map.put("serialNo", serialNo);
        map.put("outError", 0);
        map.put("outMessage","");
        //showLog(map, "goAutoPOSbyATM");
        new WSImpl().goAutoPOSbyATM(map);
        //showLog(map, "goAutoPOSbyATM");
        return map;
    }

    public void showLog(Map<String, Object> map, String title) 
    {
        System.out.println(++logNumb + ". Title " + title);
        for (Map.Entry<String, Object> entry : map.entrySet()) 
        {
            
        String string = entry.getKey();
            Object object = entry.getValue();
            System.out.println(string + ": " + object);
        }
        System.out.println();
    }
    
    public Map goSendSMS(String NAME, String ADDRESS,String CITY,String BITRHDATE,String BIRTHPLACE,String MSISDNFROM,String MSISDNCUST,String IDTYPE,String IDNO,String OUTLET)
    {
        Map<String,Object> map = new HashMap<>();
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
        new WSImpl().goSendSMS(map);
        return map;
        
    }     
            
}
