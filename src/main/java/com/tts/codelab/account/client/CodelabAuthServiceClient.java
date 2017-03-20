package com.tts.codelab.account.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tts.codelab.account.domain.User;

@FeignClient(name = "codelab-auth-service")
public interface CodelabAuthServiceClient {

    @RequestMapping(path = "/uaa/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void createUser(User user);

    @RequestMapping(path = "/uaa/users", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void updateUser(User user);
}
