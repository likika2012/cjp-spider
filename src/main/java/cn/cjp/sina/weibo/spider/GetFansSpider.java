package cn.cjp.sina.weibo.spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.log4j.Logger;

import cn.cjp.base.utils.FileUtil;
import cn.cjp.sina.weibo.core.SinaWeiboHttpClientAccessCore;
import cn.cjp.sina.weibo.domain.UserDomain;
import cn.cjp.sina.weibo.proxy.bean.HttpProxyBean;
import cn.cjp.sina.weibo.proxy.service.HttpProxyService;

public class GetFansSpider implements Runnable {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GetFansSpider.class);

	SinaWeiboHttpClientAccessCore accessCore = null;
	private HttpHost httpHost = null;
	/**
	 * 已抓取的Uid队列
	 */
	private static Set<String> grabbedUidSet = new HashSet<String>();
	/**
	 * 等待抓取的Uid队列
	 */
	private static List<String> waitingUidList = new ArrayList<String>();
	/**
	 * 正在抓取的Uid队列
	 */
	private static Set<String> grabbingUidSet = new HashSet<String>();

	private int grabPageNum = 10;

	/**
	 * 是否存储到文件
	 */
	private static String savedFileDir = "";

	/**
	 * Instantiation DefaultSpider
	 */
	public GetFansSpider() {
	}

	/**
	 * Instantiation ProxySpider
	 * @param httpProxyBean
	 *            an object of {@link HttpProxyBean}
	 */
	public GetFansSpider(HttpProxyBean httpProxyBean) {
		httpHost = new HttpHost(httpProxyBean.getHost(),
				httpProxyBean.getPort());
	}

	/**
	 * setting acount for logging on to sina weibo
	 * 
	 * @param username
	 * @param password
	 */
	public void setAcount(String username, String password) {

		accessCore = SinaWeiboHttpClientAccessCore.getInstance(username,
				password);
		// 登录成功，才可以加上代理进行后续操作
		if (this.httpHost != null) {
			HttpProxyBean proxyBean = HttpProxyService.getRandomProxy();
			HttpHost httpHost = new HttpHost(proxyBean.getHost(),
					proxyBean.getPort());
			accessCore.httpClient.getParams().setParameter(
					ConnRoutePNames.DEFAULT_PROXY, httpHost);
			logger.info("setting proxy ：" + httpHost.getHostName() + ":"
					+ httpHost.getPort());
		}
	}

	/**
	 * 初始化待抓取队列
	 * 
	 * @param uid
	 *            （UID for test：5574133962）
	 */
	public void initWaitingUidList(String uid) {
		waitingUidList.add(uid);
	}

	/**
	 * 初始化待抓取队列
	 * 
	 * @param uids
	 *            （UID for test：5574133962）
	 */
	public void initWaitingUidList(List<String> uids) {
		waitingUidList.addAll(uids);
	}

	/**
	 * 设置存储路径，不设置则不存
	 * 
	 * @param dir
	 */
	@SuppressWarnings("static-access")
	public void setSavedFileDir(String dir) {
		this.savedFileDir = dir;
	}

	public void run() {
		/**
		 * 验证是否登录成功
		 */
		if (accessCore == null) {
			logger.error("未登录或登录失败", new Throwable(
					"Please call setAcount for login"));
			return;
		}
		synchronized (logger) {
			logger.info("Thread start...\n");
		}

		while (true) {
			toGrab();
			synchronized (logger) {
				logger.info("======================");
				logger.info("等待队列 : " + waitingUidList.size());
				logger.info("正在抓取队列 : " + grabbingUidSet.size());
				logger.info("已抓取队列 : " + grabbedUidSet.size());
				logger.info("======================");
			}
		}
	}

	/**
	 * 从待抓取Uid队列中取出一个Uid
	 * 
	 * @return
	 */
	private synchronized String borrowOne() {
		// while 比 if 更好点
		while (waitingUidList.size() == 0) {
			try {
				logger.info("wait 等待队列大小：" + waitingUidList.size());
				this.wait();
				logger.info("awake 等待队列大小：" + waitingUidList.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String borrowingUid = waitingUidList.remove(0);
		grabbingUidSet.add(borrowingUid);
		return borrowingUid;
	}

	/**
	 * 开始抓取
	 */
	private void toGrab() {
		String grabbingUid = borrowOne();
		for (int i = 1; i <= grabPageNum; i++) {
			// 抓取,解析Json
			List<UserDomain> fansList = accessCore.requestFansByUid(
					grabbingUid, i);
			if (fansList.size() == 0) {
				break;
			}
			// 新增Uid存入 待抓取 队列
			int addingCount = 0;
			for (UserDomain fan : fansList) {
				String uid = fan.getId() + "";
				if (!grabbedUidSet.contains(uid)
						&& !grabbingUidSet.contains(uid)) {
					addingCount++;
					addOne(uid);
				}
			}
			logger.info("新增Uid个数 : " + addingCount);
			// 保存数据
			saveGrabbedFans(grabbingUid, fansList);
			// 从 正在抓取队列 删除当前Uid，并存入已抓取队列
			grabbingUidSet.remove(grabbingUid);
			grabbedUidSet.add(grabbingUid);

		}
	}

	private synchronized void addOne(String uid) {
		waitingUidList.add(uid);
		this.notify();
	}

	/**
	 * 保存数据
	 * 
	 * @param grabbingUid
	 *            正在抓取的Uid
	 * @param fansList
	 *            抓取到的fans列表
	 */
	private void saveGrabbedFans(String grabbingUid, List<UserDomain> fansList) {

		if (savedFileDir == null || savedFileDir == "") {
			return;
		}
		String data = "";
		for (UserDomain fan : fansList) {
			data += fan.toString() + "\r\n";
		}

		FileUtil.write(data, savedFileDir+ "/" + grabbingUid + ".fans", true);
	}

}
