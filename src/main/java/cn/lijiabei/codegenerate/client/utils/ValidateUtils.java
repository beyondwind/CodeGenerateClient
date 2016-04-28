package cn.lijiabei.codegenerate.client.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.lijiabei.codegenerate.client.bo.GenerateInfo;

/**
 * @ClassName: ValidateUtils
 * @Description: 校验工具类
 * @author lijiabei
 * @date 2014-2-21 下午4:28:14
 */
public class ValidateUtils {

	/***
	 * 处理是否数字
	 */
	public static synchronized boolean isDiaget(String value) throws Exception {
		if (value == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
		Matcher isNum = pattern.matcher(value);
		return isNum.matches();
	}

	/***
	 * 是否包含特殊字符
	 */
	public static synchronized boolean hasSpecial(String value) throws Exception {
		if (value == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\[\\] \\n]{1,}");
		Matcher hasSpecial = pattern.matcher(value);
		return hasSpecial.matches();
	}

	/**
	 * 长度范围校验
	 */
	public static synchronized boolean legalWordsLength(String value, int minLenght, int maxLenght) throws Exception {
		if (value == null) {
			return false;
		}
		if (maxLenght > 0) {
			if (value.length() > maxLenght) {
				return false;
			}
		}
		if (minLenght > 0) {
			if (value.length() < minLenght) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 数字长度范围校验
	 */
	public static synchronized boolean legalDigitSize(float value, boolean hasMin, int min, boolean hasMax, int max) throws Exception {
		if (hasMax) {
			if (value > max) {
				return false;
			}
		}
		if (hasMin) {
			if (value < min) {
				return false;
			}
		}
		return true;
	}

	/***
	 * 邮箱格式校验
	 */
	public static synchronized boolean handleIsEmail(String value) throws Exception {
		if (value == null || value.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[_-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher isEmail = pattern.matcher(value);
		return isEmail.matches();
	}

	/***
	 * 只包含英文字母和数字
	 */
	public static synchronized boolean isWordDigit(String value) throws Exception {
		if (value == null || value.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[a-z0-9A-Z]+");
		Matcher isWordDigit = pattern.matcher(value);
		return isWordDigit.matches();
	}

	/***
	 * 自定义校验
	 */
	public static synchronized boolean checkDIY(String value, String diyPattern) throws Exception {
		if (value == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(diyPattern);
		Matcher checkDIY = pattern.matcher(value);
		return checkDIY.matches();
	}

	public static String checkGenerateInfo(GenerateInfo generateInfo) {
		StringBuffer msg = new StringBuffer();
		try {
			// ip校验
			if (StringUtill.isEmptyString(generateInfo.getIpAddress())) {
				msg.append("ip地址不能为空！\n");
			} else if (!checkDIY(generateInfo.getIpAddress(), "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$")) {
				msg.append("ip地址格式不正确！\n");
			}

			// port校验
			if (StringUtill.isEmptyString(generateInfo.getPort())) {
				msg.append("数据库端口不能为空！\n");
			} else if (!isDiaget(generateInfo.getPort())) {
				msg.append("数据库端口为1~65536的数字！\n");
			} else if (generateInfo.getIntegerPort() < 1 || generateInfo.getIntegerPort() > 65536) {
				msg.append("数据库端口为1~65536的数字！\n");
			}

			// 数据库用户名校验
			if (StringUtill.isEmptyString(generateInfo.getUserName())) {
				msg.append("数据库用户名不能为空！\n");
			}

			// 数据库用密码校验
			if (StringUtill.isEmptyString(generateInfo.getPassword())) {
				msg.append("数据库密码不能为空！\n");
			}

			if (generateInfo.getDatabaseType() != GenerateConstants.TYPE_MYSQL) {
				msg.append("目前只支持MySQL！\n");
			}

			// 数据库校验
			if (StringUtill.isEmptyString(generateInfo.getDbName())) {
				msg.append("数据库名不能为空！\n");
			}

			// Package Path校验
			if (StringUtill.isEmptyString(generateInfo.getPackagePath())) {
				msg.append("包路径不能为空！\n");
			}

			if (StringUtill.isEmptyString(generateInfo.getUserPath())) {
				msg.append("项目生成路径不能为空！\n");
			}
		} catch (Exception e) {
			return "校验异常！\n";
		}
		return msg.toString();
	}

}
