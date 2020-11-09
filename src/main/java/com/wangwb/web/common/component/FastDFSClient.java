//package com.wangwb.web.common.component;
//
//import org.apache.commons.httpclient.NameValuePair;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.wangwb.web.common.bean.FastDFSFile;
//
///**
// * fastdfs客户端操作
// * @author wangwb@sparknet.com.cn
// *
// */
//public class FastDFSClient {
//	private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
//
//	static{
//        try {
//            ClientGlobal.initByProperties("fastdfs-client.properties");
//        } catch (Exception e) {
//            e.printStackTrace();
//            
//        }
//    }
//	
//	/**
//	 * 上传
//	 * @param file
//	 * @return
//	 */
//	public static String[] upload(FastDFSFile file) {
//		String[] uploadResults = null;
//		NameValuePair[] mataList = new NameValuePair[1];
//		mataList[0] = new NameValuePair("author", file.getAuthor());
//		long startTime = System.currentTimeMillis();
//		StorageClient storageClient=null;
//		try {
//			//获取 
//			storageClient = getStorageClient();
//			//上传
//			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
//		} catch (Exception e) {
//			logger.error(file.getName()+"文件上传异常");
//		}
//		//验证上传结果
//		if (uploadResults == null && storageClient!=null) {
//			logger.info("upload file fail, error code:" + storageClient.getErrorCode());
//	    }
//		//上传文件成功会返回 groupName。
//		logger.info("upload file successfully!!!" + "group_name:" + uploadResults[0] + ", remoteFileName:" + " " + uploadResults[1]);
//		return uploadResults;
//	}
//	
//	/**
//	 * 下载文件（返回文件字节流）
//	 * @param groupName
//	 * @param remoteFileName
//	 * @return
//	 */
//	public static InputStream downFile(String groupName, String remoteFileName) {
//		try {
//			StorageClient storageClient = getStorageClient();
//			byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
//			InputStream ins = new ByteArrayInputStream(fileByte);
//			return ins;
//		} catch (IOException e) {
//			logger.error("IO Exception: Get File from Fast DFS failed", e);
//		} catch (Exception e) {
//			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
//		}
//		return null; 
//	}
//	
//	/**
//	 * 获取文件信息
//	 * @param groupName
//	 * @param remoteFileName
//	 * @return
//	 */
//	public static FileInfo getFile(String groupName, String remoteFileName) {
//		try {
//			storageClient = new StorageClient(trackerServer, storageServer);
//			return storageClient.get_file_info(groupName, remoteFileName);
//		} catch (IOException e) {
//			logger.error("IO Exception: Get File from Fast DFS failed", e);
//		} catch (Exception e) {
//			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
//		}
//		return null;
//	}
//	
//	/**
//	 * 删除文件
//	 * @param groupName
//	 * @param remoteFileName
//	 * @throws Exception
//	 */
//	public static void deleteFile(String groupName, String remoteFileName) throws Exception {
//		StorageClient storageClient = getStorageClient();
//		int i = storageClient.delete_file(groupName, remoteFileName);
//		logger.info("delete file successfully!!!" + i);
//	}
//	
//	/**
//	 * 获取TrackerServer
//	 * @return
//	 * @throws IOException
//	 */
//	private static TrackerServer getTrackerServer() throws IOException {
//	    TrackerClient trackerClient = new TrackerClient();
//	    TrackerServer trackerServer = trackerClient.getConnection();
//	    return  trackerServer;
//
//	}
//	
//	/**
//	 * 获取Storage
//	 * @return
//	 * @throws IOException
//	 */
//	private static StorageClient getStorageClient() throws IOException {
//		TrackerServer trackerServer = getTrackerServer();
//		StorageClient storageClient = new StorageClient(trackerServer, null);
//		return  storageClient;
//	}
//	
//	
//}
