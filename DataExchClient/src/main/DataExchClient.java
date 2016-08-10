package main;

public class DataExchClient {
	public static void main(String[] args) {
		int count = 0;
		String mes = "";
		while (!"ok".equals(mes)) {
			mes = CsConnection.csConn(ReadTxt.readPros());
			System.out.println(mes);
			count++;
			if(count>3){
				throw new RuntimeException("服务器忙！");
			}
		}
	}

}
