package org.pickly.service.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookmarkController {

  private final BookmarkService bookmarkService;

}
