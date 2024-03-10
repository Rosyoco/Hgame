package com.hgame.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSON;
import com.hgame.result.Result;

import lombok.extern.slf4j.Slf4j;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

	public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String requestURI = req.getRequestURI();

		log.info("拦截到请求: {}", requestURI);

		String[] urls = new String[] {
				"/user/login",
				"/user/logout",
				"/login.html",
				"/product-dev.html",
				"/js/**",
				"/css/**",
				"/images/**",
				"/favicon.ico"
		};

		boolean check = check(urls, requestURI);


		if(check) {
			log.info("本次请求{}不需要处理", requestURI);
			chain.doFilter(req, resp);
			return;
		}

		if(req.getSession().getAttribute("username") != null) {
			log.info("用户已登录,用户ID为{}", req.getSession().getAttribute("username"));
			chain.doFilter(req, resp);
			return;
		}

		log.info("用户未登录");
//		resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        //如果验证失败,重定向到login.html
//        resp.sendRedirect("/login.html");
		return;
	}

	public boolean check(String[] urls, String requestURL) {
		for (String url : urls) {
			boolean match = PATH_MATCHER.match(url, requestURL);
			if(match) {
				return true;
			}
		}
		return false;
	}

}
