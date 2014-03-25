package tips4scut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import tips4scut.crawler.ScutLibrary;
import tips4scut.model.Book4Info;
import tips4scut.model.Book4Search;
import tips4scut.quartz.RunTask;
import tips4scut.redis.RedisServ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-25.
 */
@Controller
@RequestMapping("/library")
public class LibraryController {
    @ResponseBody
    @RequestMapping(value = "user")
    public Map<String,Object> getUserINfo(String id,String password){
        return ScutLibrary.crawlStudentInfo(id,password);
    }

    @ResponseBody
    @RequestMapping(value = "searchBook")
    public List<Book4Search> getUserINfo(String title,int page){
        return ScutLibrary.SearchBookByTitle(title,page,10);
    }

    @ResponseBody
    @RequestMapping(value = "bookInfo")
    public Book4Info getUserINfo(String id){
        return ScutLibrary.getBookDetailById(id);
    }
}
