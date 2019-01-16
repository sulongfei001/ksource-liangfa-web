package com.ksource.liangfa.web.bean;

public class MyFile {

	private String fileName;
	private byte[] file;
    private String url;
    private String fileByte;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public boolean fileisNull(){
		return this.fileName==null ||this.file==null || this.file.length==0;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileByte() {
		return fileByte;
	}
	public void setFileByte(String fileByte) {
		this.fileByte = fileByte;
	}
}
