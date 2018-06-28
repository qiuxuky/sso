package com.yale.sso.crypto;

import javax.validation.constraints.NotNull;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.jasig.cas.authentication.handler.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * 借助shiro加密功能
 * @author qx
 *
 */
@Component("customPasswordEncoder")
public class CustomPasswordEncoder implements PasswordEncoder{
	
	@Value("${cas.passswordEncoder.hashIterations}")
	private int hashIterations;
	
	@NotNull
	@Value("${cas.passswordEncoder.encodingAlgorithm}")
    private  String encodingAlgorithm;
	
	public CustomPasswordEncoder() {
		super();
	}

	public CustomPasswordEncoder(String encodingAlgorithm) {
        this.encodingAlgorithm = encodingAlgorithm;
    }
	
	public String encode(String password) {
		if (password == null || "".equals(password)) {
            return null;
        }
		
		return new SimpleHash(encodingAlgorithm,password).toHex();
	}
	
	
	public String encode(String password,Object salt) {
		if (password == null  || "".equals(password)) {
            return null;
        }
		
		return new SimpleHash(encodingAlgorithm,password,salt,hashIterations).toHex();
	}


	public int getHashIterations() {
		return hashIterations;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}
	

}
