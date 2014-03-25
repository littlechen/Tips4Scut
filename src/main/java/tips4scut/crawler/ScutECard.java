package tips4scut.crawler;

import java.util.*;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import tips4scut.model.EcardConsume;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.StringUtil;

public class ScutECard {
	private static final String E_CARD_HOME_URL = "http://116.57.72.198/homeLogin.action";
	private static final String E_CARD_LOGIN_URL = "http://116.57.72.198/loginstudent.action";
	private static final String E_CARD_VERICODE_URL = "http://116.57.72.198/getCheckpic.action?rand=8674.793053818528";
	private static final String E_CARD_ACOUNT_USER_URL = "http://116.57.72.198/accountcardUser.action";
	private static final String E_CARD_ACOUNT_FRAME = "http://116.57.72.198/accountleftFrame.action";
	
	private static final String E_CARD_ACOUNT_TODAY = "http://116.57.72.198/accounttodatTrjnObject.action";
	private static final String E_CARD_ACCOUNT_QUERY_START = "http://116.57.72.198/accounthisTrjn.action";
	private static final String E_CARD_ACCOUNT_RESULT_VIEW_BY_PAGE = "http://116.57.72.198/accountconsubBrows.action";
	@SuppressWarnings("serial")
	public static final Map<String, String> HEADER_ACADEMIC_MAP = new HashMap<String, String>(){
		{
			put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			put("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			put("Accept-Encoding","gzip, deflate");
			put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0");
			put("Connection", "Keep-Alive");
			put("Host", "116.57.72.198");
		}
	};
	
//	public static void main(String [] args){
//		ScutECard test = new ScutECard("201030470102","888888");
//		test.loginEcard();
//		test.getUserInfo();
//		test.queryUserConsumeByDate("20131001","20131031");
//		System.out.println();
//		test.getConsumeNextPage(2);
//
//		System.out.println();
//		test.getConsumeNextPage(1);
//		test.getUserConsumeToday();
//	}

    public static String List2ValStr(List<EcardConsume> list){
        StringBuilder sb = new StringBuilder();
        for(EcardConsume val : list){
            sb.append(val.toString()).append(StringUtil.DELIMIT_1ST);
        }
        String res = sb.toString();

        res = res.contains("|") ? res.substring(0,res.length() - 1) : "";
        System.out.println(res);
        return  res;
    }

    public static List<EcardConsume> crawlEcard4Student(
           String id,String password,String startDate,String endDate){
        ScutECard crawler = new ScutECard(id,password);

        crawler.loginEcard();
        crawler.getUserInfo();
        crawler.queryUserConsumeByDate(startDate, endDate);
        List<EcardConsume> resList = new LinkedList<EcardConsume>();

        List<EcardConsume> list;
        list = crawler.getConsumeNextPage(1);
        resList.addAll(list);

        Jedis jedis = RedisServ.getJedis();
        jedis.set(RedisKeyUtils.getEcardConsume(id,2,false),List2ValStr(list));
               list = crawler.getConsumeNextPage(2);
        resList.addAll(list);
               jedis.set(RedisKeyUtils.getEcardConsume(id,1,false),List2ValStr(list));
               list = crawler.getConsumeNextPage(3);
        resList.addAll(list);
               jedis.set(RedisKeyUtils.getEcardConsume(id,3,false),List2ValStr(list));


        return resList;
    }



    public static List<EcardConsume> crawlEcardConsumeToday(String id,String password){
        ScutECard crawler = new ScutECard(id,password);
        crawler.loginEcard();
        crawler.getUserInfo();
        List<EcardConsume> list =  crawler.getUserConsumeToday();
        Jedis jedis = RedisServ.getJedis();
        jedis.set(RedisKeyUtils.getEcardConsume(id,0,true),List2ValStr(list));
        return list;
    }

	private String userId;
	private String userPasswd;
	private String cardId;
	private String consumeUrl = null;
	private String selectStart = null;
	private String selectEnd = null;
	private CookieStore cookie = null;
	private String cookies2Post = null;
	
	public ScutECard(String userId,String userPasswd){
		this.userId = userId;
		this.userPasswd = userPasswd;
		this.cardId = null;
	}
	
	
	/**
	 * 登录校园一卡通，得到Cookie,相关参数
	 */
	private void loginEcard(){
		getContentWithGet(E_CARD_HOME_URL,null);//求请页面，得到相应的cookies
		getContentWithGet(E_CARD_VERICODE_URL,E_CARD_HOME_URL);//请求服务器，得到认证码
		String postData = String.format(
				"name=%s&loginType=2&userType=1&passwd=%s&rand=8674&imageField.x=17&imageField.y=16",userId,userPasswd);
		getContentWithPostRefer(E_CARD_LOGIN_URL,E_CARD_HOME_URL,postData);
	}
	
	/**
	 * 一卡通信息
	 * @return
	 */
	private String getUserInfo(){
		String content = getContentWithGet(E_CARD_ACOUNT_USER_URL,E_CARD_ACOUNT_FRAME);
		Elements document = Jsoup.parse(content).select("table[cellspacing=8]");
		this.cardId = document.select("td[width=10%]").text();
		String userInfo = document.text().replace(" ", "");
		userInfo = userInfo.substring(userInfo.lastIndexOf("卡状态"),userInfo.lastIndexOf("银行"));
		return userInfo;
	}
	
	/**
	 * 学生当天消费
	 * @return
	 */
	private List<EcardConsume> getUserConsumeToday(){
		String content = getContentWithGet(E_CARD_ACOUNT_TODAY,E_CARD_ACOUNT_FRAME);
		return getPage2List(content);
	}
	
	/**
	 * 按日期查找学生消费情况
	 * @param start , eg 20131001
	 * @param end, eg 20131031
	 * @return
	 */
	private List<EcardConsume> queryUserConsumeByDate(String start,String end){
		if(cardId != null){
			//查询主页
			String content = getContentWithGet(E_CARD_ACCOUNT_QUERY_START, E_CARD_ACOUNT_FRAME);
			String url = getForwordPage(content);
			//System.out.println(url);
			//选择类型
			String postType = "account=" + this.cardId + "&inputObject=all&Submit=+%C8%B7+%B6%A8+";
			content = getContentWithPostRefer(url, E_CARD_ACCOUNT_QUERY_START,postType);
			String chooseUrl = getForwordPage(content);
			//System.out.println(chooseUrl);
			//选择日期
			String postDate = String.format("inputStartDate=%s&inputEndDate=%s",start,end);
			content = getContentWithPostRefer(chooseUrl,url,postDate);
			String resultViewUrl = getForwordPage(content);
			//System.out.println(resultViewUrl);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//查询结果
			content = getContentWithPostRefer(resultViewUrl,chooseUrl, null);
			consumeUrl = resultViewUrl;
			selectStart = start;
			selectEnd = end;
			return getPage2List(content);
		}else{
			return new ArrayList<EcardConsume>();
		}
	}

	/**
	 * 消费页面情况翻页
	 * @param page
	 * @return
	 */
	private List<EcardConsume> getConsumeNextPage(int page){
		if(selectEnd != null && selectEnd !=null && consumeUrl != null){
			String postDate = String.format("inputStartDate=%s&inputEndDate=%s&pageNum=%s",selectStart,selectEnd,page);
			String content = getContentWithPostRefer(E_CARD_ACCOUNT_RESULT_VIEW_BY_PAGE,consumeUrl, postDate);
			return getPage2List(content);
		}else{
			return new ArrayList<EcardConsume>();
		}
	}
	
	private String getForwordPage(String content){
		String resultViewUrl = Jsoup.parse(content).select("form[id=accounthisTrjn]").attr("action");
		resultViewUrl = String.format("http://116.57.72.198%s", resultViewUrl);
		return resultViewUrl;
	}
	
	private List<EcardConsume> getPage2List(String content){
		List<EcardConsume> list = new ArrayList<EcardConsume>();
		Elements trs = Jsoup.parse(content).select("tr[class=listbg]");
		for(Element tr : trs){
			StringBuilder sb = new StringBuilder();
			for(Element td : tr.select("td")){
				sb.append(td.text().trim()).append(StringUtil.DELIMIT_2ND);
			}
			String res = sb.toString();
			res = res.contains("$") ? res.substring(0,res.length() - 1 ) : res;
            list.add(new EcardConsume(res));
		}
		return list;
	}


	private String getContentWithGet(String url,String referer){
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
		if(cookies2Post != null){
			get.addHeader("Cookie", cookies2Post);
		}
		if(referer != null){
			get.addHeader("Referer", url);
		}
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
	
	private String getContentWithPostRefer(String url,String referer,String postData){
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
		if(referer != null){
			post.addHeader("Referer", referer);
		}
		if(cookies2Post != null){
			post.addHeader("Cookie", cookies2Post);
		}
		List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
		if(postData != null){
			for (String val : postData.split("&")) {
				String[] arr = val.split("=", 2);
				parms.add(new BasicNameValuePair(arr[0].trim(), arr[1].trim()));
			}
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
	
}
