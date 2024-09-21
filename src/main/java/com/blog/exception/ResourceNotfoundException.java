package com.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotfoundException extends RuntimeException{
  String Entityname;
  String Fieldname;
  int value;
  
  public ResourceNotfoundException(String entityname) {
	  super("No users exists");
  }
  
public ResourceNotfoundException(String entityname, String fieldname, int value) {
	 super(String.format("%s not found with %s :%d", entityname,fieldname,value));
	Entityname = entityname;
	Fieldname = fieldname;
	this.value = value;
}
  
}
