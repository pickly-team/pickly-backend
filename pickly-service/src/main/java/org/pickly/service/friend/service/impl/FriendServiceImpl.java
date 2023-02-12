package org.pickly.service.friend.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.friend.service.interfaces.FriendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendServiceImpl implements FriendService {

}
