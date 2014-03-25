package tips4scut.crawler.crawlerConsts;

import java.util.HashMap;
import java.util.Map;

public class ScutLibraryConsts {

	public static final String LOGIN_CHAR = "";
	public static final String LIBRARY_SERVER_URL = "http://202.38.232.10/opac/servlet/opac.go";
	public static final String LIBRARY_INDEX_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=mylibrary.index";
	public static final String LIBRARY_LOAN_URL = "http://202.38.232.10/opac/servlet/opac.go?cmdACT=loan.list";
	public static final String LIBRARY_HOST = "202.38.232.10";
	
	public static final String LIBRARY_BOOK_STATUS_ATTR = "馆址#馆藏地#索书号#条码号#卷期信息#书刊状态#应还日期";
	public static final String LIBRARY_BOOK_INFO = "book_info";
	public static final String LIBRARY_BOOK_STATUS = "book_status";
	public static final String LIBRARY_BOOK_NUM_STATUS = "book_num_status";
	public static final String USER_LOAD_BOOK_STATUS = "书名|地方|借阅时间|到期时间|剩下借阅次数|是否过期|续借地址";
	
	public static final String LIBRARY_SEARCH_POST_DATA_STR = "cmdACT=simple.list&RDID=ANONYMOUS&PAGE=%s&ORGLIB=SCUT&FIELD1=TITLE&VAL1=%s&MODE=FRONT&PAGESIZE=%s";

	
	@SuppressWarnings("serial")
	public static final Map<String, String> LOGIN_LIBRARY_POST_BASE_DATA = new HashMap<String, String>(){
		{
			put("cmdACT","mylibrary.login");
			put("user_login","%E7%99%BB%E5%BD%95");
			put("libcode","");
			put("method", "mylib");
		}
	};
	
	
	
	
;}
