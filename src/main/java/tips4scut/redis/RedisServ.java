package tips4scut.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by Administrator on 13-12-25.
 */
public class RedisServ{

    private static Jedis jedis;

    public static Jedis getJedis(){
        if(jedis == null){
            jedis = new Jedis(RedisConsts.REDIS_HOST_DEV,6379);
        }
        return jedis;
    }

    public void del(byte [] key){
        this.getJedis().del(key);
    }

    public void del(String key){
        this.getJedis().del(key);
    }

    public void set(byte [] key,byte [] value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }

    public void set(String key,String value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }

    public void set(String key,String value){
        this.getJedis().set(key, value);
    }

    public void set(byte [] key,byte [] value){
        this.getJedis().set(key, value);
    }

    public String get(String key){
        String value = this.getJedis().get(key);
        return value;
    }

    public byte[] get(byte [] key){
        return this.getJedis().get(key);
    }

    public Set<String> keys(String pattern){
        return this.getJedis().keys(pattern);
    }

    public boolean exists(String key){
        return this.getJedis().exists(key);
    }

    public String flushDB(){
        return this.getJedis().flushDB();
    }

    public long dbSize(){
        return this.getJedis().dbSize();
    }

    public String ping(){
        return this.getJedis().ping();
    }
}
