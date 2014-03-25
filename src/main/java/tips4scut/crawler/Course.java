package tips4scut.crawler;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haiqi on 14-3-20.
 */
public class Course {

    private static final String baseUrl = "http://www.icourses.cn/dirQueryVCourse.action";
    public static final Map<String, String> HEADER_ACADEMIC_MAP = new HashMap<String, String>(){
        {
            put("Accept", "text/html, application/xhtml+xml, */*");
            put("Accept-Language","en-US,en;q=0.8,zh-Hans-CN;q=0.5,zh-Hans;q=0.3");
            put("Accept-Encoding","gzip, deflate");
            put("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
            put("Connection", "Keep-Alive");
          //  put("Host", "www.icourses.cn");
            put("Cookie","icoursesSymbol=wKg3nFMqiS0cBX/PAzK3Ag==; TJ_PVT=1395302264365; Hm_lvt_787dbcb72bb32d4789a985fd6cd53a46=1395296156,1395301520; TJ_VISIT=1395301520265; Hm_lpvt_787dbcb72bb32d4789a985fd6cd53a46=1395302265; JSESSIONID=F553FB34D65821A5FD676F8928012793.uc21");
        }
    };

    private static final String postData = "videoCourse.title=%s&videoCourse.mainTeacherName=%s&videoCourse.organName=%s";
    private static final String referer = "http://www.icourses.cn/cuoc/";

    public static void main(String [] args){
        searchCourse("数","","");
    }


    public static List<Course> searchCourse(String keyword,String teacher,String university){

        String post ="videoCourse.title=" + URLEncoder.encode(keyword) +
                "&videoCourse.mainTeacherName=" + URLEncoder.encode(teacher) + "&videoCourse.organName=" + URLEncoder.encode(university);

        post = "videoCourse.title=%E6%95%B0&videoCourse.mainTeacherName=&videoCourse.organName=";
        String resStr = getContentWithPostRefer("http://www.icourses.cn/dirQueryVCourse.action",referer,post);
        System.out.print(resStr);
        return null;
    }

    public static String getContentWithPostRefer(String url,String referer,String postData){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
        for (String val : postData.split("&")) {
            String[] arr = val.split("=", 2);
            parms.add(new BasicNameValuePair(arr[0].trim(), arr[1].trim()));
        }
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(parms);
            post.setEntity(entity);
            HttpResponse res = httpClient.execute(post);
            String resStr = EntityUtils.toString(res.getEntity());
            httpClient.getConnectionManager().shutdown();
            return resStr;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return "";
    }


}
