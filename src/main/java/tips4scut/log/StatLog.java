package tips4scut.log;

import org.apache.log4j.Logger;

/**
 * User: Administrator
 * Date: 13-10-24
 * Time: 下午2:50
 */
public class StatLog {
    protected static final Logger logger = Logger.getLogger(StatLog.class);

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
