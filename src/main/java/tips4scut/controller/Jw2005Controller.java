package tips4scut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tips4scut.crawler.ScutJW2005;
import tips4scut.model.Jw2005Schedule;
import tips4scut.model.Jw2005Score;
import tips4scut.service.Jw2005Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-25.
 */
@Controller
@RequestMapping(value = "jw2005")
public class Jw2005Controller {
    @RequestMapping(value = "init")
    @ResponseBody
    public Map<String, Object> init(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            ScutJW2005 jw2005 = Jw2005Service.initJw2005();
            File file = new File("jw2005." + request.getSession().getId());
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(jw2005);
            out.close();
            resMap.put("verCodeUrl", jw2005.getVeriCodeUrl());
            resMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("jw2005login", "false");
            resMap.put("success", false);
        }
        return resMap;
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public Map<String, Object> loginJw2005(HttpServletRequest request, String id, String password, String verCode) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            File file = new File("jw2005." + request.getSession().getId());
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
            ScutJW2005 jw2005 = (ScutJW2005) oin.readObject();
            boolean login = jw2005.loginJw2005(id, password, verCode);
            if (login) {
                request.getSession().setAttribute("jw2005login", "true");
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(jw2005);
                out.close();
                returnMap.put("success", true);
                returnMap.put("login", true);
            }
        } catch (Exception e) {
            returnMap.put("success", false);
            returnMap.put("login", false);
            e.printStackTrace();
        }
        return returnMap;
    }

    @RequestMapping(value = "schedule")
    @ResponseBody
    public Map<String, Object> schedule(HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (request.getSession().getAttribute("jw2005login").toString().compareTo("true") == 0) {
            try {
                File file = new File("jw2005." + request.getSession().getId());
                ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
                ScutJW2005 jw2005 = (ScutJW2005) oin.readObject();
                Jw2005Schedule schedule = jw2005.getUsrSchedule();
                oin.close();
                returnMap.put("success", true);
                returnMap.put("message", schedule.getSchedule());
                return returnMap;
            } catch (Exception e) {
                returnMap.put("success", false);
                e.printStackTrace();
            }
        }
        returnMap.put("success", false);
        returnMap.put("login",false);
        return returnMap;
    }

    @RequestMapping(value = "score")
    @ResponseBody
    public Map<String, Object> score(HttpServletRequest request, String type, int year, int term) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String val = request.getSession().getAttribute("jw2005login").toString();
        System.out.println(val);
        if (request.getSession().getAttribute("jw2005login").toString().compareTo("true") == 0) {
            try {
                File file = new File("jw2005." + request.getSession().getId());
                ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
                ScutJW2005 jw2005 = (ScutJW2005) oin.readObject();
                List<Jw2005Score> scores;
                if (type.compareTo("all") == 0) {
                    scores = jw2005.getUsrTranscriptByYear(year);
                } else if (type.compareTo("year") == 0) {
                    scores = jw2005.getUsrTranscriptByYear(year);
                } else if (type.compareTo("term") == 0) {
                    scores = jw2005.getUsrTranscriptByTerm(year, term);
                } else {
                    returnMap.put("success", false);
                    oin.close();
                    return returnMap;
                }
                oin.close();
                returnMap.put("message", scores);
                return returnMap;
            } catch (Exception e) {
                e.printStackTrace();
                returnMap.put("success", false);
                return returnMap;
            }
        } else {
            returnMap.put("login", false);
            returnMap.put("success", false);
            return returnMap;
        }
    }
}
