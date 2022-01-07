package com.mydata.crm.web.filter;

import com.mydata.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("进入到验证有没有登录过的过滤器");
        //我们需要用的request和response的方法。是向下转型，需要强制类型转换
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
        Object obj = request.getSession().getAttribute("user");
        //
        String path=request.getServletPath();
        System.out.println("path===="+path);
        if("/login.jsp".equals(path)||"/settings/user/login.do".equals(path)){
            chain.doFilter(req,resp);
        }else {
            if(obj!=null){
                chain.doFilter(req,resp);
            }else {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }

    }

    public void destroy() {
    }
}
