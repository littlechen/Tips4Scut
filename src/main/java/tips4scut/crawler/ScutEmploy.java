package tips4scut.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import tips4scut.model.Employ;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.StringUtil;


public class ScutEmploy {
	public static final String SCUT_EMPLOY_SPECIAL_EXTRA_URL ="http://employ.scut.edu.cn:8880/zpzc/extramural.jsp";
	public static final String SCUT_INTERSHIP_URL = "http://employ.scut.edu.cn:8880/shixi/index.jsp";
	public static final String SCUT_EMPLOY_URL =  "http://employ.scut.edu.cn:8880/information/index.jsp";
	public static final String SCUT_EMPLOY_SPECIAL_ALL_URL = "http://employ.scut.edu.cn:8880/zpzc/future.jsp";
	public static final String SCUT_EMPLOY_SPECIAL_RECENT_URL = "http://employ.scut.edu.cn:8880/zpzc/all.jsp";
	
//	public static void main(String [] args){
//		System.out.println(getIntershipList(1, 5));
//		System.out.println(getIntershipList(2,15));
//		System.out.println(getEmployList(3,15));
//		System.out.println(getSpecialRecent(2,15));
//		System.out.println(getSpecialExitra(2, 15));
//	}

    public static void crawlEmploy(){
        Jedis jedis = RedisServ.getJedis();
        for(int i =1 ;i<=3;i++){
            jedis.set(RedisKeyUtils.getEmployInterships(i,15),List2ValStr(getIntershipList(i, 15)));
        }
        for(int i =1 ;i<=3;i++){
            jedis.set(RedisKeyUtils.getEmployList(i, 15),List2ValStr(getEmployList(i, 15)));
        }
        for(int i =1 ;i<=3;i++){
            jedis.set(RedisKeyUtils.getEmploySpecialExitra(i, 15),List2ValStr(getSpecialExitra(i,15)));
        }
        for(int i =1 ;i<=3;i++){
            jedis.set(RedisKeyUtils.getEmploySpecialAll(i, 15),List2ValStr(getSpecialAll(i, 15)));
        }
        for(int i =1 ;i<=3;i++){
            jedis.set(RedisKeyUtils.getEmploySpecialRecent(i, 15),List2ValStr(getSpecialRecent(i, 15)));
        }
    }

    public static String List2ValStr(List<Employ> list){
        StringBuilder sb = new StringBuilder();
        for(Employ val : list){
            sb.append(val.toString()).append(StringUtil.DELIMIT_1ST);
        }
        String res = sb.toString();
        System.out.println(res);
        return res.contains("|") ? res.substring(0,res.length() - 1) : "";
    }
	
	/**
	 * 实习公告信息
	 * @param page
	 * @param pageCount
	 * @return
	 */
	public static List<Employ> getIntershipList(int page,int pageCount){
		List<Employ> list = new ArrayList<Employ>();
		String postData = String.format("pageNum=%d&countPerPage=%d", page, pageCount);
		String content = getContentWithPostRefer(SCUT_INTERSHIP_URL, SCUT_INTERSHIP_URL, postData);
		Elements td = Jsoup.parse(content).select("a[class=ablue02]");
		for(Element job : td){
            list.add(new Employ(job.text().trim(),
                    String.format("http://employ.scut.edu.cn:8880%s",job.attr("href").substring(2) ) ) ) ;
		}
		return list;
	}
	
	/**
	 * 招聘公告信息
	 * @param page
	 * @param pageCount
	 * @return
	 */
	public static List<Employ> getEmployList(int page,int pageCount){
		List<Employ> list = new ArrayList<Employ>();
		String postData = String.format("pageNum=%d&countPerPage=%d",page,pageCount);
		String content = getContentWithPostRefer(SCUT_EMPLOY_URL,SCUT_EMPLOY_URL,postData);
		Elements td = Jsoup.parse(content).select("a[class=ablue02]");
		for(Element job : td){
            list.add(new Employ(job.text().trim(),
                    String.format("http://employ.scut.edu.cn:8880/information/%s",job.attr("href"))));
		}
		return list;
	}
	
	/**
	 * 最近招聘专场公告
	 * @param page
	 * @param pageCount
	 * @return
	 */
	public static List<Employ> getSpecialRecent(int page,int pageCount){
		List<Employ> list = new ArrayList<Employ>();
		String postData = String.format("pageNum=%s&countPerPage=%s", page,pageCount);
		String content = getContentWithPostRefer(SCUT_EMPLOY_SPECIAL_RECENT_URL,SCUT_EMPLOY_SPECIAL_RECENT_URL,postData);
		Elements td = Jsoup.parse(content).select("a[class=ablue02]");
		for(Element job : td){
            list.add(new Employ(job.text().trim(),
                    String.format("http://employ.scut.edu.cn:8880/zpzc/%s",job.attr("href")) ));
		}
		return list;
	}

	/**
	 * 最近招聘专场公告
	 * @param page
	 * @param pageCount
	 * @return
	 */
	public static List<Employ> getSpecialAll(int page,int pageCount){
		List<Employ> list = new ArrayList<Employ>();
		String postData = String.format("pageNum=%s&countPerPage=%s", page,pageCount);
		String content = getContentWithPostRefer(SCUT_EMPLOY_SPECIAL_ALL_URL,SCUT_EMPLOY_SPECIAL_ALL_URL,postData);
		Elements td = Jsoup.parse(content).select("a[class=ablue02]");
		for(Element job : td){
            list.add(new Employ(job.text().trim(),
                    String.format("http://employ.scut.edu.cn:8880/zpzc/%s",job.attr("href")) ));
		}
		return list;
	}
	
	/**
	 * 外校招聘专场公告
	 * @param page
	 * @param pageCount
	 * @return
	 */
	public static List<Employ> getSpecialExitra(int page,int pageCount){
		List<Employ> list = new ArrayList<Employ>();
		String postData = String.format("pageNum=%s&countPerPage=%s", page,pageCount);
		String content = getContentWithPostRefer(SCUT_EMPLOY_SPECIAL_EXTRA_URL,SCUT_EMPLOY_SPECIAL_EXTRA_URL,postData);
		Elements td = Jsoup.parse(content).select("a[class=ablue02]");
		for(Element job : td){
			if(!job.text().trim().isEmpty()){
                list.add(new Employ(job.text().trim(),
                        String.format("http://employ.scut.edu.cn:8880/%s",job.attr("href").replace("../", ""))));
			}
		}
		return list;
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
