package tips4scut.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import tips4scut.model.Item4Sell;
import tips4scut.redis.RedisKeyUtils;
import tips4scut.redis.RedisServ;
import tips4scut.utils.StringUtil;


public class ScutSecondMarket {

	private static final String ELECTRON_MARKET_PAGE ="http://bbs.scut.edu.cn/classic/content.jsp?forumID=394";
	private static final String BOOK_MARKET_PAGE = "http://bbs.scut.edu.cn/classic/content.jsp?forumID=538";
	private static final String BIKE_MARKET_PAGE_= "http://bbs.scut.edu.cn/classic/content.jsp?forumID=545";
	private static final String HOURSE_MARKET_PAGE = "http://bbs.scut.edu.cn/classic/automatch/index.jsp?forumID=541";
	
	
	public static void main(String [] args){
		getElectronMarketItem();
        getBikeMarketItem();
        getHourseMarketItem();
        getBookMarketItem();
	}


    public static String List2ValStr(List<Item4Sell> list){
        StringBuilder sb = new StringBuilder();
        for(Item4Sell val : list){
            sb.append(val.toString()).append(StringUtil.DELIMIT_1ST);
        }
        String res = sb.toString();
        System.out.println(res);
        return res.contains("|") ? res.substring(0,res.length() - 1) : "";
    }

    public static void crawlMarket(){
        Jedis jedis = RedisServ.getJedis();
        jedis.set(RedisKeyUtils.getMarketBike(),List2ValStr(getBikeMarketItem()));
        jedis.set(RedisKeyUtils.getMarketBook(),List2ValStr(getBookMarketItem()));
        jedis.set(RedisKeyUtils.getMarketElectron(),List2ValStr(getElectronMarketItem()));
        jedis.set(RedisKeyUtils.getMarketHourse(),List2ValStr(getHourseMarketItem()));
    }
	
	/**
	 * 电子二手市场
	 * @return
	 */
	public static List<Item4Sell> getElectronMarketItem(){
		String content = getContentWithGet(ELECTRON_MARKET_PAGE);
		return getPageItem2List(content);
	}
	
	/**
	 * 书籍二手市场
	 * @return
	 */
	public static List<Item4Sell> getBookMarketItem(){
		String content = getContentWithGet(BOOK_MARKET_PAGE);
		return getPageItem2List(content);
	}
	
	/**
	 * 物品二手市场
	 * @return
	 */
	public static List<Item4Sell> getBikeMarketItem(){
		String content = getContentWithGet(BIKE_MARKET_PAGE_);
		return getPageItem2List(content);
	}
	
	/**
	 * 出租房二手市场
	 * @return
	 */
	public static List<Item4Sell> getHourseMarketItem(){
		String content = getContentWithGet(HOURSE_MARKET_PAGE);
		return getPageItem2List(content);
	}
	
	private static List<Item4Sell> getPageItem2List(String content){
		List<Item4Sell> list = new ArrayList<Item4Sell>();
		Elements document = Jsoup.parse(content).select("table[class=listTable]").select("tr");
		boolean first = true;
		for(Element tr : document){
			if(first){
				first = false;
				continue;
			}
			Elements val = tr.select("a");
			String link = val.attr("href");
			String title = val.text().trim();
			String time = tr.select("p[class=content_p02]").get(0).text();
			String hasSeen = tr.select("td[class=tf]").get(2).text();
			String item = String.format("%s|%s|%s|%s",title,link,time,hasSeen);
            list.add(new Item4Sell(title,time,link,hasSeen));
		}
		return list;
	}
	
	
	private static String getContentWithGet(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		get.addHeader("Referer", url);
		try {
			HttpResponse res = httpClient.execute(get);
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
