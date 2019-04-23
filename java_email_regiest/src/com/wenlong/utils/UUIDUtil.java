package com.wenlong.utils;

import java.util.UUID;

/**
 * Éú³ÉËæ»ú×Ö·û´®
 * @author wenlong
 *
 */
public class UUIDUtil {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
