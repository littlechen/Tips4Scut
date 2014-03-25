package tips4scut.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import tips4scut.model.Inform;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.StringUtil;

/**
 * 教务系统通知
 * 
 * 
 */

public class ScutAcademic {
	
//	public static void main(String [] args){
//		ScutAcademic test = new ScutAcademic();
//        test.getAcademicAllColleageNotice(1);
//        test.getAcademicMediaReport(1);
//        test.getAcademicNews(1);
//        test.getNoticeList(1);
//
//        List<Inform> list = test.getAcademicNoticeByCollege("数学系");
//        for(Inform val : list){
//			System.out.println(val);
//		}
//	}


    public static String List2ValStr(List<Inform> list){
        StringBuilder sb = new StringBuilder();
        for(Inform val : list){
            sb.append(val.toAcademicString()).append(StringUtil.DELIMIT_1ST);
        }
        String res = sb.toString();
        System.out.println(res);
        return res.contains("|") ? res.substring(0,res.length() - 1) : "";
    }

    public static void crawlAcademicInform(){
        Jedis jedis = RedisServ.getJedis();
        ScutAcademic crawler = new ScutAcademic();

        for(int i=1 ; i<=3 ; i++ ){
            jedis.set(RedisKeyUtils.getAcadeicNotic(i),List2ValStr(crawler.getNoticeList(i) ) );
        }

        for(int i=1 ; i<=3 ; i++ ){
            jedis.set(RedisKeyUtils.getAcademicMediaReport(i),List2ValStr(crawler.getAcademicMediaReport(i) ) );
        }

        for(int i=1 ; i<=3 ; i++ ){
            jedis.set(RedisKeyUtils.getAcademicNew(i),List2ValStr(crawler.getAcademicNews(i) ) );
        }

        for(int i=1 ; i<=3 ; i++ ){
            jedis.set(RedisKeyUtils.getAcademicAllCollege(i),List2ValStr(crawler.getAcademicAllColleageNotice(i) ) );
        }
    }

    public static void crawlColleageByName(String name){
        ScutAcademic crawler = new ScutAcademic();
        Jedis jedis = RedisServ.getJedis();
        jedis.set(RedisKeyUtils.getAcadeicCollegeByName(name),List2ValStr(crawler.getAcademicNoticeByCollege(name) ) );
    }

	private static final String ACADEMIC_POST_STR = "postBack_Bt=Button&__EVENTVALIDATION=%s&__VIEWSTATE=%s&currentPage=%s";
	private static final String ACADEMIC_NOTICE_PAGE = "http://202.38.193.235/jiaowuchu/%E9%A6%96%E9%A1%B5/%E6%95%99%E5%8A%A1%E9%80%9A%E7%9F%A51/more.aspx";
	private static final String ACADEMIC_NEWS_PAGE = "http://202.38.193.235/jiaowuchu/%E9%A6%96%E9%A1%B5/%E6%96%B0%E9%97%BB%E5%8A%A8%E6%80%81/more.aspx";
	private static final String ACADEMIC_MEDIA_PAGE = "http://202.38.193.235/jiaowuchu/%E9%A6%96%E9%A1%B5/%E5%AA%92%E4%BD%93%E5%85%B3%E6%B3%A8/more.aspx";
	private static final String ACADEMIC_COLLEGE_PAGE = "http://202.38.193.235/jiaowuchu/%E9%A6%96%E9%A1%B5/%E5%AD%A6%E9%99%A2%E4%BF%A1%E6%81%AF/more.aspx";
	@SuppressWarnings("serial")
	private static final Map<String, String> HEADER_ACADEMIC_MAP = new HashMap<String, String>(){
		{
			put("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
			put("Accept", "text/html, application/xhtml+xml, */*");
			put("Accept-Language","zh-CN");
			put("Host", "202.38.193.235");
			put("Connection", "Keep-Alive");
			put("Cache-Control","no-cache");	
			put("DNT", "1");
		}
	};
	
	@SuppressWarnings("serial")
	private static final Map<String,Integer> ACADEMIC_TYPE_MAP = new HashMap<String, Integer>(){
		{
			put(ACADEMIC_NOTICE_PAGE, 1);
			put(ACADEMIC_NEWS_PAGE, 2);
			put(ACADEMIC_MEDIA_PAGE, 3);
			put(ACADEMIC_COLLEGE_PAGE,4);
		}
	};
	
	
	private CookieStore cookie = null;
	private String cookies2Post = null;
	private String viewStatus = null;
	private String eventValdation = null;
	private List<Inform> recentNotice = null; //缓存
	private int recentType = 0; //缓存类型

	/**
	 * 
	 * @param url
	 */
	public void initNoticeSetting(String url){
		String content = getContentWithGet(url);
		Document doc = Jsoup.parse(content);
		viewStatus = doc.select("input[id=__VIEWSTATE]").attr("value").trim();
		eventValdation = doc.select("input[id=__EVENTVALIDATION]").attr("value").trim();
		recentNotice = getPageNoticeList(content);
	}


	private List<Inform> getNotice(String url,int page){
		if(eventValdation == null && viewStatus == null && recentType!= ACADEMIC_TYPE_MAP.get(url)){
			initNoticeSetting(url);//更新
		}
		if(page == 1){
			return recentNotice;
		}else{
			String postData = String.format(ACADEMIC_POST_STR,eventValdation, viewStatus,page);
			String content = getContentWithPostRefer(url,postData);
			return getPageNoticeList(content);
		}
	}
	
	/**
	 * 教务通知
	 * @param page
	 * @return
	 */
	public List<Inform> getNoticeList(int page){
		return getNotice(ACADEMIC_NOTICE_PAGE,page);
	}
	
	/**
	 * 教务新闻
	 * @param page
	 * @return
	 */
	public List<Inform> getAcademicNews(int page){
		return getNotice(ACADEMIC_NEWS_PAGE,page);
	}
	
	/**
	 * 媒体报道
	 * @param page
	 * @return
	 */
	public List<Inform> getAcademicMediaReport(int page){
		return getNotice(ACADEMIC_MEDIA_PAGE,page);
	}
	
	/**
	 * 所有学院通知
	 * @param page
	 * @return
	 */
	public List<Inform> getAcademicAllColleageNotice(int page){
		return getNotice(ACADEMIC_COLLEGE_PAGE,page);
	}
	
	/**
	 * 某一个学院的通知，搜索接口
	 * @param colleageName
	 * @return
	 */
	public List<Inform> getAcademicNoticeByCollege(String colleageName){
		List<Inform> resList = new ArrayList<Inform>();
		for(int i= 1;i <8;i++){
			List<Inform> list = getNotice(ACADEMIC_COLLEGE_PAGE,i);
			for(Inform val : list){
				if(val.getTitle().contains(colleageName)){
					resList.add(val);
				}
			}
		}
		return resList;
	}
	

	private String getContentWithPostRefer(String url,String postData){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		if(cookie != null){
			httpClient.setCookieStore(cookie);
		}
		for(Entry<String, String> val :HEADER_ACADEMIC_MAP.entrySet()){
			post.addHeader(val.getKey(), val.getValue());
		}
		post.addHeader("Referer", url);
		if(cookies2Post != null){
			post.addHeader("Cookie", cookies2Post);
		}
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
			httpClient.getConnectionManager().shutdown();
		}
		return "";
	}
	
	private List<Inform> getPageNoticeList(String content){
		List<Inform> list = new LinkedList<Inform>();
		Elements tr = Jsoup.parse(content).select("tr[align=left");
		for(Element val : tr){
			String title = val.select("a[title=点击观看]").text();
			String time = val.select("span").text();
            Inform tmp = new Inform(title,"");
            tmp.setTime(time);
            tmp.setAuthor("教务处");
            list.add(tmp);
			//list.add(String.format("%s|%s", title,time));
		}
		return list;
	}
	
	private String getContentWithGet(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		if(cookie != null){
			httpClient.setCookieStore(cookie);
		}
		for(Entry<String, String> val :HEADER_ACADEMIC_MAP.entrySet()){
			get.addHeader(val.getKey(), val.getValue());
		}
		get.addHeader("Referer", url);
		try {
			HttpResponse res = httpClient.execute(get);
			cookie = httpClient.getCookieStore();
			if(cookie.getCookies().size() > 0){
				Cookie cookieTmp = cookie.getCookies().get(0);
				cookies2Post = String.format("%s=%s", cookieTmp.getName(),cookieTmp.getValue());
			}
			String resStr = EntityUtils.toString(res.getEntity());
			httpClient.getConnectionManager().shutdown();
			return resStr;
		} catch (Exception e) {
			e.printStackTrace();
			httpClient.getConnectionManager().shutdown();
		}
		return "";
	}
}
