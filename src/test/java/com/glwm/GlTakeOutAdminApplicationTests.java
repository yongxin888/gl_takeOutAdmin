package com.glwm;

import com.glwm.common.FileUtils;
import com.glwm.dto.MerchantsAndUserPage;
import com.glwm.entity.User;
import com.glwm.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class GlTakeOutAdminApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		User login = userMapper.Login("admin", "e10adc3949ba59abbe56e057f20f883e");
		System.out.println(login);
	}
	
	@Test
	void test1(){
//		String download = FileUtils.download("blob:http://localhost:9090/fa3fda92-fa0f-40d3-a2fd-efcef4446052");
//		System.out.println(download);
		String replace = UUID.randomUUID().toString().replace("-", "");
		System.out.println(replace);
	}

}
