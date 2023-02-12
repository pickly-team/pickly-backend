package org.pickly.service.comment.controller;

import lombok.RequiredArgsConstructor;
import org.pickly.service.comment.service.interfaces.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

  private final CommentService commentService;

}
