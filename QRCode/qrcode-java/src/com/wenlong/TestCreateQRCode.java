package com.wenlong;

import java.io.File;

import com.google.zxing.Result;
import com.wenlong.QRCode.QRCodeUtils;
import com.wenlong.zxing.ZXingQRCodeUtil;

public class TestCreateQRCode {
	public static void main(String[] args) {
		String pathString = null;
		try {
			pathString = new File("").getCanonicalPath().toString()+"\\img\\img.png";
			//CreateQRCodeUtil.createQRCode(300, 300, "www.imooc.com",pathString);
			QRCodeUtils.CreateRQCode("www.imooc.com", pathString);
			Result result = ZXingQRCodeUtil.ReadQRCode(new File("").getCanonicalPath()+"\\img\\img.png");
			System.out.println(result.getText()+"\n"+result.getBarcodeFormat()+"\n"+result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
