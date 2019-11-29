package com.my.xa.core.utills;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
/***
 * 
 * @author xuyilu
 * @describe 按照行数分割数据文件
 *
 */
public class SplitFile {
	private static final Logger LOG = LoggerFactory.getLogger( SplitFile.class.getName() );
	/***
	 * 
	 * @param source 待分割文件
	 * @param preFileName 文件前缀名称
	 * @param desName  后缀
	 * @param numFomat 格式
	 * @param start    开始命名
	 * @param iLimt   分割行数
	 * @throws Exception
	 */
	public static  void splitFile(File source,String preFileName,String desName,String numFomat,int start,int iLimt ) throws Exception{
		DecimalFormat decimalformat = new DecimalFormat(numFomat);
		int k = start;
		File target = new File(source.getParentFile().getAbsolutePath()+"/"+ preFileName+"_"+ decimalformat.format(k)+desName);
		if(!target.getParentFile().exists())target.getParentFile().mkdirs();
		if(!target.exists())target.createNewFile();
		OutputStream o = new FileOutputStream(target);
		OutputStreamWriter out = new OutputStreamWriter(o);
		BufferedWriter  bw = new BufferedWriter(out);
		InputStreamReader fr = null;
		BufferedReader bf = null;
	    int iCountLint = 1;
		try {
			if(!source.exists())throw new Exception("file not exists");
			 fr = new InputStreamReader(new FileInputStream(source));
			 bf = new BufferedReader(fr);
		     String st = bf.readLine();
		     while(st!=null){
		    	 int currPage = (int)Math.floor(iCountLint/iLimt)+ (iCountLint%iLimt>0?1:0);
		    	 if(currPage>k){
		    		    bw.flush();
		    		    if(bw!=null)bw.close();
						if(out!=null)out.close();
						if(o!=null)o.close();
		    		    k=currPage;
		    		    target = new File(source.getParentFile().getAbsolutePath()+"/"+preFileName+"_"+ decimalformat.format(k)+desName);
		    		    if(!target.getParentFile().exists())target.getParentFile().mkdirs();
		    		    if(!target.exists())target.createNewFile();
		    			o = new FileOutputStream(target);
		    			out = new OutputStreamWriter(o);
		    			bw = new BufferedWriter(out);
		    	 }
		    	 bw.write(st+"\r\n");
		    	 st = bf.readLine();
		    	 iCountLint++;
		     }
		     bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				 if(bw!=null)bw.close();
				 if(out!=null)out.close();
				 if(o!=null)o.close();
				 if(bf!=null)bw.close();
				 if(fr!=null)out.close();
			} catch (Exception e2) {
				 
			}
		}
	}

	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static void WriteStringToFile(File file,String content) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
