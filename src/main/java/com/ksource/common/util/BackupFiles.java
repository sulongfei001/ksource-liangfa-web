/**
 * 
 */
package com.ksource.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author XT
 * 2012-12-20
 */
public class BackupFiles {
	private  String oldRoot;
	private  String newRoot;
	public BackupFiles(){}
	public BackupFiles(String oldPath,String newPath){
		this.oldRoot=oldPath;
		this.newRoot=newPath;
	}
	public Boolean backupFiles(){
		File sourceFolder=new File(oldRoot);
		try {
			backupFiles(sourceFolder);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**递归函数
	 * @param sourceFolder
	 * @author XT
	 */
	private void backupFiles(File sourceFolder) throws IOException {
		File[] files = getCopyFiles(oldRoot, newRoot, sourceFolder);
		for (File copyFile : files) {
			if (copyFile.isDirectory()) {
				//如果是文件夹就创建文件夹
				createFolder(copyFile);
				//递归调用，如果是文件夹就先备份该文件夹中的文件
				backupFiles(copyFile);
			}else {
				copyFile(copyFile);
			}
		}
		//备份完后，查看这个文件夹下的文件（夹）数是不是和源文件夹下的文件(夹)数是否相同，不同说明目录文件夹下有需要删除的文件
		File goleFile=new File(sourceFolder.getPath().replace(oldRoot, newRoot));
		//先进行的是备份，这里目标文件夹下的文件数只可能大于源文件夹下的文件数
		if ((goleFile.list().length)>(sourceFolder.list().length)) {
			//删除多余的文件或目录
			deleteGoleFileChilds(goleFile);
		}
	}
	/**
	 * @throws FileNotFoundException,IOException 
	 * @throws FileNotFoundException 
	 * 拷贝文件
	 * @param oldRoot2
	 * @param newRoot2
	 * @param copyFile
	 * @author XT
	 * @throws  
	 */
	private void copyFile(File copyFile) throws FileNotFoundException,IOException{
			FileInputStream inputStream=new FileInputStream(copyFile);
			FileOutputStream outputStream=new FileOutputStream(new File(copyFile.getPath().replace(oldRoot, newRoot)));
			byte[] buffer=new byte[1024*1000];
			Integer length=inputStream.read(buffer);
			while (length!=-1) {
				outputStream.write(buffer, 0, length);
				length=inputStream.read(buffer);
			}
			outputStream.flush();
			inputStream.close();
			outputStream.close();
	}
	/**
	 * 删除目标文件夹下的多余的文件(夹)
	 * @param goleFile
	 * @author XT
	 */
	private void deleteGoleFileChilds(File goleFile) {
		File[] deleteFiles=goleFile.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File childFile) {
				File tempFile=new File(childFile.getPath().replace(newRoot, oldRoot));
				return !tempFile.exists();
			}
		});
		deleteFiles(deleteFiles);
	}
	/**
	 * 
	 * 强制删除文件夹
	 * @param deleteFiles
	 * @author XT
	 */
	private void deleteFiles(File[] deleteFiles) {
		for (File file : deleteFiles) {
			if (file.isFile()) {
				file.delete();
			}else{
				File[] files=file.listFiles();
				deleteFiles(files);
				//删除文件夹
				file.delete();
			}
		}
	}
	
	
	/**
	 * 创建文件夹
	 * @param copyFile
	 * @author XT
	 */
	private void createFolder(File copyFile) {
		String newPath=copyFile.getPath().replace(oldRoot, newRoot);
		File file=new File(newPath);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	/**
	 * 查找需要备份的文件
	 * @param oldRoot2
	 * @param newRoot2
	 * @param sourceFolder
	 * @return
	 * @author XT
	 */
	private File[] getCopyFiles(String oldRoot2, String newRoot2,
			File sourceFolder) {
		FileFilter fileFilter=new FileFilter() {
			@Override
			public boolean accept(File file) {
				String path=file.getPath();
				path=path.replace(oldRoot,newRoot);
				File tempFile=new File(path);
				return !tempFile.exists()||tempFile.isDirectory();
			}
		};
		File[] files=sourceFolder.listFiles(fileFilter);
		return files;
	}
	
}
