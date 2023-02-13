package org.pickly.service.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.comment.repository.interfaces.CommentRepository;
import org.pickly.service.comment.service.interfaces.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

}
