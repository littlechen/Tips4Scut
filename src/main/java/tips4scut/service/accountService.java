package tips4scut.service;

import redis.clients.jedis.Jedis;
import tips4scut.model.Account;
import tips4scut.model.User;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haiqi on 14-3-20.
 */
public class accountService {

    public static User checkAccountByEmail(String email,String passwd){
        Jedis jedis = RedisServ.getJedis();
        Map<String,String> map = jedis.hgetAll(RedisKeyUtils.getUserID(email));
        if(map!= null){
            String pwd = map.get("password");
            if(pwd != null && pwd.compareTo(MD5.getMD5Code(passwd) ) == 0){
                return new User(email,map.get("keyword"),map.get("interest"));
            }
        }
        return null;
    }

    public static boolean checkAccount(Account account)
    {
        String email = account.getEmail();
        Jedis jedis = RedisServ.getJedis();
        if(jedis.get(RedisKeyUtils.getUserID(email)) != null ){
            return false;
        }
        return true;
    }

    public static User initAccount(Account account){
        String email = account.getEmail();
        Map<String,String> map = new HashMap<String, String>();
        map.put("password", MD5.getMD5Code(account.getPassword()));
        map.put("interest",account.getInterest());
        map.put("keyword",account.getKeyword());
        map.put("createTime",account.getCreateTime().toString());

        Jedis jedis = RedisServ.getJedis();
        jedis.hmset(RedisKeyUtils.getUserID(email), map);
        return new User(account.getEmail(),account.getKeyword(),account.getInterest());
    }
}
