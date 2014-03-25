package tips4scut.service;

import tips4scut.crawler.ScutJW2005;

/**
 * Created by Administrator on 13-12-26.
 */
public class Jw2005Service {

    //点击Jw2005加载时，调用一次，Jw2005调用主界面，得到jw2005状态号，验证码 [Tips用户状态码 - userScutJw2005系列化]
    public static ScutJW2005 initJw2005(){
        ScutJW2005 jw2005 = new ScutJW2005();
        return jw2005;
    }

    //由 Tips用户状态码 读取 Redis对应的 userScutJw2005系列化串，反系列化，进行登录操作
    public static boolean loginJw2005(String studentId,String password,String vericode,ScutJW2005 jw2005){
        boolean login = jw2005.loginJw2005(studentId,password,vericode);
        return login;
    }

    //由 Tips用户状态码 读取 Redis对应的 userScutJw2005系列化串，反系列化，判断登录状态，并进行一系列查询
    public static void handerJw2005(){

    }
}
