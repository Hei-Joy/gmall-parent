package com.atguigu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.constant.SysCacheConstant;
import com.atguigu.gmall.to.CommonResult;
import com.atguigu.gmall.ums.entity.Member;
import com.atguigu.gmall.ums.service.MemberService;
import com.atguigu.gmall.vo.ums.LoginResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {


    @Autowired
    StringRedisTemplate redisTemplate;

    @Reference
    MemberService memberService;


    @ResponseBody
    @GetMapping("/userinfo")
    public CommonResult getUserInfo(@RequestParam("accessToken") String accessToken){
        //确定去redis中查询用户用的key
        String redisKey= SysCacheConstant.LOGIN_MEMBER+accessToken;
        String member = redisTemplate.opsForValue().get(redisKey);
        Member loginMember = JSON.parseObject(member, Member.class);
        loginMember.setId(null);
        loginMember.setPassword(null);
        return new CommonResult().success(loginMember);
    }


    /**
     * 这个方法是我们系统用的登录方法，只需要登录成功统一返回token即可
     * @return
     */
    @ResponseBody
    @PostMapping("/applogin")
    public CommonResult loginForGmall(@RequestParam("username") String username,
                                      @RequestParam("password") String password){
        Member member = memberService.login(username,password);

        if(member == null){
            //用户没有
            CommonResult result = new CommonResult().failed();
            result.setMessage("账号密码不匹配，请重新登录");
            return result;
        }else {
            String token = UUID.randomUUID().toString().replace("-", "");
            String memberJson = JSON.toJSONString(member);
            redisTemplate.opsForValue()
                    .set(SysCacheConstant.LOGIN_MEMBER+token,memberJson,
                            SysCacheConstant.LOGIN_MEMBER_TIMEOUT, TimeUnit.MINUTES);

            LoginResponseVo vo = new LoginResponseVo();
            BeanUtils.copyProperties(member,vo);
            //设置访问令牌
            vo.setAccessToken(token);
            return new CommonResult().success(vo);

        }


    }


    /**
     * 以下的方法是测试单点登录用的方法
     * @param redirec_url
     * @param ssoUser
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "redirec_url") String redirec_url,
                        @CookieValue(value = "sso_user",required = false) String ssoUser,
                        HttpServletResponse response,
                        Model model) throws IOException {
        System.out.println("认证中心开始认证.....");
        //1、判断之前是否登录过
        if(!StringUtils.isEmpty(ssoUser)){
            //登录过,回到之前的地方，并且把当前ssoserver获取到的cookie以url方式传递给其他域名【cookie同步】
            String url = redirec_url+"?"+"sso_user="+ssoUser;
            response.sendRedirect(url);
            return null;
        }else {
            //去redis验证，如果没有
            //没有登录过
            model.addAttribute("redirec_url",redirec_url);
            return "login";
        }
    }

    @PostMapping("/doLogin")
    public void doLogin(String username, String password,String redirec_url,
                        HttpServletResponse response,
                        HttpServletRequest request) throws IOException {
        //1、模拟用户的信息
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("email",username+"@qq.com");

        //2、以上标识用户登录; jwt
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(token,JSON.toJSONString(map));

        //3、登录成功。做两件事。
        //1）、命令浏览器把当前的token保存为cookie；  sso_user=token
        Cookie cookie = new Cookie("sso_user",token);
        response.addCookie(cookie);
        response.sendRedirect(redirec_url+"?"+"sso_user="+token);


        //2）、命令浏览器重定向到他之前的位置；
        //StringBuffer requestURL = request.getRequestURL();
        //System.out.println("将要去的地方是："+requestURL.toString());




    }
}
