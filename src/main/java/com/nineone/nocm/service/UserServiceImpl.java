package com.nineone.nocm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nineone.nocm.domain.User;
import com.nineone.nocm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;


//	@Override
//	public boolean insertUser(User user) {
////		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		return (userRepository.insertUser(user) > 0) ? true : false;
//		
//	}


	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//		Map<String,String> userInfo = (Map<String, String>) userRepository.getUserfindByEmail(userEmail);
//		User user = User.builder()
//				.name(userInfo.get("name"))
//				.email(userInfo.get("email"))
//				.phone(userInfo.get("phone"))
//				.picture(userInfo.get("picture"))
//				.build();
//		if (user == null){
//			throw new UsernameNotFoundException("can not find user");
//		}
//		return (UserDetails) user;
		return null;
	}


	@Override
	public boolean emailCheck(String id) {
		return (userRepository.getUserfindById(id) == null) ? true : false;
	}


	@Override
	@Transactional
	public boolean insertUser(User user, DefaultOAuth2User oauth2user,HttpSession httpsession) {
		Map<String,Object> map = new HashMap<>();
		map.put("identifier", oauth2user.getName());
		map.put("email", user.getEmail());
		int userResult = 0;
		User dbUser = userRepository.getUserfindByEmail(user.getEmail());
		if(dbUser!=null) {
			userResult = 1;
		}else {
			userResult = userRepository.insertUser(user); 
		}
		
		int snsResult = userRepository.insertSNSInfo(map);
		
		if (userResult > 0 && snsResult > 0) {
			
			if(dbUser==null) {
				User settingUser = (User)httpsession.getAttribute("user");
				settingUser.setName(user.getEmail());
				settingUser.setName(user.getName());
				settingUser.setPhone(user.getPhone());
				httpsession.setAttribute("user", settingUser);
			}else {
				httpsession.setAttribute("user", dbUser);
			}
			
			return true;
		}else { 
			return false;
		}  
	}


	@Override
	public List<User> getAllUserList() {
		return userRepository.getAllUserList();
	}

	@Override
	public List<User> getCurrentChannelUserList(int channel_id) {
		return userRepository.thisChannelUserList(channel_id);
	}

}
