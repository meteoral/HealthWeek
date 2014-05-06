package com.bit_health.android.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**********************************************************************
 * 类名：BirthdayToAge
 * 
 * 主要功能：由出生年月算出年龄
 * 
 * @author 梁才学 创建日期：2014.1.13
 **********************************************************************/
public class BirthdayToAge {
	
	/**********************************************************************
	 * 方法描述：根据日期计算出年龄，并返回年龄的数值
	 * 
	 **********************************************************************/
	public String getAge(String strDate){
		
		try {
			return birthdayTurnToAge(ConverToDate(strDate));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "年龄计算出错，请输入正确出生日期。";
		}
	}

	/**********************************************************************
	 * 方法描述：把字符串转为日期
	 * 
	 * @param strDate 字符串格式形如：2013-10-5
	 * @return 返回日期
	 **********************************************************************/
	private Date ConverToDate(String strDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(strDate);
	}

	/**********************************************************************
	 * 方法描述：把日期转为字符串
	 * 
	 * @param strDate 字符串格式形如：2013-10-5
	 * @return 返回日期
	 **********************************************************************/
	private String ConverToString(Date date)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
		return df.format(date);
	}
	
	/**********************************************************************
	 * 方法描述：根据日期计算出年龄
	 * 
	 * @param 日期格式形如：2013-10-5
	 * @return 年龄
	 **********************************************************************/
	private String birthdayTurnToAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}

		return age + "";
	}
}
