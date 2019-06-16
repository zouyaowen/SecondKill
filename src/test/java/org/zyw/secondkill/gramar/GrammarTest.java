package org.zyw.secondkill.gramar;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;
import org.springframework.util.DigestUtils;

public class GrammarTest {

	@Test
	public void test() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("hello", "hello");
		map.put("world", "world");
		Collection<String> values = map.values();
		System.out.println(values.toString());
		System.out.println(String.join(",", values));
	}

	@Test
	public void test1() {
		String password = DigestUtils.md5DigestAsHex("123456".getBytes());
		System.out.println(password);
	}

	@Test
	public void test2() {
		UUID randomUUID = UUID.randomUUID();
		String aaa = randomUUID.toString().substring(0, 8);
		System.out.println(aaa);
		String bbb = randomUUID.toString();
		System.out.println(bbb);
		// 随机生成的数据8位字符串拼接密码进行MD5加密后再拼接随机生成的数据作为加密后的密码存储，保证一样的密码不一样的加密效果
	}

	@Test
	public void test3() {
		String str = "http://8.pic.pc6.com/thumb/up/2018-1/2018111203031193270653480_600_0.png";
		System.out.println(str.length());
	}

	@Test
	public void test4() {
		LocalDateTime now = LocalDateTime.now();
		String format = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
		System.out.println(format);
		//ISO_DATE 2019-06-09
		//BASIC_ISO_DATE 20190609
		//ISO_DATE_TIME 2019-06-09T22:35:16.392
		//ISO_INSTANT 异常
		//ISO_LOCAL_DATE 2019-06-09
		//ISO_LOCAL_DATE_TIME 2019-06-09T22:36:48.524
		//ISO_LOCAL_TIME 22:37:15.51
		//ISO_OFFSET_DATE 异常
		//ISO_OFFSET_DATE_TIME 异常
		//ISO_OFFSET_TIME 异常
		//ISO_ORDINAL_DATE 2019-160 诡异
		//ISO_TIME 22:39:19.589
		//ISO_ZONED_DATE_TIME 异常
		//String format2 = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
		//System.out.println(format2);
		LocalDateTime now2 = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
		System.out.println(now2);

	}

	@Test
	public void test5() {
		System.out.println(String.format("%06d", 2));
	}
}
