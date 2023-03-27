package org.pickly.service.bookmark.controller.request;


    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.NonNull;
    import org.pickly.service.bookmark.entity.Visibility;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkCreateReq {

  @NonNull
  private Long categoryId;

  @NonNull
  private Long memberId;

  @NonNull
  private String url;

  @NonNull
  private String title;

  @NonNull
  private String previewImageUrl;

  @NonNull
  private Boolean isUserLike;

  @NonNull
  private Boolean readByUser;

  @NonNull
  private Visibility visibility;

}
