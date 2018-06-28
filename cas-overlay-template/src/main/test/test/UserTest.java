package test;

import org.apache.shiro.crypto.hash.SimpleHash;

public class UserTest {
	
	public static void main(String[] args) {
		
		System.out.println(new SimpleHash("MD5","qx1234","qiuxu",1024));
	}

}
