package tips4scut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import tips4scut.model.Item4Sell;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.ListUtils;

import java.util.List;

/**
 * Created by Administrator on 13-12-25.
 */
@Controller
@RequestMapping(value=("/market"))
public class MarketController {

    //统一接口
    @ResponseBody
    @RequestMapping(value = "message")
    public List<Item4Sell> getMarket(String type){
        Jedis jedis = RedisServ.getJedis();
        String value = "";
        if(type.compareTo("electron") == 0){
            value = jedis.get(RedisKeyUtils.getMarketElectron());
        }else if(type.compareTo("bike") == 0){
            value = jedis.get(RedisKeyUtils.getMarketBike());
        }else if(type.compareTo("book") == 0){
            value = jedis.get(RedisKeyUtils.getMarketBook());
        }else if(type.compareTo("house") ==0){
            value = jedis.get(RedisKeyUtils.getMarketHourse());
        }
        return ListUtils.str2List4ItemSell(value);
    }
}
