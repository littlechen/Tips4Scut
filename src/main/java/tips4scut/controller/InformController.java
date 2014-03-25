package tips4scut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import tips4scut.model.Inform;
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
@RequestMapping("/inform")
public class InformController {

    private static final int FINAL_KEY = 8;
    private static final Map<String,Integer> typeMap = new HashMap<String,Integer>()
    {
        {
            put("partTimeJob",0);
            put("hospital",1);
            put("youth",2);
            put("logistics",3);
            put("campusECard",4);
            put("library",5);
            put("globalPartnerShips",6);
            put("campusNetwork",7);
            put("campusTransferMachine",FINAL_KEY);
        }

    };

    @ResponseBody
    @RequestMapping (value = "message")
    public Map<String,Object> inform(String type,int page){
        Jedis jedis = RedisServ.getJedis();
        Map<String,Object> returnMap = new HashMap<String, Object>();
        String value = "";
        Integer typeCode = typeMap.get(type);
        if(typeCode != null){
            switch (typeCode){
                case 0 :
                    value = RedisKeyUtils.getInformParttimejob();
                    break;
                case 1:
                    value = RedisKeyUtils.getinformHospital();
                    break;
                case 2:
                    value =  RedisKeyUtils.getInformYourth(page);
                    break;
                case 3:
                    value =  RedisKeyUtils.getInformLogistics(page);
                    break;
                case 4:
                    value =  RedisKeyUtils.getCampusCard(page);
                    break;
                case 5:
                    value = RedisKeyUtils.getInformLibrary(page);
                    break;
                case 6:
                    value =  RedisKeyUtils.getInformGlobalPartnerships(page);
                    break;
                case 7:
                    value =  RedisKeyUtils.getInformCampusNetwork(page);
                    break;
                case FINAL_KEY:
                    value = RedisKeyUtils.getCampusTransferMarchine();
                    break;
            }
        }
        value = jedis.get(value);
        if(value != null && !value.trim().isEmpty()){
            returnMap.put("success",true);
            if(typeCode != FINAL_KEY){
                returnMap.put("message",ListUtils.str2List4Inform(value));
            }else{
                returnMap.put("message",value);
            }
        }else{
            returnMap.put("message",false);
        }
        return returnMap;
    }

//    @ResponseBody
//    @RequestMapping(value = "campusTransferMachine")
//    public String campusTransferMachine(){
//        Jedis jedis = RedisServ.getJedis();
//        String value = jedis.get(RedisKeyUtils.getCampusTransferMarchine());
//        return value;
//    }
}
