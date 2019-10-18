package com.learning.project.fzk.multiThread.blockingQueue.use;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间、日期计算工具类
 */
public class DateUtil {

	public static final String dateFormatType = "yyyy-MM-dd HH:mm:ss";

	// date类型转换为String类型
	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date date) {
		return new SimpleDateFormat(dateFormatType).format(date);
	}

	// long类型转换为String类型
	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(Long currentTime) throws Exception {
		Date date = longToDate(currentTime); // long类型转成Date类型
		return dateToString(date);
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// long转换为Date类型
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime) throws Exception {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld); // 把date类型的时间转换为string
		return stringToDate(sDateTime);
	}

	// string类型转换为long类型
	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime) throws Exception {
		Date date = stringToDate(strTime); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			return dateToLong(date);
		}
	}

	// date类型转换为long类型
	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	/**
	 * 获取当前时间的 long 型值
	 */
	public static long nowDateLong() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取 目标时间 距 当前的时间 值
	 */
	public static long timeToNow(String originTime) throws Exception {
		return nowDateLong() - stringToLong(originTime);
	}

	/**
	 * 获取 当前时间 距 目标时间 值
	 */
	public static long nowToTime(String targetTime) throws Exception {
		return stringToLong(targetTime) - nowDateLong();
	}

	/**
	 * 判断 是否在当前时间 之前
	 */
	public static Boolean beforeThanNow(String targetTime) throws Exception {

		Date target = null;

		target = stringToDate(targetTime);


		return target.equals(new Date()) || target.before(new Date());
	}

	/**
	 * 判断 是否在当前时间 之后
	 */
	public static boolean laterThanNow(String targetTime) {

		Date target = null;
		try {
			target = stringToDate(targetTime);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return target.after(new Date());
	}

	public static boolean judgeThisTimeRunOrNot(String startTime, String endTime) throws Exception {
		return beforeThanNow(startTime) && laterThanNow(endTime);
	}

	public static boolean judgeThisTimeIsNotStart(String startTime) {
		return laterThanNow(startTime);
	}

	public static boolean judgeThisTimeIsOutOfDate(String endTime) throws Exception {
		return beforeThanNow(endTime);
	}
}
