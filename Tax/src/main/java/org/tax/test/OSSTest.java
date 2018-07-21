package org.tax.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;

/**
 * @author wyhong
 * @date 2018-7-21
 */
public class OSSTest {

	public void testUpload(){
		File file = new File("E://temp//avatar//11240998_221713648110_2.jpg");
		String key = "e10adc3949ba59abbe56e057f20f883e"+file.getName().substring(file.getName().lastIndexOf("."));
		uploadFileToOSS(file, key);
	}
	
	@Test
	public void testDownload(){
		String key = "e10adc3949ba59abbe56e057f20f883e.jpg";
		downloadFromOSS(key);
	}
	
	public void uploadFileToOSS(File file, String key){
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "oss-cn-beijing.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		final String accessKeyId = "";//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = "";//你的accessKeySecret，参考本文档步骤2
		String bucketName = "tax-avatar";
		String objectName = key;
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 上传文件。
		InputStream in = null;
		ByteArrayOutputStream bos = null;
		try {
			in = new FileInputStream(file);
			bos = new ByteArrayOutputStream((int)file.length());
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = in.read(buf)) != -1){
				bos.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bos.toByteArray()));
		// 关闭OSSClient。
		ossClient.shutdown();
	}
	
	public void downloadFromOSS(String key){
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "oss-cn-beijing.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		final String accessKeyId = "";//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = "";//你的accessKeySecret，参考本文档步骤2
		String bucketName = "tax-avatar";
		String objectName = key;
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
		OSSObject ossObject = ossClient.getObject(bucketName, objectName);
		// 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
		InputStream content = ossObject.getObjectContent();
		OutputStream out = null;
		try {
			out = new FileOutputStream("E://"+key);
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = content.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				content.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 关闭OSSClient。
		ossClient.shutdown();
	}
}
