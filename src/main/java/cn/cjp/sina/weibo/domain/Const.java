package cn.cjp.sina.weibo.domain;

import java.util.HashMap;
import java.util.Map;

public class Const {

	/**
	 * @deprecated use {@link #HEADER_FOR_LOGIN} OR {@link #HEADER_DEFAULT}
	 *             instead
	 */
	@SuppressWarnings("serial")
	public static final Map<String, String> header = new HashMap<String, String>() {
		{
			put("Referer",
					"https://passport.sina.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F");
			put("User-Agent",
					"Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			// put("User-Agent",
			// "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1C28 Safari/419.3");
			// put("User-Agent",
			// "HUAWEI G610-U00_4.2.1_weibo_4.6.1_android");
			put("Host", "passport.sina.cn");
		}
	};
	/**
	 * HTTP header for login
	 */
	public static final Map<String, String> HEADER_FOR_LOGIN = new HashMap<String, String>() {
		private static final long serialVersionUID = -8947068432793000113L;
		{
//			put("Accept", "application/json, text/javascript, */*; q=0.01");
//			put("Accept-Encoding", "gzip, deflate");
//			put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//			put("X-Requested-With", "XMLHttpRequest");
			put("Referer",
					"https://passport.sina.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F");
			put("User-Agent",
					"Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			put("Host", "passport.sina.cn");
		}
	};
	/**
	 * HTTP header for request except for login
	 */
	public static final Map<String, String> HEADER_DEFAULT = new HashMap<String, String>() {
		private static final long serialVersionUID = 4178503057499842033L;
		{
//			put("Accept", "application/json, text/javascript, */*; q=0.01");
//			put("Accept-Encoding", "gzip, deflate");
//			put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//			put("X-Requested-With", "XMLHttpRequest");
			put("User-Agent",
					"Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			put("Host", "m.weibo.cn");
			put("Referer", "http://m.weibo.cn");
		}
	};

	/**
	 * 获取模糊位置<br>
	 * Method : GET<br>
	 * 参数<br>
	 * page : 页数<br>
	 */
	public static final String GEO_LOCATION_URL = "http://m.weibo.cn/interestnew/poi/getNearbyPois?offset=0&getLocation=1&lat=34.7466&long=113.625368&format=cards&page={page}";

	/**
	 * 用于登录 <br>
	 * Method : POST
	 */
	public static final String LOGIN_URL = "https://passport.sina.cn/sso/login";
	/**
	 * 获取附近位置<br>
	 * lat : 纬度 <br>
	 * lon : 经度 <br>
	 * q：查询条件（关键词）（自己添加）<br>
	 */
	public static final String GET_NEAR_BY_POIS = "http://m.weibo.cn/interestnew/poi/getNearbyPois?offset=0&getLocation=1&lat={lat}&long={lon}&format=cards";
	/**
	 * 这种发布微博的方式是最简单的，登录的cookies参数在请求中设置到cookies里面
	 */
	public static final String B_PUB_WEIBO = "http://m.weibo.cn/mblogDeal/addAMblog";
	/**
	 * 这种发布微博的方式，是要把登录cookie gsid放到url后面<br>
	 * s : 校验参数
	 * 
	 */
	public static final String M_PUB_WEIBO = "http://api.weibo.cn/2/statuses/send?"; // ?gsid=4uaR1a293RPiHX2XyWmf1b5k4dJ
	/**
	 * 关注列表<br>
	 * 参数：<br>
	 * uid <br>
	 * page <br>
	 */
	public static final String FOLLOWERS_URL = "http://m.weibo.cn/page/json?containerid=100505{uid}_-_FOLLOWERS&page={page}";
	/**
	 * 粉丝列表<br>
	 * 参数：<br>
	 * uid <br>
	 * page <br>
	 */
	public static final String FANS_URL = "http://m.weibo.cn/page/json?containerid=100505{uid}_-_FANS&page={page}";

	/**
	 * 用于发微博时，预上传图片
	 */
	public static final String ADD_PIC_URL = "http://m.weibo.cn/mblogDeal/addPic";

	public static final String LOGIN_SUCCESS = "success";
	public static final String LOGIN_FAIL = "fail";

	/**
	 * 登录用户的首页，显示关注的用户的微博 <br>
	 * Method : GET <br>
	 * 额外参数 <br>
	 * page : 可以不加，但不可以为空，最小为1 <br>
	 * next_cursor : 可以不加，但不可以为空
	 */
	public static final String HOME_WEIBO_URL = "http://m.weibo.cn/index/feed?format=cards";

	/**
	 * 获取当前登录用户的个人消息（包括@，评论，赞等）<br>
	 * 参数：<br>
	 * page
	 */
	public static final String HOME_MSG_URL = "http://m.weibo.cn/msg/index?format=cards";
	/**
	 * 某用户的微博列表
	 */
	public static final String WEIBOs_OF_USER_URL = "http://m.weibo.cn/page/json?containerid=100505{uid}_-_WEIBO_SECOND_PROFILE_WEIBO&page={page}";
	/**
	 * 微博评论
	 */
	public static final String PLs_OF_WEIBO_URL = "http://m.weibo.cn/{uid}/{mid}/getRCList?format=cards&type=pl&page={page}";
	/**
	 * 微博转发
	 */
	public static final String ZFs_OF_WEIBO_URL = "http://m.weibo.cn/{uid}/{mid}/getRCList?format=cards&type=zf&page={page}";
	/**
	 * 微博赞
	 */
	public static final String ATTITUDEs_OF_WEIBO_URL = "http://m.weibo.cn/attitudesDeal/getAttitudeList?id={mid}&format=cards&page={page}";

	/**
	 * 获取'@'列表
	 */
	public static final String AT_LIST_URL = "http://m.weibo.cn/attention/getAtList";

	/**
	 * 根据关键词获取'@'列表<br>
	 * 参数：<br>
	 * keyword <br>
	 * page <br>
	 */
	public static final String AT_LIST_BY_KEY_URL = "http://m.weibo.cn/attention/getAttentionList?keyword={keyword}&page={page}&format=cards";

	/**
	 * 获取topic列表
	 */
	public static final String TOPIC_LIST_URL = "http://m.weibo.cn/searchs/searchTopic";

	/**
	 * 根据keyword获取topic列表<br>
	 * 参数： <br>
	 * keyword <br>
	 */
	public static final String TOPIC_LIST_BY_KEY_URL = "http://m.weibo.cn/searchs/searchTopicByKeyword?q={keyword}";

	/**
	 * 获取当前登录用户的个人资料
	 */
	public static final String GET_ME_URL = "https://m.weibo.cn/home/me?format=cards";

}
