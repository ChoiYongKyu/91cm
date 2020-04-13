package com.nineone.nocm.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;
import com.nineone.nocm.domain.LastAccess;
import com.nineone.nocm.repository.JoinInfoRepository;
import com.nineone.nocm.util.DateUtil;

@Service
public class JoinInfoServiceImpl implements JoinInfoService {

	@Autowired
	private MessageService messageService;
	
    @Autowired
    private JoinInfoRepository joinInfoRepository;

    @Override
    public boolean leaveUser(Map<String, Object> info) {
        return joinInfoRepository.leaveUser(info);
    }

    @Override
    public boolean isExistUser(Invite invite) {
        return joinInfoRepository.isExistUser(invite);
    }

    @Override
    public boolean AuthorityCheck(Invite invite) {
       return joinInfoRepository.channelAuthorityCheck(invite);
    }

    @Override
    public boolean insertJoinInfo(JoinInfo joinInfo) {
        return (joinInfoRepository.insertJoinInfo(joinInfo) > 0);
    }

	@Override
	public int updateLastAccessDate(int CurrentChannelId,String email) {
		Date last_access_date = DateUtil.makeDate();
		JoinInfo joinInfo = JoinInfo.builder()
    			.channel_id(CurrentChannelId)
    			.member_email(email)
    			.last_access_date(last_access_date)
    			.build();
		return joinInfoRepository.updateLastAccessDate(joinInfo);
	}


}
