package com.wenlong.rinetd.entity;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo, UIKeyboardInteractive {

	@Override
	public String getPassphrase() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean promptPassphrase(final String arg0) {
		return false;
	}

	@Override
	public boolean promptPassword(final String arg0) {
		return false;
	}

	@Override
	public boolean promptYesNo(final String arg0) {
		return true;
	}

	@Override
	public void showMessage(final String arg0) {
	}

	@Override
	public String[] promptKeyboardInteractive(String var1, String var2, String var3, String[] var4, boolean[] var5) {
		return null;
	}
}
