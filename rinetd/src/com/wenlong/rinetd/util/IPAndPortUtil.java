package com.wenlong.rinetd.util;

import com.wenlong.rinetd.entity.IPAndPort;

/**
 * 提供判断转发地址是否相同
 * @author twl
 *
 */
public class IPAndPortUtil {
	/**
	 * 判断是否同一个转发配置
	 * @param iap1
	 * @param iap2
	 * @return
	 */
	public static boolean isTheSameForward(IPAndPort iap1, IPAndPort iap2) {
		boolean flag = false;
		if (iap1.getTargetIP().equals(iap2.getTargetIP()) && iap1.getTargetPort().equals(iap2.getTargetPort())
				&& iap1.getJumpIP().equals(iap2.getJumpIP()) && iap1.getJumpIP().equals(iap2.getJumpIP())) {
			flag = true;
		}
		return flag;
	}
}
