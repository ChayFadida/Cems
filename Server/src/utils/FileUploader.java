package utils;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DBController;
import DataBase.SqlQueries;

public class FileUploader {
	private FileProcessor file;

	public FileProcessor getFile() {
		return file;
	}

	public void setFile(FileProcessor file) {
		this.file = file;
	}
	public FileUploader(FileProcessor file) {
		this.file = file;
	}
	
	public void fileRecived() {
		System.out.println("new file recived with length " + file.getSize());
	}
	
	public boolean createFileFromDb(String table, int examId, int studentId, String newFile) {
	    ArrayList<HashMap<String, Object>> resultList;
	    byte[] blobData = null;
		try {
			FileOutputStream fos = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			resultList = DBController.getInstance().executeQueries(SqlQueries.getFile(table, examId, studentId));
			blobData = (byte[]) resultList.get(0).get("pdfBytes");
			bos.write(blobData, 0 , blobData.length);
			bos.write(blobData, 0 , blobData.length);
			bos.flush();
			fos.flush();
		} catch(Exception e) {
			System.out.println("error whie fetching the file from db");
			return false;
		}
		return true;
	}
	
}
