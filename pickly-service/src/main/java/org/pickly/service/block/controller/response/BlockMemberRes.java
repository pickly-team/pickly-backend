package org.pickly.service.block.controller.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockMemberRes {

  private List<BlockMember> data;

  public static BlockMemberRes of(List<BlockMember> blockMembers) {
    return new BlockMemberRes(blockMembers);
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BlockMember {

    private Long id;
    private String name;
    private String profileEmoji;

    public static BlockMember of(Long id, String name, String profileEmoji) {
      return new BlockMember(id, name, profileEmoji);
    }
  }

}
