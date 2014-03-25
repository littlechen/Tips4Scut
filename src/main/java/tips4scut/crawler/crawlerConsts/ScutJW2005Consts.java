package tips4scut.crawler.crawlerConsts;

import java.util.HashMap;
import java.util.Map;

public class ScutJW2005Consts {//&hidLanguage=&ddlXN=&ddlXQ=&ddl_kcxz=&__EVENTTARGET=&__EVENTARGUMENT=

	public static final String JW2005_URL = "http://jw2005.scuteo.com/";
	public static final String JW2005_TRANSSCRIPT_POST = "ddlXQ=&txtQSCJ=0&txtZZCJ=100&Button2=%s";
	
	@SuppressWarnings("serial")
	public static final Map<String, String> HEADER_JW2005_MAP = new HashMap<String, String>(){
		{
			put("Accept", "text/html, application/xhtml+xml, */*");
			put("Accept-Language","zh-CN");
			put("Accept-Encoding","gzip, deflate");
			put("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
			put("Connection", "Keep-Alive");
			put("Content-Type", "application/x-www-form-urlencoded");
		}
	};
	
}
