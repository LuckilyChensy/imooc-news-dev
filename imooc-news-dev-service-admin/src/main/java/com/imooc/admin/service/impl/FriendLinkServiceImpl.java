package com.imooc.admin.service.impl;

import com.imooc.admin.service.FriendLinkService;
import com.imooc.pojo.mo.FriendLinkMO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Override
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO) {

    }

    @Override
    public List<FriendLinkMO> queryAllFriendLinkList() {
        return null;
    }

    @Override
    public void delete(String linkId) {

    }

    @Override
    public List<FriendLinkMO> queryPortalAllFriendLinkList() {
        return null;
    }

}
