package com.ndscd;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;



public class Test {
	public  String logPath = "";
	public static final String LOG_FILE_NAME="ex@date.log";
	public static final String PLACEHOLDER="@date";
	public static final String ANNOTATE_CHAR = "#";
	public static final String SPILT_CHAR = " ";
	public static final String END_PREFIX = ".asp";
	public static final String MYPAGE_URL_STARTCHAR = "/mypage/";
	public static final String JOB_URL_STARTCHAR = "/job/";
	public static final int CLIENT_TYPE_PC_MYPAGE_WEB=7;
	public static final int CLIENT_TYPE_PC_JOB_WEB=8;
	//0时统计前一日
	public static final int START_WORK_TIME_HOUR=2;
	public static void main(String[] args) {
		try{
			Test t= new Test();
			Vector<Pv> l=t.getLogFileContent(new File("D:/log/ex150305.log"));
			t.savePv(l);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void savePv( Vector<Pv> l) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url ="jdbc:mysql://localhost/tpg_ag?user=root&password=123&useUnicode=true&characterEncoding=utf-8";
		Connection connMysql= DriverManager.getConnection(url);
		Statement st=connMysql.createStatement();
		String sql="";
		for (int i=0;i<l.size();i++){
			Pv p=l.get(i);
			 sql="insert into pv_copy (ip,url,client_type,ts,param)"
			 		+ " values('"+p.getIp()+"','"+p.getUrl()+"',"+p.getClientType()+","
			 				+ "'"+p.getTs()+"','"+p.getParam()+"')";
			st.addBatch(sql);
			if (i%200==0){
				st.executeBatch();
			}
		}
		st.executeBatch();
	}
	public  Vector<Pv> getLogFileContent(File file) throws Exception {
		Vector<Pv> result=new Vector<Pv>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String arr[];
		while ((line = br.readLine()) != null) {
			if (!line.startsWith(ANNOTATE_CHAR)) {
				arr = line.split(SPILT_CHAR);
				String url = arr[4];
				Pv pv= new Pv(arr[8], url, arr[0]+" " +arr[1] ,arr[5]);
				//ASP结尾
				if (url.endsWith(END_PREFIX)) {
					if (url.startsWith(MYPAGE_URL_STARTCHAR)){
						pv.setClientType(CLIENT_TYPE_PC_MYPAGE_WEB);
					}else if (url.startsWith(JOB_URL_STARTCHAR)){// job/开头
						pv.setClientType(CLIENT_TYPE_PC_JOB_WEB);
					}
					result.add(pv);
				}

			}
		}
		br.close();
		return result;
	}
}	
