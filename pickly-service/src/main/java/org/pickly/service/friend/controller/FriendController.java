package org.pickly.service.friend.controller;

import lombok.RequiredArgsConstructor;
import org.pickly.service.friend.service.interfaces.FriendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FriendController {

  private final FriendService friendService;

}
