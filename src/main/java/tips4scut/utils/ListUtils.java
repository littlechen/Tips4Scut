package tips4scut.utils;

import tips4scut.model.EcardConsume;
import tips4scut.model.Employ;
import tips4scut.model.Inform;
import tips4scut.model.Item4Sell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-25.
 */
public class ListUtils {

    public static List<Employ> str2List4Employ(String redisStr){
        List<Employ> res = new ArrayList<Employ>();
        if(redisStr != null){
            for(String val : redisStr.split(StringUtil.STR_DELIMIT_1ST)){
                res.add(new Employ(val));
            }
        }
        return  res;
    }

    public static List<Inform> str2List4Inform(String redisStr){
        List<Inform> res = new ArrayList<Inform>();
        if(redisStr != null){
            for(String val : redisStr.split(StringUtil.STR_DELIMIT_1ST)){
                res.add(new Inform(val));
            }
        }
        return  res;
    }

    public static List<EcardConsume> str2List4EcardConsume(String redisStr){
        List<EcardConsume> res = new ArrayList<EcardConsume>();
        if(redisStr != null){
            for(String val : redisStr.split(StringUtil.STR_DELIMIT_1ST)){
                res.add(new EcardConsume(val,true));
            }
        }
        return  res;
    }

    public static List<Item4Sell> str2List4ItemSell(String redisStr){
        List<Item4Sell> res = new ArrayList<Item4Sell>();
        if(redisStr != null){
            for(String val : redisStr.split(StringUtil.STR_DELIMIT_1ST)){
                res.add(new Item4Sell(val));
            }
        }
        return  res;
    }
}
