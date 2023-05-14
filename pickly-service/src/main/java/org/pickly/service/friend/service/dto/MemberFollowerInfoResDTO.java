package org.pickly.service.friend.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberFollowerInfoResDTO {

  private int count;

  private List<FollowerResDTO> followerList;

}
