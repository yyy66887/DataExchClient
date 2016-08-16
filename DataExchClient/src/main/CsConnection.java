package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import md5Utils.Md5Utils;
import domain.FilePro;

public class CsConnection {
	private static Socket s = null;
	private static ObjectOutputStream oos = null;
	private static FileInputStream fis = null;

	public static String csConn(Properties pros) {
		try {
			String ip = pros.getProperty("ip").trim();
			String path = pros.getProperty("path").trim();
			int port = Integer.parseInt(pros.getProperty("port").trim());
			s = new Socket(ip, port);

			send(path);
			InputStream in = s.getInputStream();
			byte[] ssin = new byte[1024];
			int l = in.read(ssin);
			String mes = new String(ssin, 0, l);
			return mes;
		} catch (UnknownHostException e) {
			throw new RuntimeException("服务器IP配置出错！");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件不存在！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("服务器忙！");
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void send(String path) throws FileNotFoundException,
			IOException {
		File file = new File(path.trim());
		FilePro fp = new FilePro(file.getName(), Md5Utils.encoding(file),file.length());
		fis = new FileInputStream(file);
		OutputStream out = s.getOutputStream();
		oos = new ObjectOutputStream(out);
		oos.writeObject(fp);

		byte[] buf = new byte[256 * 1024];
		int len = -1;
		while ((len = fis.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		s.shutdownOutput();
	}
}
