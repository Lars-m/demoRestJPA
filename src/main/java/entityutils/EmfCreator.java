package entityutils;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmfCreator {

    public static String developerDB = "jdbc:mysql://localhost:3306/demo?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static String getValFromEnvironment(String key, String defaultVal){
        String value = System.getenv(key);
        return value != null ? value: defaultVal;
    }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return getEntityManagerFactory("", "", "");
    }

    public static EntityManagerFactory getEntityManagerFactory( String connection_str, String usr, String pw) {
        String db = !connection_str.equals("") ? connection_str : developerDB;
        Properties props = new Properties();
        
        String user = getValFromEnvironment("USER", usr);
        String password = getValFromEnvironment("PASSWORD",pw);
        String connectionStr = getValFromEnvironment("CONNECTION_STR",db);
        String puName =getValFromEnvironment("PU", "pu"); //"pu" is the name of the default persistenceUnit
        
        props.setProperty("javax.persistence.jdbc.user", user);
        props.setProperty("javax.persistence.jdbc.password", password);
        props.setProperty("javax.persistence.jdbc.url", connectionStr);
      
        return Persistence.createEntityManagerFactory(puName, props);
    }
}
