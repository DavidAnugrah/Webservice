/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.webservice.implement;

import id.my.berkah.util.dao.MyBatisConnectionFactory;
import id.my.berkah.webservice.model.ListCity;
import id.my.berkah.webservice.model.ListMSISDN;
import id.my.berkah.webservice.model.ListPackage;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author LENOVO
 */
public class WSImpl {

    private final SqlSessionFactory sqlSessionFactory;

    public WSImpl()
    {
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
    }
    
    public Map insertCustomer(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.insertCustomer", map);
            return map;
        }
        finally 
        {
             session.close();
        }	
    }

    public Map updateCustomer(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.updateCustomer", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }

    public Map updatePayment(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.updatePayment", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }

    public List<ListPackage> GetListPackage(String code) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            List<ListPackage> list = session.selectList("id.my.berkah.webservice.dao.WSMapper.GetListPackage", code);
            return list;
        } 
        finally 
        {
             session.close();
        }
    }

    public List<ListCity> GetListCity(String code) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            List<ListCity> list = session.selectList("id.my.berkah.webservice.dao.WSMapper.GetListCity", code);
            return list;
        } 
        finally 
        {
             session.close();
        }
    }

    public List<ListMSISDN> GetListMSISDN(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            List<ListMSISDN> list = session.selectList("id.my.berkah.webservice.dao.WSMapper.GetListMSISDN", map);

            return list;
        }
        finally 
        {
             session.close();
        }	
    }

    public List<Integer> GetCountMSISDN(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            List<Integer> list = session.selectList("id.my.berkah.webservice.dao.WSMapper.GetCountMSISDN", map);
            return list;
        } 
        finally 
        {
            session.close();
        }
    }
    
    public Map goCheckUserAPI(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.goCheckUserAPI", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    

    public Map goCheckIPWhiteList(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.goCheckIPWhiteList", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    

    public Map ClientAttribute(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.ClientAttribute", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    

    public Map ClientInsertTrcId(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.ClientInsertTrcId", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    

    public Map ClientGetDataPersoATM(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.ClientGetDataPersoATM", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    

    public Map PersoUpdateStatus(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.PersoUpdateStatus", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    
    
    public Map goOPKeyAndTKey(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.goOPKeyAndTKey", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    

    public Map goAutoPOSbyATM(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.goAutoPOSbyATM", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    
    
    public Map goSendSMS(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.goSendSMS", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    
    public Map CekIP(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("id.my.berkah.webservice.dao.WSMapper.CekIP", map);

            return map;
        }
        finally 
        {
             session.close();
        }	
    }    
}
