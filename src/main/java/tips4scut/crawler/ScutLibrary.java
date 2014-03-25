package tips4scut.crawler;

import java.net.CookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
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

import tips4scut.crawler.crawlerConsts.ScutLibraryConsts;
import tips4scut.model.Book4Borrow;
import tips4scut.model.Book4Info;
import tips4scut.model.Book4Search;
import tips4scut.utils.Pair;
import tips4scut.utils.StringUtil;

public class ScutLibrary {

	private String studentID = "";
	private String password = "";
	private boolean loginStatus = false;
	private String cookie;
    org.apache.http.client.CookieStore cookieStore;

    public static void main(String[] args) {

	//	ScutLibrary mylib = new ScutLibrary("D1030470101","chenhaiqi");

//		 System.out.println("查书:");
//		 System.out.println(SearchBookByTitle("java", 1, 5));
//		 //System.out.println(SearchBookByTitle("java", 2, 5));
//
//		 System.out.println("图书信息:");
//		 System.out.println(getBookDetailById("4886"));

        ScutLibrary.crawlStudentInfo("201030470102","chenhaiq");


	}

    public static Map<String,Object> crawlStudentInfo(String id,String password){
        Map<String,Object> map = new HashMap<String, Object>();
        ScutLibrary mylib = new ScutLibrary(id,password);
        map.put("userinfo",mylib.getUserInfo());
       // System.out.println(mylib.getUserInfo());
       // System.out.println("学生借书：");
       map.put("borrowBook", mylib.getUserLoanBookList());
        return  map;
    }

	/**
	 * 图书馆系统中的学生信息
	 * 
	 * @return
	 */
	public String getUserInfo() {
		if (loginStatus == false)
			return "";
		String content = getDataWithRefer(
				" http://202.38.232.10/opac/servlet/opac.go?cmdACT=mylibrary.index",
				"http://202.38.232.10/opac/servlet/opac.go?cmdACT=reader.info");
       // System.err.println(content);
		StringBuilder sb = new StringBuilder();
		String userStr = Jsoup.parse(content).select("table[align=center]").text();
		sb.append(
				userStr.substring(userStr.indexOf("姓名"),
						userStr.indexOf("借书证号")).replace("姓名","").trim()).append(
				StringUtil.DELIMIT_1ST);

		sb.append(userStr.substring(userStr.indexOf("欠费情况") + "欠费情况".length()).trim());
		return sb.toString();
	}

	/**
	 * 学生已借的书籍
	 * 
	 * @return
	 */
	private List<Book4Borrow> getUserLoanBookList() {
		if (loginStatus == false)
			return null;
		List<Book4Borrow> list = new ArrayList<Book4Borrow>();
		String content = getDataWithRefer(ScutLibraryConsts.LIBRARY_INDEX_URL,ScutLibraryConsts.LIBRARY_LOAN_URL);
		Elements select = Jsoup.parse(content)
				.select("table[class=table_line]");
		for (Element val : select.select("tr")) {
            Book4Borrow book4Borrow = new Book4Borrow();
			int cnt = 0;
			for (Element detail : val.select("td")) {
				if (cnt == 2) {
					//sb.append("书名#").append(detail.text().trim());
                    book4Borrow.setTitle(detail.text().trim());
				} else if (cnt == 5) {
				//	sb.append("$地方#").append(detail.text().trim());
                    book4Borrow.setPlace(detail.text().trim());
				} else if (cnt == 6) {
				//	sb.append("$借阅时间#").append(detail.text().trim());
                    book4Borrow.setStartTime(detail.text().trim());
				} else if (cnt == 7) {
				//	sb.append("$到期时间#").append(detail.text().trim());
                    book4Borrow.setEndTime(detail.text().trim());
				} else if (cnt == 8) {
				//	sb.append("$剩下借阅次数#").append(detail.text().trim());
                    book4Borrow.setRemain(detail.text().trim());
				} else if (cnt == 9) {
				//	sb.append("$是否过期#").append(detail.text().trim());
                    book4Borrow.setExpired(detail.text().trim());
				} else if (cnt == 10) {
				//	sb.append("$续借地址#")

                    book4Borrow.setContinueLink(detail
                            .getElementsByTag("a")
                            .attr("href")
                            .replace(
                                    "..",
                                    "http://"
                                            + ScutLibraryConsts.LIBRARY_HOST)
                            .trim());
				}
				cnt++;
			}
            if(book4Borrow.getTitle()!=null){
                System.out.println(book4Borrow);
                list.add(book4Borrow);
            }
		}
		return list;
	}

	public ScutLibrary(String studentId, String password) {
		this.studentID = studentId;
		this.password = password;
       getContentWithGet("http://202.38.232.10/opac/servlet/opac.go");
        loginScutLibraryInfo("http://202.38.232.10/opac/servlet/opac.go","");
		//loginScutLibrary(studentId, password);
	}

	public ScutLibrary() {
		this.studentID = "";
		this.password = "";
	}

	@SuppressWarnings("serial")
	public static Map<String, String> DEFAULT_EQUEST_MAP = new HashMap<String, String>() {
		{
	    	put("Host", "202.38.232.10");
			put("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
			put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			put("Accept-Language", "zh-CN,zh;q=0.8");
			put("Accept-Encoding", "gzip,deflate,sdch");
			put("Connection", "keep-alive");
            put("Cache-Control", "max-age=0");
            put("Referer","http://202.38.232.10/opac/servlet/opac.go?cmdACT=mylibrary.index");
           // put("Cookie","JSESSIONID=74B315B5D7371D694346BE2346414F6F");
//			put("Host", "202.38.232.10");
//			put("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0");
//			put("Accept","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//			put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//			put("Accept-Encoding", "gzip, deflate");
//			put("Referer","http://202.38.232.10/opac/servlet/mylib.go?cmdACT=mylibrary.index&method=myinfo");
//			put("Cookie", "JSESSIONID=C72D78A73112DAF9E7952BD3C8CF5FFF");
//			put("Connection", "keep-alive");
		}
	};

	public void loginScutLibrary(String studentId, String password) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(ScutLibraryConsts.LIBRARY_SERVER_URL);
		List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
		for (Entry<String, String> val : DEFAULT_EQUEST_MAP.entrySet()) {
			post.setHeader(val.getKey(), val.getValue());
            System.out.println(val.getKey() + "\t\t" + val.getValue());
		}
        if(cookie != null){
            httpClient.setCookieStore(cookieStore);
        }

        if(cookie != null){
            post.addHeader("Cookie", cookie);
        }
        String postData= String.format("cmdACT=mylibrary.login&libcode=&method=mylib&userid=%s&passwd=%s&user_login=%s",studentId,password,"%E7%99%BB%E5%BD%95");

		for (String postItem : postData.split("&")) {
			String[] arr = postItem.split("=");
			if (arr.length > 1) {
				parms.add(new BasicNameValuePair(arr[0], arr[1]));
			} else {
				parms.add(new BasicNameValuePair(arr[0], ""));
			}
		}
		post.setHeader("Referer","http://202.38.232.10/opac/servlet/opac.go?cmdACT=mylibrary.index&&method=myinfo");
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(parms);
			post.setEntity(entity);
			httpClient.execute(post);
			cookie = post.getLastHeader("Cookie").getValue();
            System.out.println(cookie);
			loginStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			loginStatus = false;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 按书名搜索图书信息
	 * 
	 * @param bookName
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public static List<Book4Search> SearchBookByTitle(String bookName, int page,
			int pageSize) {
		List<Book4Search> list = new LinkedList<Book4Search>();
		boolean bookNameStatus = bookName.trim().isEmpty();
		if (bookNameStatus == true)
			return null;
		String searchPostData = String.format(
				ScutLibraryConsts.LIBRARY_SEARCH_POST_DATA_STR, page - 1,
				bookName, pageSize);
		String res = getPageWithReferPostNoCookie(searchPostData,
				ScutLibraryConsts.LIBRARY_INDEX_URL,
				ScutLibraryConsts.LIBRARY_SERVER_URL);
		Elements elements = Jsoup.parse(res).select("table[id=result_content]");
		String tableTitle = null;

		for (Element tr : elements.select("tr")) {
			StringBuilder sb = new StringBuilder();
			if (tableTitle == null) {
				tableTitle = tr.text().trim();
				continue;
			}
			int cnt = 0;

			for (Element td : tr.select("td")) {
				if (cnt == 0) {
					cnt++;
					continue;
				}
				if (cnt == 1) {
					String bookDetail = td
							.getElementsByTag("a")
							.attr("href")
							.replace("..",
									"http://" + ScutLibraryConsts.LIBRARY_HOST);
					String id = bookDetail.substring(
							bookDetail.indexOf("(") + 1,
							bookDetail.length() - 1);
					sb.append(id).append(StringUtil.DELIMIT_2ND);
				}
                sb.append(td.text()).append(StringUtil.DELIMIT_2ND);
				cnt++;
			}
            String val  = sb.toString();
            val = val.contains("$") ? val.substring(0,val.length() -1) : "";
            Book4Search book = Book4Search.createBook4Search(val);
			list.add(book);
		}
		return list;
	}

	/**
	 * 通过 id 来查找书籍详细信息，LIBRARY_BOOK_STATUS(存放情况) LIBRARY_BOOK_NUM_STATUS(借阅数量情况)
	 * LIBRARY_BOOK_INFO(书籍信息)
	 * 
	 * @param bookId
	 * @return
	 */
	public static Book4Info getBookDetailById(String bookId) {

		String postData = String.format("cmdACT=query.bookdetail&bookid=%s",
				bookId);
		String resString = getPageWithReferPostNoCookie(postData,
				ScutLibraryConsts.LIBRARY_INDEX_URL,
				ScutLibraryConsts.LIBRARY_SERVER_URL);
		Elements tables = Jsoup.parse(resString).select("table[bgcolor=#d2d2d2]");
		Element infoTable = tables.get(0);
	    Map<String,String> bookDetail = new HashMap<String,String>();
		for (Element val : infoTable.select("tr")) {
			if (!val.text().trim().isEmpty()) {
                bookDetail.put(val.getElementsByClass("list_detail").text().trim(),
                        	val.getElementsByClass("book_list").text()
							.replace(";", "").trim().replace(" ",""));
			}
		}

		Element libTable = Jsoup.parse(resString)
				.select("table[id=queryholding]")
				.select("table[bgcolor=#d2d2d2]").get(0);
		String libInfo = null;
		StringBuilder bookStatus = new StringBuilder();
        Map<String, Integer> bookRemain = new HashMap<String, Integer>();
		for (Element tr : libTable.select("tr")) {
			if (libInfo == null) {
				libInfo = tr.text();
				continue;
			}
            StringBuilder sb = new StringBuilder();
			int cnt = 0;
			for (Element td : tr.select("td")) {
				if (cnt == 0 || cnt == 1 || cnt == 5 || cnt == 6) {
					String status = td.text().trim();
					if (cnt == 5) {
						Integer value = bookRemain.get(status);
						if (value == null) {
                            bookRemain.put(status, 1);
						} else {
                            bookRemain.put(status, value + 1);
						}
					}
                    if(!td.text().trim().isEmpty()){
					    sb.append(td.text().trim().replace(" ","")).append(StringUtil.DELIMIT_2ND);
                    }
				}
				cnt++;
			}
            String val = sb.toString();
            val = val.contains("$") ? val.substring(0,val.length() -1) :"";
			bookStatus.append(val).append(StringUtil.DELIMIT_1ST);
		}
        StringBuilder sb = new StringBuilder();
        for(Entry<String,Integer> entry : bookRemain.entrySet()){
            sb.append(entry.getKey() + " : " + entry.getValue()).append("\n");
        }
        Book4Info book4Info = new Book4Info(bookDetail,sb.toString(),bookStatus.toString().replace(StringUtil.DELIMIT_2ND,'\t'));
        System.out.println(book4Info);
		return book4Info;
	}

	private String getDataWithRefer(String refUrl, String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		for (Entry<String, String> val : DEFAULT_EQUEST_MAP
				.entrySet()) {
			get.setHeader(val.getKey(), val.getValue());
		}
		get.setHeader("Cookie", cookie);
		get.setHeader("Referer", refUrl);

		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			HttpResponse res = httpClient.execute(get);
			String resString = EntityUtils.toString(res.getEntity());
			return resString;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return "";
	}

	private static String getPageWithReferPostNoCookie(String postData,
			String refUrl, String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		for (Entry<String, String> val : DEFAULT_EQUEST_MAP
				.entrySet()) {
			post.setHeader(val.getKey(), val.getValue());
		}
		post.setHeader("Referer", refUrl);
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
			return EntityUtils.toString(res.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	public String getStudentId() {
		return studentID;
	}

	public void setStudentId(String studentId) {
		this.studentID = studentId;
	}

	public void SetPasswd(String passwd) {
		this.password = passwd;
	}

	public void loginScutLibraryInfo(String url, String referer) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();

        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());


        for (Entry<String, String> val : DEFAULT_EQUEST_MAP
				.entrySet()) {
			post.setHeader(val.getKey(), val.getValue());
		}

        if(cookie != null){
            httpClient.setCookieStore(cookieStore);
        }

        if(cookie != null){
            post.addHeader("Cookie", cookie);
        }


		post.setHeader("Referer", referer);

		for (Entry<String, String> val : ScutLibraryConsts.LOGIN_LIBRARY_POST_BASE_DATA
				.entrySet()) {
			parms.add(new BasicNameValuePair(val.getKey(), val.getValue()));
		}
		parms.add(new BasicNameValuePair("userid", studentID));
		parms.add(new BasicNameValuePair("passwd", password));
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(parms);
			post.setEntity(entity);
			HttpResponse res = httpClient.execute(post);
			cookie = post.getLastHeader("Cookie").getValue();
			System.out.println(EntityUtils.toString(res.getEntity()));
			loginStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			loginStatus = false;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

    private String getContentWithGet(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
//        get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
//                new DefaultHttpMethodRetryHandler());
        for(Entry<String, String> val :DEFAULT_EQUEST_MAP.entrySet()){
            get.addHeader(val.getKey(), val.getValue());
        }
        if(cookie != null){
            httpClient.setCookieStore(cookieStore);
        }

        if(cookie != null){
            get.addHeader("Cookie", cookie);
        }
        try {
            HttpResponse res = httpClient.execute(get);
            //String val = res.getFirstHeader("Set-Cookie").toString();
            //System.out.println("Test : " + val);
            cookieStore = httpClient.getCookieStore();
            System.out.println(cookieStore);

            if(cookieStore.getCookies().size() > 0){
                Cookie cookieTmp = cookieStore.getCookies().get(0);
                cookie = String.format("%s=%s", cookieTmp.getName().trim(),cookieTmp.getValue().trim());
                DEFAULT_EQUEST_MAP.put("Cookie",cookie.trim());
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
