package tips4scut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import tips4scut.model.Employ;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-25.
 */
@Controller
@RequestMapping(value = ("/employ"))
public class EmployController {

    private static final Map<String, Integer> TYPE_MAP = new HashMap<String, Integer>() {
        {
            put("employ", 0);
            put("specialRecent", 1);
            put("specialExtra", 2);
            put("specialAll", 3);
        }
    };

    @RequestMapping(value = "message")
    @ResponseBody
    public Map<String, Object> employ(String type, int page) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Jedis jedis = RedisServ.getJedis();
        String value = "";
        switch (TYPE_MAP.get(type)) {
            case 0:
                value = RedisKeyUtils.getEmployList(page, 15);
                break;
            case 1:
                value = RedisKeyUtils.getEmploySpecialRecent(page, 15);
                break;
            case 2:
                value = RedisKeyUtils.getEmploySpecialExitra(page, 15);
                break;
            case 3:
                value = RedisKeyUtils.getEmploySpecialAll(page, 15);
        }
        value = jedis.get(value);
        if (value != null && !value.trim().isEmpty()) {
            returnMap.put("success", true);
            returnMap.put("message", ListUtils.str2List4Employ(value));
        } else {
            returnMap.put("success", false);
        }
        return returnMap;
    }
}
