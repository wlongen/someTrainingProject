package com.wenlong.zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * zxing4-7-8.jar 生成和解析二维码
 * @author twl
 *
 */
public class ZXingQRCodeUtil {


	/**
	 * describe 利用ZXing 生成二维码
	 * @param width 二维码宽度
	 * @param height 二维码高度
	 * @param contentString 二维码内容信息
	 * @param path 二维码保存路径
	 */
	public static void createQRCode(int width, int height, String contentString, String path) {

		String formatString = "png";
		// 定义二维码参数
		HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);

		try {
			Path file = new File(path).toPath();
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contentString, BarcodeFormat.QR_CODE, width, height,
					hints);
			MatrixToImageWriter.writeToPath(bitMatrix, formatString, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 利用ZXing 解析二维码信息
	 * @param path 二维码的路径
	 * @return
	 */
	public static Result ReadQRCode(String path) {
		Result result = null;
		try {
			MultiFormatReader multiFormatReader = new MultiFormatReader();
			// 二维码参数
			HashMap<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

			File file = new File(path);
			BufferedImage image = ImageIO.read(file);
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
			result = multiFormatReader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
