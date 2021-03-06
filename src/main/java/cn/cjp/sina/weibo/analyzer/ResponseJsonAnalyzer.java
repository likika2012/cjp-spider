package cn.cjp.sina.weibo.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.cjp.sina.weibo.domain.StatusPubWeibo;
import cn.cjp.sina.weibo.domain.UserDomain;

/**
 * 解析请求返回的JSON/HTML数据
 * @author REAL
 *
 */
public class ResponseJsonAnalyzer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ResponseJsonAnalyzer.class);

	/**
	 * 解析发表微博返回的Json
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static StatusPubWeibo analyzePubWeibo(String json) {
		StatusPubWeibo statusPubWeibo = new StatusPubWeibo();

		try {
			JSONObject jsonObject = new JSONObject(json);
			statusPubWeibo.setOk(jsonObject.getInt("ok"));
			if (statusPubWeibo.getOk() == 1) {
				statusPubWeibo.setId(jsonObject.getString("id"));
			}
			statusPubWeibo.setMsg(jsonObject.getString("msg"));
		} catch (JSONException e) {
			logger.error("JSON转换失败 : " + json, e);

		}
		return statusPubWeibo;
	}
	/**
	 * 解析获取关注列表Json
	 * @param json
	 * @return
	 */
	public static List<UserDomain> analyzerGetFollows(String json) {
		return analyzerGetFans(json);
	}

	/**
	 * 解析获取粉丝列表Json
	 * @param json
	 * @return
	 */
	public static List<UserDomain> analyzerGetFans(String json) {
		List<UserDomain> userDomains = new ArrayList<UserDomain>();
		
		if(StringUtils.isBlank(json)){
			return userDomains;
		}

		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray cardsJsonArray = jsonObject.getJSONArray("cards");
			if (cardsJsonArray.getJSONObject(0).isNull("card_group")) {
				return userDomains;
			}
			JSONArray cardGroupJsonArray = cardsJsonArray.getJSONObject(0)
					.getJSONArray("card_group");

			for (int i = 0; i < cardGroupJsonArray.length(); i++) {
				JSONObject userJsonObject = cardGroupJsonArray.getJSONObject(i)
						.getJSONObject("user");
				UserDomain userDomain = toEntity(userJsonObject.toString());
				if (null != userDomain) {
					userDomains.add(userDomain);
				}
			}

		} catch (JSONException e) {
			logger.error("JSON转换错误：" + json, e);
		}

		return userDomains;
	}

	private static UserDomain toEntity(String json) {
		UserDomain userDomain = new UserDomain();
		try {
			JSONObject jsonObject = new JSONObject(json);
			if (!jsonObject.isNull("created_at")) {
				userDomain.setCreatedAt(get(jsonObject, "created_at")
						.toString());
			}
			userDomain.setDesc1(get(jsonObject, "desc1").toString());
			userDomain.setDesc2(get(jsonObject, "desc2").toString());
			userDomain
					.setDescription(get(jsonObject, "description").toString());
			userDomain.setFansNum(get(jsonObject, "fansNum").toString());
			userDomain.setGender(get(jsonObject, "gender").toString());
			userDomain.setId(Long.parseLong(get(jsonObject, "id").toString()));
			userDomain.setIsmember((Integer) get(jsonObject, "ismember"));
			userDomain.setMbtype(get(jsonObject, "mbtype").toString());
			userDomain.setProfileImageUrl(get(jsonObject, "profile_image_url")
					.toString());
			userDomain.setProfileUrl(get(jsonObject, "profile_url").toString());
			userDomain.setRemark(get(jsonObject, "remark").toString());
			userDomain.setScreenName(get(jsonObject, "screen_name").toString());
			userDomain.setStatuses_count((Integer) get(jsonObject,
					"statuses_count"));
			userDomain
					.setText(get(jsonObject, "text", String.class).toString());
			userDomain.setValid(get(jsonObject, "valid").toString());
			userDomain.setVerified((Boolean) get(jsonObject, "verified"));
			userDomain.setVerifiedReason(get(jsonObject, "verified_reason")
					.toString());
			userDomain.setVerifiedType((Integer) get(jsonObject,
					"verified_type"));
		} catch (JSONException e) {
			logger.error("JSON转换错误:" + json, e);
			userDomain = null;
		}

		return userDomain;
	}

	private static Object get(JSONObject jsonObject, String key) {
		try {
			return jsonObject.get(key);
		} catch (JSONException e) {
			return null;
		}
	}

	private static Object get(JSONObject jsonObject, String key, Class<?> clazz) {
		Object object = null;
		try {
			object = jsonObject.get(key);
		} catch (JSONException e) {
			if (clazz == String.class) {
				object = "";
			} else if (clazz == Integer.class) {
				object = 0;
			} else if (clazz == (Boolean.class)) {
				object = null;
			}
		}
		return object;
	}
}
