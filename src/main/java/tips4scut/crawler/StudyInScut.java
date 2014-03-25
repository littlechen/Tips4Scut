package tips4scut.crawler;

import java.io.IOException;


import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.map.util.JSONPObject;

public class StudyInScut {

	private static final String ACADEMIC_REPORT_PAGE = "http://www.scut.edu.cn/publish2/news/AcademicEvents/index.xml";
	
	public static void main(String [] args){
		getAcademicReportNotic();
		
	}

    /**
	 * 学术报告通知，XML数据
	 * @return
	 */
	public static String getAcademicReportNotic(){
		String content = "";
		try {
			content = new String(getPageConent(ACADEMIC_REPORT_PAGE).getBytes("ISO-8859-1"),"utf8");
        } catch (Exception e) {
			e.printStackTrace();
		}	
		return content;
	}

	private static String getPageConent(String url) {
		HttpClient client=new HttpClient();
		GetMethod getMethod = new GetMethod(url);
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
