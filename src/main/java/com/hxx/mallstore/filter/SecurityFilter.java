package com.hxx.mallstore.filter;

import com.alibaba.fastjson.JSON;
import com.hxx.mallstore.util.JsonResult;
import com.hxx.mallstore.util.MallStore;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录限制过滤器:
 */
public class SecurityFilter implements Filter {

    //将redis模板定义为其成员变量
    private StringRedisTemplate redisTemplate;

    //成员变量redis模板的set方法
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 过滤器拦截到请求执行的方法:
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        //获取请求url接口
        String path = request.getServletPath();
        /*
          白名单请求都直接放行:
         */
        List<String> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/product/img-upload");
        urlList.add("/bootstrap3/**");
        urlList.add("/css/**");
        urlList.add("/images/**");
        urlList.add("/js/**");
        urlList.add("/web/register.html");
        urlList.add("/web/login.html");
        urlList.add("/web/index.html");
        urlList.add("/web/product.html");
        urlList.add("/users/reg");
        urlList.add("/users/login");
        urlList.add("/districts/**");
        urlList.add("/products/**");

        //对static下的/img/upload中的静态资源图片的访问直接放行
        if(urlList.contains(path)){
            chain.doFilter(request, response);
            return;
        }

        /*
          其它请求都校验token:
         */
        //拿到前端归还的token
//        String clientToken = request.getHeader(MallStore.HEADER_TOKEN_NAME);
//        //校验token,校验通过请求放行
//        if(StringUtils.hasText(clientToken)&&redisTemplate.hasKey(clientToken)){
//            chain.doFilter(request, response);
//            return;
//        }
//        //校验失败,向前端响应失败的Result对象转成的json串
//        JsonResult result = new JsonResult(JsonResult.CODE_ERR_UNLOGINED, "请登录！");
//        String jsonStr = JSON.toJSONString(result);
//        response.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        out.print(jsonStr);
//        out.flush();
//        out.close();
    }

}
