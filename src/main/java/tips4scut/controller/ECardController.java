package tips4scut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import tips4scut.crawler.ScutECard;
import tips4scut.model.EcardConsume;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.ListUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-25.
 */
@Controller
@RequestMapping(value = ("/eCard"))
public class ECardController {
    @ResponseBody
    @RequestMapping(value = "consume")
    public Map<String,Object> eCardConsume(HttpServletRequest req, String id, String password, String start, String end) {
        Map<String,Object> returnMap = new HashMap<String, Object>();
        req.getSession().setAttribute("id", id);
        returnMap.put("success",true);
        returnMap.put("message",ScutECard.crawlEcard4Student(id, password, start, end));
        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = "nextPage")
    public Map<String,Object> eCardConsume(String id, int page) {
        Jedis jedis = RedisServ.getJedis();
        Map<String,Object> returnMap = new HashMap<String, Object>();
        String value = jedis.get(RedisKeyUtils.getEcardConsume(id, page, false));
        if(value != null && !value.trim().isEmpty()){
            returnMap.put("success",true);
            returnMap.put("message",value);
        }else{
            returnMap.put("success",false);
        }
        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = "consumeToday")
    public Map<String,Object> eCardConsumeToday(String id, String password) {
        Map<String,Object> returnMap = new HashMap<String, Object>();
        returnMap.put("success",true);
        returnMap.put("message",ScutECard.crawlEcardConsumeToday(id, password));
        return returnMap;
    }
}
