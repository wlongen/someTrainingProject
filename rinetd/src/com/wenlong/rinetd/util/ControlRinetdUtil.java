package com.wenlong.rinetd.util;

import java.util.Map;
import com.wenlong.rinetd.entity.IPAndPort;

/**
 * 对rinetd的配置内容进行管理操作
 * 
 * @author twl
 *
 */
public class ControlRinetdUtil {
	private static SSHConnUtil ssh = null;

	/**
	 * 连接主机
	 * 
	 * @param ip
	 * @param username
	 * @param passwd
	 */
	public static void Conn(String ip, String username, String passwd) {
		ssh = new SSHConnUtil(ip, username, passwd);
	}

	/**
	 * 关闭连接
	 */
	public static void closeConn() {
		if (ssh != null) {
			ssh.closeSessionConn();
		}
	}

	/**
	 * 读取配置文件
	 * 
	 * @return
	 */
	public static Map<Integer, IPAndPort> seeConf() {
		Map<Integer, IPAndPort> map = ssh.readConfFile("/etc/rinetd.conf");
		return map;

	}

	/**
	 * 添加配置文件内容
	 * 
	 * @param iap 添加信息
	 */
	public static void addConf(IPAndPort iap) {
		String command = String.format("echo %s %s %s %s >>/etc/rinetd.conf", iap.getJumpIP(), iap.getJumpPort(),
				iap.getTargetIP(), iap.getTargetPort());
		ssh.execute(command);
	}

	/**
	 * 删除配置文件指定行内容
	 * 
	 * @param index 行数
	 */
	public static void deleteConf(int index) {
		Map<Integer, IPAndPort> confMap = ssh.readConfFile("/etc/rinetd.conf");
		confMap.remove(index);
		if (confMap.isEmpty()) {
			String command = "true > /etc/rinetd.conf";
			ssh.execute(command);
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			for (Map.Entry<Integer, IPAndPort> entry : confMap.entrySet()) {
				IPAndPort inputIAP = entry.getValue();
				stringBuilder.append(String.format("%s %s %s %s\\n", inputIAP.getJumpIP(), inputIAP.getJumpPort(),
						inputIAP.getTargetIP(), inputIAP.getTargetPort()));
			}
			stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
			String command = String.format("echo -e \"%s\" >/etc/rinetd.conf", stringBuilder.toString());
			ssh.execute(command);
		}
	}

	/**
	 * 修改指定行配置信息
	 * 
	 * @param index 要修改的行数
	 * @param iap   修改后的内容
	 */
	public static void modifyConf(int index, IPAndPort iap) {
		Map<Integer, IPAndPort> confMap = ssh.readConfFile("/etc/rinetd.conf");
		confMap.put(index, iap);
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<Integer, IPAndPort> entry : confMap.entrySet()) {
			IPAndPort inputIAP = entry.getValue();
			stringBuilder.append(String.format("%s %s %s %s\\n", inputIAP.getJumpIP(), inputIAP.getJumpPort(),
					inputIAP.getTargetIP(), inputIAP.getTargetPort()));
		}
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		String command = String.format("echo -e \"%s\" >/etc/rinetd.conf", stringBuilder.toString());
		ssh.execute(command);
	}

	/**
	 * 重启rinetd服务
	 */
	public static void restart() {
		String[] command = { "pkill rinetd", "rinetd -c /etc/rinetd.conf" };
		ssh.shell(command);
	}
}
