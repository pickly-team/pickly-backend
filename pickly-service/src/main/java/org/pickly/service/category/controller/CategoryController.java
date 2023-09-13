package org.pickly.service.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.pickly.service.category.common.CategoryMapper;
import org.pickly.service.category.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.category.controller.request.CategoryRequestDTO;
import org.pickly.service.category.controller.request.CategoryUpdateRequestDTO;
import org.pickly.service.category.controller.response.CategoryRes;
import org.pickly.service.category.controller.response.CategoryResponseDTO;
import org.pickly.service.category.service.dto.CategoryDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.pickly.service.common.utils.encrypt.EncryptService;
import org.pickly.service.common.utils.encrypt.ExtensionKey;
import org.pickly.service.common.utils.page.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Category", description = "카테고리 API")
public class CategoryController {

  private final EncryptService encryptService;
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @PostMapping("/categories")
  @Operation(summary = "카테고리들을 생성한다.", description = "동시에 다수의 카테고리를 생성할 수 있다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public void create(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long memberId,

      @RequestBody @Valid
      @Length(min = 1, message = "최소 1개의 카테고리 정보를 입력해주세요.") final List<CategoryRequestDTO> requests
  ) {
    categoryService.create(memberId, requests);
  }

  @PutMapping("/categories/{categoryId}")
  @Operation(summary = "카테고리를 수정한다.", description = "특정 카테고리의 이름, 이모지를 수정할 수 있다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryResponseDTO.class)))
  })
  public ResponseEntity<CategoryResponseDTO> update(
      @PathVariable @Positive Long categoryId,
      @RequestBody @Valid final CategoryUpdateRequestDTO dto
  ) {
    Category category = categoryService.update(categoryId, dto);

    return ResponseEntity
        .ok(categoryMapper.toResponseDTO(category));
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "400", description = "잘못된 순서값 입력"),
  })
  @PatchMapping("/categories/order-num")
  @Operation(summary = "카테고리들의 순서를 수정한다.", description = "다수의 카테고리의 순서를 수정할 수 있다.")
  public void updateOrderNum(
      @RequestBody @Valid
      @Length(min = 1, message = "최소 1개의 카테고리 정보를 입력해주세요.") final List<CategoryOrderNumUpdateReq> requests
  ) {
    categoryService.updateOrderNum(requests);
  }

  @DeleteMapping("/categories/{categoryId}")
  @Operation(summary = "특정 카테고리를 삭제한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 카테고리"),
  })
  public ResponseEntity<Void> delete(
      @PathVariable Long categoryId
  ) {
    categoryService.delete(categoryId);
    return ResponseEntity
        .noContent()
        .build();
  }

  @DeleteMapping("/categories")
  @Operation(summary = "다수의 카테고리를 삭제한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "400", description = "존재하지 않는 카테고리"),
  })
  public ResponseEntity<Void> deleteAllById(
      @RequestParam(value = "categoryId") List<Long> categoryIds
  ) {
    categoryService.deleteAllByCategoryId(categoryIds);
    return ResponseEntity
        .noContent()
        .build();
  }

  @GetMapping("/categories")
  @Operation(summary = "특정 유저의 카테고리 목록을 조회한다.", description = "페이지네이션이 적용되어 있다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = PageResponse.class)))
  })
  public PageResponse<CategoryDTO> getCategoryWithPagingByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long memberId,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "1")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    return categoryService.getCategoriesWithPagingByMember(cursorId, pageSize, memberId);
  }

  @GetMapping("/members/{memberId}/categories")
  @Operation(summary = "특정 유저의 카테고리 목록을 전체 조회한다.", description = "페이지네이션이 적용되어 있지 않다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryDTO.class)))
  })
  public List<CategoryRes> getCategoryByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long memberId
  ) {
    List<CategoryDTO> dtos = categoryService.getCategoriesByMember(memberId);
    return dtos.stream().map(categoryMapper::toResponse).toList();
  }

  @GetMapping("/categories/{categoryId}")
  @Operation(summary = "특정 카테고리를 조회한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryDTO.class)))
  })
  public CategoryRes findById(
      @Parameter(name = "categoryId", description = "카테고리 ID (PK) 값", example = "1", required = true)
      @Positive(message = "카테고리 ID (PK)는 양수입니다.") @PathVariable final Long categoryId
  ) {
    CategoryDTO dto = categoryService.findById(categoryId);
    return categoryMapper.toResponse(dto);
  }

  @GetMapping("/categories/cnt")
  @Operation(summary = "특정 유저의 전체 카테고리 수를 조회한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Integer.class)))
  })
  public ResponseEntity<Integer> getCategoryCntByMember(
      @RequestParam(value = "memberId") Long memberId
  ) {
    Integer response = categoryService.getCategoryCntByMember(memberId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/categories/chrome-extension")
  @Operation(summary = "[크롬 익스텐션] 특정 유저의 카테고리 목록을 조회한다.", description = "페이지네이션이 적용되어 있다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = PageResponse.class)))
  })
  public PageResponse<CategoryDTO> getCategoriesByMemberForExtension(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @NotBlank(message = "암호화된 유저 ID를 입력해주세요.") final String memberId,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "1")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    ExtensionKey key = encryptService.getKey();
    return categoryService.getCategoriesWithPagingByMember(cursorId, pageSize, key.decrypt(memberId));
  }

}
