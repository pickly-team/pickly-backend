package org.pickly.service.friend.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberFollowerInfoRes {

  private int count;

  private List<FollowerRes> followerList;

}
