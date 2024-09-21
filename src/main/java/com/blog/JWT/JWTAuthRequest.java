package com.blog.JWT;

import lombok.Data;

@Data
public class JWTAuthRequest {

	private String username;
	private String password;
}
