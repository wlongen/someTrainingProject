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
 * zxing4-7-8.jar ���ɺͽ�����ά��
 * @author twl
 *
 */
public class ZXingQRCodeUtil {


	/**
	 * describe ����ZXing ���ɶ�ά��
	 * @param width ��ά����
	 * @param height ��ά��߶�
	 * @param contentString ��ά��������Ϣ
	 * @param path ��ά�뱣��·��
	 */
	public static void createQRCode(int width, int height, String contentString, String path) {

		String formatString = "png";
		// �����ά�����
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
	 * ����ZXing ������ά����Ϣ
	 * @param path ��ά���·��
	 * @return
	 */
	public static Result ReadQRCode(String path) {
		Result result = null;
		try {
			MultiFormatReader multiFormatReader = new MultiFormatReader();
			// ��ά�����
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
