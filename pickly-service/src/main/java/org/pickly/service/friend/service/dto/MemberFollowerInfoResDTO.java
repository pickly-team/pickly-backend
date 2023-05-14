package org.pickly.service.friend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberFollowerInfoResDTO {

  private int count;

  private List<FollowerResDTO> followerList;

}
