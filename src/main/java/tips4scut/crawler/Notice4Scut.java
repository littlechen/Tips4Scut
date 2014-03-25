package tips4scut.crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import tips4scut.model.Inform;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.StringUtil;

/**
 * 各个学校机构的通知信息
 * @author dynamite
 *
 */

public class Notice4Scut {
	
	
	@SuppressWarnings("serial")
	public static final Map<String, String> HEADER_MAP = new HashMap<String, String>(){
		{
			put("Content-type","text/html" );
			put("Accept", "text/html, application/xhtml+xml, */*");
			put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
			put("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			put("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
			put("Connection", "Keep-Alive");
			put("DNT", "1");
		}
	};

    public static void crawlInform(){
        Jedis jedis = RedisServ.getJedis();
        for(int i = 1 ; i <= 3 ; i++){
            jedis.set(RedisKeyUtils.getInformLogistics(i),List2ValStr(getLogistics(i)));
        }

        for(int i = 1 ; i <= 3 ; i++){
            jedis.set(RedisKeyUtils.getCampusCard(i),List2ValStr(getCampusCardNotice(i)));
        }

        for(int i = 1 ; i <= 3 ; i++){
            jedis.set(RedisKeyUtils.getInformCampusNetwork(i),List2ValStr(getCampusNetWorkNotice(i)));
        }

        for(int i = 1 ; i <= 3 ; i++){
            jedis.set(RedisKeyUtils.getInformLibrary(i),List2ValStr(getLibraryNotice(i)));
        }

        for(int i = 1 ; i <= 3 ; i++){
            jedis.set(RedisKeyUtils.getInformYourth(i),List2ValStr(getYouthNotice(i)));
        }

        for(int i = 1 ; i <= 2 ; i++){
            jedis.set(RedisKeyUtils.getInformGlobalPartnerships(i),List2ValStr(getGlobalPartnerships(i)));
        }
        jedis.set(RedisKeyUtils.getinformHospital(),List2ValStr(getHospitalNotice()));

        jedis.set(RedisKeyUtils.getCampusTransferMarchine(),strList2ValStr(getCampusTransferMachine()) );

        jedis.set(RedisKeyUtils.getInformParttimejob(),List2ValStr(getPartTimeJob()));

    }

    public static String List2ValStr(List<Inform> list){
        StringBuilder sb = new StringBuilder();
        for(Inform val : list){
            sb.append(val.toString()).append(StringUtil.DELIMIT_1ST);
        }
        String res = sb.toString();
        System.out.println(res);
        return res.contains("|") ? res.substring(0,res.length() - 1) : "";
    }

    public static String strList2ValStr(List<String> list){
        StringBuilder sb = new StringBuilder();
        for(String val : list){
            sb.append(val).append(StringUtil.DELIMIT_1ST);
        }
        String res = sb.toString();
        System.out.println(res);
        return res.contains("|") ? res.substring(0,res.length() - 1) : "";
    }

	public static List<Inform> getLogistics(int page){
		List<Inform> list = new ArrayList<Inform>();
		String indexUrl = "http://202.38.194.216";
		String url = String.format("http://202.38.194.216/news/f_news.asp?page=%s&classid=7&Nclassid=",page);
		String content = "";
		try {
			content = new String(getPageConent(url).getBytes("ISO-8859-1"),"GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Elements select = Jsoup.parse(content).select("td[style=letter-spacing:0px;line-height:23px;font-family:宋体;font-size:10pt;]");
		for(Element val : select){
			String noticeTitle = val.text().trim().substring(1);
			Element a =  val.select("a").get(0);
			String title = a.attr("title").trim();
			String noticePeople = "校内各用户";
			if(title.contains("：")){
				noticePeople = title.substring(title.indexOf("宋体>") + 3,title.lastIndexOf("："));
			}
			String noticeUrl = String.format("%s%s", indexUrl, a.attr("href").substring(2));
            Inform tmp = new Inform(noticeTitle,noticeUrl,"生活后勤");
            tmp.setForUserType(noticePeople);
			list.add(tmp);
		}
		return list;
	}
	

	public static List<Inform> getPartTimeJob(){
		List<Inform> list = new ArrayList<Inform>();
		String url = "http://student1.scut.edu.cn:8280/list.jsp?topDirId=299&currentId=334";
		String content = getPageConent(url);
		Elements select = Jsoup.parse(content).select("ul").select("a");
		for(Element val : select){
			String job = val.text().trim();
			if(!job.contains("已截止") && job.length() > 5){
				list.add(new Inform(job,"","勤工助学"));
			}
		}
		return list;
	}
	


	public static List<Inform> getYouthNotice(int page){
		List<Inform> list = new ArrayList<Inform>();
		String indexUrl = "http://youth.100steps.net";
		String url = String.format("http://youth.100steps.net/articlelist-159-%d.htm",page);
		String content = getPageConent(url);
		Elements select = Jsoup.parse(content).select("ul[class=cont_list inner_cont_list]").select("li");
		for(Element val : select.select("a")){
           list.add(new Inform(val.text().trim(),String.format("%s%s",indexUrl,val.attr("href") ),"校团委") );
		}
		return list;
	}

	public static String getYouthNotice(String noticeUrl){
		String content = getPageConent(noticeUrl);
		Elements select = Jsoup.parse(content).select("div[class=container").select("div[class=scrapCont]");
		String title = select.select("h2").text().trim();
		String body = select.text().trim().replace(" ", "|");
		return String.format("%s#%s", title,body);
	}

	public static List<Inform> getLibraryNotice(int page){
		List<Inform> list = new ArrayList<Inform>();
		String url = String.format("http://www.lib.scut.edu.cn/more/more.jsp?Pages=%s",page);
		String content = getPageConent(url);
		Elements select = Jsoup.parse(content).select("div[id=style1]").select("a");
		for(Element val : select){
			String text = val.text().trim();
			if(text.length()  > 5){
				String link = String.format("%s%s","http://www.lib.scut.edu.cn",val.attr("href").trim());
				list.add(new Inform(text,link,"图书馆"));
			}
		}
		return list;
	}

	public static List<Inform> getHospitalNotice(){
		List<Inform> list = new ArrayList<Inform>();
		String url = "http://www2.scut.edu.cn/s/85/t/87/p/21/list.htm";
		String content = "";
		try {
			content = new String(getPageConent(url).getBytes("ISO-8859-1"),"utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(Element a : Jsoup.parse(content).select("div[id=newslist]").select("a[target=_blank]")){
			String link = a.attr("href");
			String title = a.text().trim();
            list.add(new Inform(title,link,"校医院"));
		}
		return list;
	}

	public static List<Inform> getCampusCardNotice(int page){
		List<Inform> list = new ArrayList<Inform>();
		String url = String.format("http://www2.scut.edu.cn/s/134/t/139/p/11/i/%s/list.htm",page);
		String content = "";
		try {
			content = new String(getPageConent(url).getBytes("ISO-8859-1"),"utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(Element a : Jsoup.parse(content).select("div[id=newslist]").select("a[target=_blank]")){
			String link = a.attr("href");
			String title = a.text().trim();
            list.add(new Inform(title,link,"校园一卡通"));
		}
		return list;
	}
	

	public static List<String> getCampusTransferMachine(){
		List<String> list = new ArrayList<String>();
		String url = "http://www2.scut.edu.cn/s/134/t/139/p/1/c/4204/d/4221/list.htm";
		String content = "";
		try {
			content = new String(getPageConent(url).getBytes("ISO-8859-1"),"utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int cnt = 1;
		for(Element tr : Jsoup.parse(content).select("tr[align=middle]")){
			if(cnt == 1) {
				cnt = 0;
				continue; 
			}
			Elements tds = tr.select("td");
            String info = String.format("%s,%s$%s", tds.get(1).text(),tds.get(3).text(),tds.get(2).text());
			list.add(info);
		}
		return list;
	}

	public static List<Inform> getCampusNetWorkNotice(int page){
		List<Inform> list = new ArrayList<Inform>();
		String url = String.format("http://web.scut.edu.cn/s/39/t/13/p/1/c/560/i/%s/list.htm",page);
		String content = "";
		try {
			content = new String(getPageConent(url).getBytes("ISO-8859-1"),"utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(Element a : Jsoup.parse(content).select("div[id=newslist]").select("a[target=_blank]")){
			String link = a.attr("href");
			String title = a.text().trim();
            list.add(new Inform(title,link,"网络中心"));
			//list.add(String.format("%s|http://web.scut.edu.cn/%s", title,link));
		}
		return list;
	}
	

	public static List<Inform> getGlobalPartnerships(int page){
		List<Inform> list = new ArrayList<Inform>();
		String url = String.format("http://www2.scut.edu.cn/s/100/t/97/p/4/i/%s/list.htm",page);
		String content = "";
		try {
			content = new String(getPageConent(url).getBytes("ISO-8859-1"),"utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(Element a : Jsoup.parse(content).select("div[class=list]").select("a[target=_blank]")){
			String link = a.attr("href");
			String title = a.text().trim();
            list.add(new Inform(title,link,"中外合作办学办公室"));
			//list.add(String.format("%s|http://www2.scut.edu.cn%s", title,link));
		}
		return list;
	}
	
	private static String getPageConent(String url) {
		HttpClient client=new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		for (Entry<String, String> val : HEADER_MAP.entrySet()) {
			getMethod.setRequestHeader(val.getKey(), val.getValue());
		}
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			                          new DefaultHttpMethodRetryHandler());
		 try {
			client.executeMethod(getMethod);
			//System.out.println(getMethod.getRequestCharSet());
			String res =  getMethod.getResponseBodyAsString();
			getMethod.releaseConnection();
			return res;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
