package com.soon83.springdatajpa.interfaces;

import com.soon83.springdatajpa.dto.Res;
import com.soon83.springdatajpa.service.UserService;
import com.soon83.springdatajpa.utils.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Res<UserResponse>> createUser(@RequestBody UserDto userDto) throws URISyntaxException {

        Long createdUserId = userService.createUser(userDto);

        return ResponseEntity
                .created(HttpUtil.getCurrentUri(createdUserId))
                .body(Res.success(new UserResponse(createdUserId)));
    }

    @GetMapping
    public ResponseEntity<Res<List<UserResponse>>> findAllUsers() {

        List<UserResponse> userResponses = userService.findAllUsers();

        return ResponseEntity.ok(Res.success(userResponses));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Res<UserResponse>> findUserById(@PathVariable Long userId) {

        UserResponse userResponse = userService.findUserById(userId);

        return ResponseEntity.ok(Res.success(userResponse));
    }

}

