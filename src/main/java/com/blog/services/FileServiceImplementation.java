package com.blog.services;

import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImplementation implements FileService {

	@Override
	public String upload(String path, MultipartFile file) throws IOException {
		
		//file name
		  String originalFilename = file.getOriginalFilename();
		
		//full path
		  String filepath= path+File.separator+originalFilename;
		  
		//create folder images if not created
		File f=new File(path) ;		
		if(!f.exists()) {
			f.mkdirs() ; //creates if not created
		}
		  
		//file copy
	//	Files.copy(file.getInputStream(), Paths.get(filepath, null), StandardCopyOption.REPLACE_EXISTING);
		
		return originalFilename;
	}

	@Override
	public InputStream getresponse(String path, String filename) throws FileNotFoundException {

		String fullpath=path+File.separator+filename;
		InputStream is=new FileInputStream(fullpath);
		return is;
	}

}
