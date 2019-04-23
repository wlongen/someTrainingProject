package com.wenlong.service.impl;

import com.wenlong.dao.UserDao;
import com.wenlong.dao.impl.UserDaoImpl;
import com.wenlong.entity.User;
import com.wenlong.service.UserService;
import com.wenlong.utils.MailUtil;

public class UserServiceImpl implements UserService {

	@Override
	public void regiest(User user) {
		try{
			//���û�ע����Ϣ���뵽���ݿ�
			UserDao userDao  = new UserDaoImpl();
			userDao.add(user);
			//���ͼ����ʼ�
			MailUtil.sendMail(user.getEmail(), user.getCode());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
