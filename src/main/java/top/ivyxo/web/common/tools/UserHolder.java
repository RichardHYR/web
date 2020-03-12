package top.ivyxo.web.common.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * 临时变量存储 Richard - 2019-12-4 17:43:49
 * @author HYR
 */
public class UserHolder {

	public static String USER_ID_KEY ="USER_ID";
	public static String USER_SESSION_KEY ="USER_SESSION";

	private static final ThreadLocal<Map<String, Object>> USER = new ThreadLocal<>();
	
	public static Object get(String key){
		Map<String, Object> cache = USER.get();
		if(cache == null) {
			return null;
		}
		
		return cache.get(key);
	}
	
	public static void set(String key, Object value) {
		Map<String, Object> cache = USER.get();
		if(cache == null) {
			cache = new HashMap<String, Object>();
			USER.set(cache);
		}
		
		cache.put(key, value);
	}
	
	public static void clear() {
		USER.set(null);
	}

}
