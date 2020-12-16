package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class HomeController {
    @Autowired
    private ApplicationArguments args;

    // X_FORWARDED_FOR
    private final static String X_FORWARDED_FOR = "x-forwarded-for";
    // PROXY_CLIENT_IP
    private final static String PROXY_CLIENT_IP = "Proxy-Client-IP";
    // WL_PROXY_CLIENT_IP
    private final static String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<String> home(HttpServletRequest request) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        StringBuilder result = new StringBuilder("session=" + request.getSession().getId());
        result.append("<br/>client=").append(getRemoteIp(request));
        result.append("<hr/>");
        result.append("server=").append(addr.getHostAddress());
        result.append("<br/>host=").append(addr.getHostName());

        for (String arg : args.getNonOptionArgs()) {
            result.append("<br/>").append(arg);
        }
        result.append("<hr/><a href='/clear'>clear</a>");

        return ResponseEntity.ok(result.toString());
    }

    @GetMapping("/clear")
    public String clear(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    private static boolean checkIP(String ip) {
        return ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip);
    }

    private static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (checkIP(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (checkIP(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
