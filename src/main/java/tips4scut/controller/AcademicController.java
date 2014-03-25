package tips4scut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.ListUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-12-25.
 */
@Controller
@RequestMapping(value = ("/academic"))
public class AcademicController {

    private static final Map<String, Integer> TYPE_MAP = new HashMap<String, Integer>() {
        {
            put("allCollege", 0);
            put("mediaReport", 1);
            put("new", 2);
            put("notice", 3);
            put("collegeByName", 4);
        }
    };

    @ResponseBody
    @RequestMapping(value = "message")
    public Map<String, Object> academic(String type, int page, String name) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Jedis jedis = RedisServ.getJedis();
        String value;
        switch (TYPE_MAP.get(type)) {
            case 0:
                value = RedisKeyUtils.getAcademicAllCollege(page);
                break;
            case 1:
                value = RedisKeyUtils.getAcademicMediaReport(page);
                break;
            case 2:
                value = RedisKeyUtils.getAcademicNew(page);
                break;
            case 3:
                value = RedisKeyUtils.getAcadeicNotic(page);
                break;
            case 4:
                value = RedisKeyUtils.getAcadeicCollegeByName(name);
                break;
            default:
                value = "";
        }
        if (value != null && !value.trim().isEmpty()) {
            returnMap.put("success", true);
            returnMap.put("message", ListUtils.str2List4Inform(jedis.get(value)));
        } else {
            returnMap.put("success", false);
        }
        return returnMap;
    }
}
