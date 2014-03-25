package tips4scut.crawler;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tips4scut.crawler.crawlerConsts.ScutJW2005Consts;
import tips4scut.model.Course;
import tips4scut.model.Jw2005Schedule;
import tips4scut.model.Jw2005Score;
import tips4scut.utils.StringUtil;

public class ScutJW2005 implements Serializable{
	public static final String JW2005_USR_INDEX_PAGE = "http://%s/(%s)/xs_main.aspx?xh=%s";
	public static final String JW2005_USR_SCHEDULE_PAGE = "http://%s/(%s)/xskbcx.aspx?xh=%s&xm=%s&gnmkdm=N121603";
	public static final String JW2005_USR_TRANSCRIPT_PAGE = "http://%s/(%s)/xscjcx.aspx?xh=%s&xm=%s&gnmkdm=N121605";

	private String host;
	private String loginUrl;
	private String tagCode;
	private String veriCodeUrl;  //验证码地址
	private String referUrl;
	private String viewStatus;

	private String studentId;
	private String passwd;
	private String studentName;
	private String studentDetail;
	private boolean tranStatus = false;

    public static void main(String[] args) throws IOException {
//		ScutJW2005 myJw2005 = new ScutJW2005();
//		System.out.print("输入验证码： ");
//		@SuppressWarnings("resource")
//		String vericode = new Scanner(System.in).nextLine().trim();
//		myJw2005.loginJw2005("201030470102", "290934",vericode);

        try{
            File file = new File("jw2005.out");

//            ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
//            oout.writeObject(myJw2005);
//            oout.close();
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
            ScutJW2005 my = (ScutJW2005)oin.readObject();
            System.out.println("After Seri:");
            Jw2005Schedule job2 = my.getUsrSchedule();
            System.out.println(job2);

            		System.out.println(my);
		StringUtil.printList4Score(my.getUsrTranscriptByAll());

		System.out.println("year:");
	//	StringUtil.printList4Score(my.getUsrTranscriptByYear(3));

		System.out.println("term:");
		//StringUtil.printList4Score(my.getUsrTranscriptByTerm(1, 1));
		//Jw2005Schedule job = my.getUsrSchedule();
		//System.out.println(job);


            oin.close();
            System.out.println(my);
        }catch (Exception e){
            e.printStackTrace();
        }
	}

	public ScutJW2005() {
		this.studentName = "N/A";
		this.studentDetail = "N/A";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		GetMethod getMethod = new GetMethod(ScutJW2005Consts.JW2005_URL);
		for (Entry<String, String> val : ScutJW2005Consts.HEADER_JW2005_MAP
				.entrySet()) {
			getMethod.setRequestHeader(val.getKey(), val.getValue());
		}
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			int statuscode = httpClient.executeMethod(getMethod);
			if (statuscode != HttpStatus.SC_OK) {
				System.err.print("Login Error");
			}
			Document doc = Jsoup.parse(getMethod.getResponseBodyAsString());
			Elements select = doc.select("input[name=__VIEWSTATE]");
			this.viewStatus = select.attr("value");
			this.loginUrl = getMethod.getURI().toString();
			this.tagCode = loginUrl.substring(loginUrl.indexOf("(") + 1,
					loginUrl.indexOf(")"));
			this.host = loginUrl.substring(0, loginUrl.indexOf("/("))
					.replace("http://", "").trim();
			this.veriCodeUrl = "http://" + host + "/(" + tagCode + ")/"
					+ "CheckCode.aspx";
			getVeriCodePic(this.veriCodeUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public List<Jw2005Score> getUsrTranscript(String postData) {
		List<Jw2005Score> list = new ArrayList<Jw2005Score>();
		if (tranStatus == false) {
            String back = String.format("http://%s/(%s)/xs_main.aspx?xh=%s",host,tagCode,studentId);
            String forward = String.format("http://%s/(%s)/xscj.aspx?xh=%s&xm=%s&gnmkdm=N121605",host,tagCode,studentId,studentName);
            String content = getDataWithRefer(back, forward);
          //  System.out.println(content);
			tranStatus = true;
		}
		postData = String.format("__VIEWSTATE=%s&", viewStatus) +  postData;
        String forward = String.format("http://%s/(%s)/xscj.aspx?xh=%s&xm=%s&gnmkdm=N121605",host,tagCode,studentId,studentName);
        String content = getDataWithReferPost(postData,forward, forward);
        Elements table = Jsoup.parse(content).select("table[class=datelist]").select("tr");
        table.remove(0);
		for (Element tr : table) {
			StringBuilder sb = new StringBuilder();
			Elements tds = tr.select("td");

            if(tds.size() == 10 ){
                String courseName = tds.get(1).text().trim();
                String type= tds.get(2).text().trim();
                String paperScore =  tds.get(3).text().trim();
                String score =  tds.get(4).text().trim();
                String credit =  tds.get(8).text().trim();
                Jw2005Score tmp = new Jw2005Score(courseName,type,paperScore,score,credit);
                list.add(tmp);
                System.out.println(tmp);
            }
		}
		return list;
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	public List<Jw2005Score> getUsrTranscriptByAll() {
		String postData = String.format(
				ScutJW2005Consts.JW2005_TRANSSCRIPT_POST,
				"%D4%DA%D0%A3%D1%A7%CF%B0%B3%C9%BC%A8%B2%E9%D1%AF");
		return getUsrTranscript(postData);
	}

	/**
	 * 学生成绩单，按学期
	 * @param year 大一：1,大二：2，大三：3
	 * @param term 1,2
	 * @return
	 */
	public List<Jw2005Score> getUsrTranscriptByTerm(int year, int term) {
	String postData = String.format("ddlXN=%s&ddlXQ=%s&txtQSCJ=0&txtZZCJ=100&Button1=%s",getCalAcademicByGrade(year),term,"%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF");
		return getUsrTranscript(postData);
	}

	private String getCalAcademicByGrade(int grade) {
		int entranceYear = Integer.parseInt(studentId.substring(0, 4));
		return String.format("%s-%s", entranceYear + grade - 1, entranceYear
				+ grade);
	}

	/**
	 * 学生成绩单，按学年 
	 * @param year 大一：1,大二：2，大三：3
	 * @return
	 */
	public List<Jw2005Score> getUsrTranscriptByYear(int year) {
		String postData = String.format("ddlXN=%s&txtQSCJ=0&txtZZCJ=100&Button5=%s",getCalAcademicByGrade(year),"%B0%B4%D1%A7%C4%EA%B2%E9%D1%AF");
        return getUsrTranscript(postData);
	}

	/**
	 * 学生课程表
	 * @return
	 * @throws java.io.IOException
	 */
	public Jw2005Schedule getUsrSchedule() {
		String content = getDataWithRefer(getUsrIndexPage(),
				getUsrSchedulePage());
        Jw2005Schedule schedule = new Jw2005Schedule();
		Document document = Jsoup.parse(content);
		String studentInfo = document.select("TR[class=trbg1]").text();
		for (Element select : document.select("table[id=Table1]").select(
				"td[align=Center]")) {
			String course = select.text();
			if (course.length() > 8) {
				String[] arr = course.split(" ");
				String courseName = arr[0];

				String day = arr[1].substring(0, 2);
				String time = arr[1].substring(2, arr[1].indexOf("{"));
				String teacher = arr[2];
				String classroom = "";
				if (arr.length == 4)
					classroom = arr[3];
                Course courseObj = new Course(courseName,time,teacher,classroom);
                if(day == null){
                    System.out.println();
                }
				List<Course> list =  schedule.getScheduleByDay(day);
				if (list == null) {
					list = new ArrayList<Course>();
                    schedule.addSchedule(day, list);
				}
                list.add(courseObj);
			}
		}
		studentDetail = studentInfo;
		return schedule;
	}

	public boolean loginJw2005(String studentId, String passwd,String veriCode) {
        this.studentId = studentId;
        this.passwd = passwd;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(this.loginUrl);
		for (Entry<String, String> val : ScutJW2005Consts.HEADER_JW2005_MAP
				.entrySet()) {
			post.setHeader(val.getKey(), val.getValue());
		}
		post.setHeader("Referer", this.loginUrl);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
		parms.add(new BasicNameValuePair("TextBox1", studentId));
		parms.add(new BasicNameValuePair("TextBox2", passwd));
		parms.add(new BasicNameValuePair("TextBox3", veriCode));
		parms.add(new BasicNameValuePair("__VIEWSTATE", viewStatus));
		parms.add(new BasicNameValuePair("RadioButtonList1", "%D1%A7%C9%FA"));
		parms.add(new BasicNameValuePair("Button1", ""));
		parms.add(new BasicNameValuePair("lbLanguage", ""));
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(parms, "gb2312");
			post.setEntity(entity);
			HttpResponse res = httpClient.execute(post);
			String resString = EntityUtils.toString(res.getEntity());
			if (!resString.contains("请登录")) {
				referUrl = resString.substring(resString.indexOf("'/(") + 1,
						resString.indexOf("'>here")).trim();

                String tmpContent = getDataWithRefer(loginUrl, getJw2005IndexPage()); // 转发到用户的jw2005欢迎页面
                studentName = Jsoup.parse(tmpContent).select("span[id=xhxm]")
                        .text().split(" ")[1].replace("同学", "").trim();
			    return true;
            }else {
                return false;
            }
		} catch (Exception e) {
			e.printStackTrace();
            return false;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	private String getDataWithReferPost(String postData, String refurl,
			String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		for (Entry<String, String> val : ScutJW2005Consts.HEADER_JW2005_MAP
				.entrySet()) {
			post.setHeader(val.getKey(), val.getValue());
		}
		post.setHeader("Referer", refurl);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
		for (String val : postData.split("&")) {
			String[] arr = val.split("=", 2);
			parms.add(new BasicNameValuePair(arr[0].trim(), arr[1].trim()));
		}
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(parms, "gb2312");
			post.setEntity(entity);
			HttpResponse res = httpClient.execute(post);
			return EntityUtils.toString(res.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return "";
	}

	private String getDataWithRefer(String refurl, String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		for (Entry<String, String> val : ScutJW2005Consts.HEADER_JW2005_MAP
				.entrySet()) {
			get.setHeader(val.getKey(), val.getValue());
		}

		get.setHeader("Referer", refurl);
		get.setHeader("Host", host);

		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			HttpResponse res = httpClient.execute(get);
			String resStr = EntityUtils.toString(res.getEntity());
			Document doc = Jsoup.parse(resStr);
			Elements select = doc.select("input[name=__VIEWSTATE]");
			this.viewStatus = select.attr("value");
			return resStr;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return "";
	}

	private void getVeriCodePic(String tagCodeUrl) throws IOException {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(getConent(tagCodeUrl));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		BufferedImage raw = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		for (int x = bi.getMinX(); x < width; x++) {
			for (int y = bi.getMinY(); y < height; y++) {
				int pixel = bi.getRGB(x, y);
				if (pixel == -16777063 || pixel == -13421620) {
					raw.setRGB(x, y, 0x000000);
				} else {
					raw.setRGB(x, y, -1);
				}
			}
		}
		for (int x = raw.getMinX() +1 ; x < width -1; x++) {
			for (int y = raw.getMinY() +1 ; y < height -1 ; y++) {
				int pixel = raw.getRGB(x, y);
				if( pixel == -16777216){
					int count = 0;
					if(raw.getRGB(x - 1, y) == -1) count++; 
					if(raw.getRGB(x + 1 , y) == -1) count++; 
					if(raw.getRGB(x, y - 1) == -1) count++; 
					if(raw.getRGB(x, y + 1) == -1) count++;
//					if(raw.getRGB(x + 1, y +1) == -1) count++; 
//					if(raw.getRGB(x - 1 , y -1) == -1) count++; 
//					if(raw.getRGB(x + 1, y - 1) == -1) count++; 
//					if(raw.getRGB(x - 1 , y + 1) == -1) count++;
					if(count == 4) raw.setRGB(x, y, -1);
				}
			}
		}
		ImageIO.write(raw, "gif", new File("d:\\veriCode" + ".gif"));
	}

	private InputStream getConent(String str) {
		int retry = 3;
		while (retry > 0) {
			try {
				HttpGet get = new HttpGet(str);
				HttpResponse res = new DefaultHttpClient().execute(get);
				return res.getEntity().getContent();
			} catch (Exception e) {
				e.printStackTrace();
				retry--;
			}
		}
		return null;
	}

	private String getJw2005IndexPage() {
		return "http://" + host + referUrl;
	}

	private String getUsrIndexPage() {
		return String.format(JW2005_USR_INDEX_PAGE, host, tagCode, studentId);
	}

	private String getUsrSchedulePage() {
		return String.format(JW2005_USR_SCHEDULE_PAGE, host, tagCode,
				studentId, studentName);
	}

	private String getUserTranscriptPage() {
		return String.format(JW2005_USR_TRANSCRIPT_PAGE, host, tagCode,
				studentId, studentName);
	}

	@SuppressWarnings("unused")
	private String getStudentDetail() {
		return studentDetail;
	}

	@Override
	public String toString() {
		return String.format(
				"viewStatus : %s\nloginUrl : : %s\nhost : %s\nveriCodeUrl : %s\ntagCode"
						+ " : %s\nstudentId:%s\nstudentName:%s\n", viewStatus,
				loginUrl, host, veriCodeUrl, tagCode, studentId, studentName);
	}


    public void setHost(String host) {
        this.host = host;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public void setStudentDetail(String studentDetail) {
        this.studentDetail = studentDetail;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public void setTranStatus(boolean tranStatus) {
        this.tranStatus = tranStatus;
    }

    public void setVeriCodeUrl(String veriCodeUrl) {
        this.veriCodeUrl = veriCodeUrl;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    public String getHost() {
        return host;
    }

    public static String getJw2005UsrIndexPage() {
        return JW2005_USR_INDEX_PAGE;
    }

    public static String getJw2005UsrSchedulePage() {
        return JW2005_USR_SCHEDULE_PAGE;
    }

    public static String getJw2005UsrTranscriptPage() {
        return JW2005_USR_TRANSCRIPT_PAGE;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTagCode() {
        return tagCode;
    }

    public String getVeriCodeUrl() {
        return veriCodeUrl;
    }

    public String getViewStatus() {
        return viewStatus;
    }

}
