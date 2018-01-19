package com.wizinno.pathcloud.wechat.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by LiuMei on 2017-05-24.
 */
public class Config {

    protected static final Log logger = LogFactory.getLog(Config.class);
    protected static final Properties p = new Properties();

    private Config(){

    }

    /**
     * 静态读入属性文件到Properties p变量中
     */
    protected static void init(String propertyFileName) {
        InputStream in = null;
        try {
            in = Config.class.getClassLoader().getResourceAsStream(propertyFileName);
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
            logger.error("load " + propertyFileName + " into Constants error!");
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + propertyFileName + " error!");
                }
            }
        }
    }

    /**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key          property key.
     * @param defaultValue 当使用property key在properties中取不到值时的默认值.
     */
    protected static String getProperty(String key, String defaultValue) {
        return p.getProperty(key, defaultValue);
    }

    static {
        init("config.properties");
    }

    public final static String jwtId =getProperty("jwt.id", "jwt");

    public final static String jwtSecret = getProperty("jwt.secret", "7786df7fc3a34e26a61c034d5ec8245d");

    //6小时
    public final static Integer jwtExpire = Integer.parseInt(getProperty("jwt.expire", "21600000"));

    public final static String pathcloudBaseUrl= getProperty("pathcloud.api.base.url", "https://114.115.211.205/psts/api/");

    public final static String emailUsername= getProperty("email.username", null);
    public final static String emailPassword= getProperty("email.password", null);
    public final static String emailSmtp= getProperty("email.smtpHost", null);
    public final static String emailToUsername= getProperty("email.to.username", null);

}
