package com.wenlong.utils;

import java.util.UUID;

/**
 * ��������ַ���
 * @author wenlong
 *
 */
public class UUIDUtil {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
