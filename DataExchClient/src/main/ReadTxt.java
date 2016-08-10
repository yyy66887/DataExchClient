package main;

import java.io.InputStream;
import java.util.Properties;

public class ReadTxt {
	public static Properties readPros(){
		try {
			Properties pros = new Properties();
			InputStream in = DataExchClient.class.getClassLoader()
					.getResourceAsStream("info.properties");
			pros.load(in);
			String path = pros.getProperty("path");
			String ip = pros.getProperty("ip");
			String port = pros.getProperty("port");
//			System.out.println(port);
//			System.out.println(path);
//			System.out.println(ip);
			return pros;
		} catch (Exception e) {
			throw new RuntimeException("配置文件出错！");
		}
	}
}
