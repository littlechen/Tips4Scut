package tips4scut.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tips4scut.model.Account;
import tips4scut.model.User;
import tips4scut.service.accountService;
import tips4scut.utils.Consts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by haiqi on 14-3-20.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    protected static Log logger = LogFactory.getLog(AccountController.class);

    @RequestMapping(value = "/ajax/login", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> login(@RequestBody Account account) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        String email = account.getEmail();
        String pwd = account.getPassword();

        if (email.isEmpty() || pwd.isEmpty()) {
            returnMap.put("message", Consts.LOGIN_RETURN_INFOVAILD);
            returnMap.put("success", false);
            return returnMap;
        }

        try {
            User user = accountService.checkAccountByEmail(email, pwd);
            if (user != null) {
                returnMap.put("message", Consts.LOGIN_RETURN_ACCOUNT_VAILD);
                returnMap.put("success", false);
                return returnMap;
            }
            session.setAttribute(Consts.CURRENT_USER, user);

        } catch (Exception err) {
            returnMap.put("message", Consts.LOGIN_RETURN_FAIL);
            returnMap.put("success", false);
            return returnMap;
        }

        returnMap.put("message", Consts.LOGIN_RETURN_SUCCESS);
        returnMap.put("success", true);
        return returnMap;
    }

    @RequestMapping("/exit")
    public String exit() {
        session.removeAttribute(Consts.CURRENT_USER);
        return "index";
    }

    @RequestMapping(value = "/ajax/checkAccount", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkAccount(String email) {
        Account account = new Account();
        account.setEmail(email);
        return !accountService.checkAccount(account);
    }


    @RequestMapping(value = "/ajax/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> register(@RequestBody Account account) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        String email = account.getEmail();
        String pwd = account.getPassword();

        if (email.isEmpty() || pwd.isEmpty()) {
            returnMap.put("message", Consts.LOGIN_RETURN_INFOVAILD);
            returnMap.put("success", false);
            return returnMap;
        }

        if (!(account.getCheckcode().equalsIgnoreCase(session.getAttribute(Consts.CURRENT_CHECK_CODE).toString()))) {
            returnMap.put("message", Consts.REGISTER_USER_CHECKCODE);
            returnMap.put("success", false);
            return returnMap;
        }

        try {
            if (accountService.checkAccount(account)) {
                returnMap.put("message", Consts.LOGIN_RETURN_REGISTERD);
                returnMap.put("success", false);
                return returnMap;
            }
        } catch (Exception err) {
            returnMap.put("message", Consts.LOGIN_RETURN_REGISTERD);
            returnMap.put("success", false);
            return returnMap;
        }

        try {
            account.setId(UUID.randomUUID().toString());
            account.setCreateTime(new Date());

            User user = accountService.initAccount(account);
            session.setAttribute(Consts.CURRENT_USER, user);

            returnMap.put("message", Consts.LOGIN_RETURN_SUCCESS);
            returnMap.put("success", true);
        } catch (Exception err) {
            returnMap.put("message", Consts.LOGIN_RETURN_FAIL);
            returnMap.put("success", false);
            return returnMap;
        }
        return returnMap;
    }


}
