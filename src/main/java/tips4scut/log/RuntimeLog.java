package tips4scut.log;


//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import org.apache.log4j.Logger;

public class RuntimeLog {

	//protected static final Log logger = LogFactory.getLog(RuntimeLog.class);
    protected static final Logger logger = Logger.getLogger(RuntimeLog.class);
	
	public static void info(Object p){
		logger.info(p);
	}
	
	public static void error(Object p){
		logger.error(p);
	}
	
	public static void error(Object arg0, Throwable arg1){
		logger.error(arg0, arg1);
	}
	
	public static void warn(Object p){
		logger.warn(p);
	}
}
