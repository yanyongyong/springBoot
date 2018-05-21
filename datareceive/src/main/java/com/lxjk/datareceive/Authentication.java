package com.lxjk.datareceive;

import com.lxjk.datareceive.jwt.JwtAuthenticationFilter;
import com.lxjk.datareceive.jwt.JwtUtil;
import com.lxjk.datareceive.response.RetCode;
import com.lxjk.datareceive.response.RetResponse;
import com.lxjk.datareceive.response.RetResult;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: ziv
 * @Date: 2018/5/17 17:43
 * @Description:
 */
@RestController
public class Authentication {

    @PostMapping("/login")
    public RetResult<String> login(HttpServletResponse response, @RequestBody final Account account) throws IOException {
        if(isValidPassword(account)) {
            String jwt = JwtUtil.generateToken(account.username);
            return RetResponse.makeOKRsp(jwt);
//            return new HashMap<String,String>(){{
//                put("token", jwt);
//            }};
        }else {
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            return RetResponse.makeErr(RetCode.UNAUTHORIZED,"token失效或未登陆");
        }
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        registrationBean.setFilter(filter);
        return registrationBean;
    }

    private boolean isValidPassword(Account ac) {
        return "admin".equals(ac.username)
                && "admin".equals(ac.password);
    }


    public static class Account {
        public String username;
        public String password;
    }
}
