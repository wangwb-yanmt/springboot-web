package com.wangwb.web.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压缩
 * @author wangwb@sparknet.com.cn
 *
 */
public class ZipUtil {

	/**
	 * 压缩
	 * @param inputFile
	 * @param outputFile
	 * @throws Exception
	 */
	public static void ZipCompress(String inputFile, String outputFile) throws Exception {
		//创建zip输出流
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile));
        //创建缓冲输出流
		BufferedOutputStream bos = new BufferedOutputStream(out);
		File file = new File(inputFile);
		//压缩
		compress(out, bos, file, null);
		bos.flush();
		bos.close();
		out.close();
    }
	
	//压缩
	public static void compress(ZipOutputStream out, BufferedOutputStream bos, File input, String name) throws IOException {
		if (name == null) {
			name = input.getName();
        }
        //如果路径为目录（文件夹）
		if (input.isDirectory()) {
			//取出文件夹中的文件（或子文件夹）
			File[] flist = input.listFiles();
			//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入
			if (flist.length == 0) {   
				out.putNextEntry(new ZipEntry(name + "/"));
			} else {      //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
				for (int i = 0; i < flist.length; i++) {
					//递归压缩
					compress(out, bos, flist[i], name + "/" + flist[i].getName());
				}
			}
		} else {			//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
			out.putNextEntry(new ZipEntry(name));
			FileInputStream fos = new FileInputStream(input);
			BufferedInputStream bis = new BufferedInputStream(fos);
			int len;
            //将源文件写入到zip文件中
			byte[] buf = new byte[1024];
			while ((len = bis.read(buf)) != -1) {
				bos.write(buf,0,len);
            }
			bis.close();
			fos.close();
        }
    }
	
	/**
     * zip文件解压
     * @param inputFile  待解压文件夹/文件
     * @param destDirPath  解压路径
     */
	public static void ZipUncompress(String inputFile,String destDirPath) throws Exception {
		File srcFile = new File(inputFile);//获取当前压缩文件
        // 判断源文件是否存在
		if (!srcFile.exists()) {
			throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
        //开始解压
        //构建解压输入流
		ZipInputStream zIn = new ZipInputStream(new FileInputStream(srcFile));
		ZipEntry entry = null;
		File file = null;
		while ((entry = zIn.getNextEntry()) != null) {
			if (!entry.isDirectory()) {
				file = new File(destDirPath, entry.getName());
				if (!file.exists()) {
					new File(file.getParent()).mkdirs();//创建此文件的上级目录
                }
				OutputStream out = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(out);
				int len = -1;
				byte[] buf = new byte[1024];
				while ((len = zIn.read(buf)) != -1) {
					bos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
				bos.close();
				out.close();
            }
        }
    }
	
	
}
