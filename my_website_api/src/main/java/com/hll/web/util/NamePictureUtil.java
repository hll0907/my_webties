package com.hll.web.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class NamePictureUtil {

	public static String createNamePic(String name, String outPath, String outName) throws IOException {
		int width = 100;
		int height = 100;
		int nameLen = name.length();
		String nameWritten;
		// 如果用户输入的昵称少于等于3个字符，不用截取
		if (nameLen <= 2) {
			nameWritten = name;
		} else {
			// 如果用户输入的姓名大于等于3个字符，截取前两位
			String first = name.substring(0, 1);
			if (isChinese(first)) {
				// 截取前两位汉字
				nameWritten = name.substring(0, 2);
			} else {
				// 截取前面的两个英文字母
				nameWritten = name.substring(0, 2).toUpperCase();
			}
		}

		String filename = outPath + File.separator + outName + ".jpg";
		File file = new File(filename);
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = (Graphics2D) bImage.getGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2d.setBackground(getRandomColor());
		graphics2d.clearRect(0, 0, width, height);
		graphics2d.setPaint(Color.WHITE);
		Font font = null;
		// 两个字以上
		if (nameWritten.length() >= 2) {
			font = new Font("微软雅黑", Font.PLAIN, 30);
			graphics2d.setFont(font);
			String firstWritten = nameWritten.substring(0, 1);
			String secondWritten = nameWritten.substring(1, 2);
			// 两个中文 如 言曌
			if (isChinese(firstWritten) && isChinese(secondWritten)) {
				graphics2d.drawString(nameWritten, 20, 60);
			} else if (isChinese(firstWritten) && !isChinese(secondWritten)) {
				// 首中次英 如 罗Q
				graphics2d.drawString(nameWritten, 27, 60);
				// 首英,如 AB
			} else {
				nameWritten = nameWritten.substring(0, 1);
			}
		}
		// 一个字
		if (nameWritten.length() == 1) {
			// 中文
			if (isChinese(nameWritten)) {
				font = new Font("微软雅黑", Font.PLAIN, 50);
				graphics2d.setFont(font);
				graphics2d.drawString(nameWritten, 25, 70);
			} else {
				// 英文
				font = new Font("微软雅黑", Font.PLAIN, 55);
				graphics2d.setFont(font);
				graphics2d.drawString(nameWritten.toUpperCase(), 33, 67);
			}
		}
		BufferedImage rounded = makeRoundedCorner(bImage, 99);
		ImageIO.write(rounded, "png", file);
		return filename;
	}

	/**
	 * 图片做圆角处理
	 * 
	 * @param bImage
	 * @param cornerRadius
	 * @return
	 */
	private static BufferedImage makeRoundedCorner(BufferedImage bImage, int cornerRadius) {
		int w = bImage.getWidth();
		int h = bImage.getHeight();
		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = output.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(bImage, 0, 0, null);
		g2.dispose();
		return output;
	}

	/**
	 * 获得随机颜色
	 * 
	 * @return
	 */
	private static Color getRandomColor() {
		String[] beautifulColors = new String[] { "232,221,203", "205,179,128", "3,101,100", "3,54,73", "3,22,52",
				"237,222,139", "251,178,23", "96,143,159", "1,77,103", "254,67,101", "252,157,154", "249,205,173",
				"200,200,169", "131,175,155", "229,187,129", "161,23,21", "34,8,7", "118,77,57", "17,63,61", "60,79,57",
				"95,92,51", "179,214,110", "248,147,29", "227,160,93", "178,190,126", "114,111,238", "56,13,49",
				"89,61,67", "250,218,141", "3,38,58", "179,168,150", "222,125,44", "20,68,106", "130,57,53",
				"137,190,178", "201,186,131", "222,211,140", "222,156,83", "23,44,60", "39,72,98", "153,80,84",
				"217,104,49", "230,179,61", "174,221,129", "107,194,53", "6,128,67", "38,157,128", "178,200,187",
				"69,137,148", "117,121,71", "114,83,52", "87,105,60", "82,75,46", "171,92,37", "100,107,48", "98,65,24",
				"54,37,17", "137,157,192", "250,227,113", "29,131,8", "220,87,18", "29,191,151", "35,235,185",
				"213,26,33", "160,191,124", "101,147,74", "64,116,52", "255,150,128", "255,94,72", "38,188,213",
				"167,220,224", "1,165,175", "179,214,110", "248,147,29", "230,155,3", "209,73,78", "62,188,202",
				"224,160,158", "161,47,47", "0,90,171", "107,194,53", "174,221,129", "6,128,67", "38,157,128",
				"201,138,131", "220,162,151", "137,157,192", "175,215,237", "92,167,186", "255,66,93", "147,224,255",
				"247,68,97", "185,227,217" };
		int len = beautifulColors.length;
		Random random = new Random();
		String[] color = beautifulColors[random.nextInt(len)].split(",");
		return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
	}

	/**
	 * 判断字符串是否为中文
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isChinese(String str) {
		String regEx = "[\\u4e00-\\u9fa5]+";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find())
			return true;
		else
			return false;
	}

	/*public static void main(String[] args) throws IOException {
		String name = "A大言曌";
		createNamePic(name,
				"E:/workplace2/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/my_website_api",
				CommUtil.genImageName() + name);
	}*/
}
