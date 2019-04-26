package com.wenlong.QRCode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * 利用Qrcode.jar 生成二维码 ,解析的包下载不下来，可以直接用ZXing的解析方法
 * @author twl
 *
 */
public class QRCodeUtils {
	
	/**
	 * 利用Qrcode.jar 生成二维码
	 * @param qrData 二维码内容信息
	 * @param path	保存路径
	 * @throws Exception
	 */
	public static void CreateRQCode(String qrData,String path) throws Exception {
		Qrcode x=new Qrcode();
		//纠错等级
		x.setQrcodeErrorCorrect('M');
		//A代表a-Z，B代表其他字符
		x.setQrcodeEncodeMode('B');
		//版本
		x.setQrcodeVersion(7);
		int width = 67 + 12*(7-1);
		int height = 67 + 12*(7-1);
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D gs = bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, width, height);
		
		//偏移量
		int pixoff = 2;
		
		byte[] d =qrData.getBytes("UTF-8");
		if (d.length>0 && d.length <120){
		    boolean[][] s = x.calQrcode(d);

		    for (int i=0;i<s.length;i++){
			for (int j=0;j<s.length;j++){
			    if (s[j][i]) {
				gs.fillRect(j*3+pixoff,i*3+pixoff,3,3);
			    }
			}
		    }
		}
		gs.dispose();
		bufferedImage.flush();
		
		ImageIO.write(bufferedImage, "png", new File(path));
	}
}
