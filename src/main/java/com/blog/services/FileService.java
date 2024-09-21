package com.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String upload(String path,MultipartFile file)  throws IOException;
	InputStream getresponse(String path,String filename)throws FileNotFoundException;
}
