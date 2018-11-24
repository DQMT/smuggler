package cn.tincat.smuggler;

import cn.tincat.smuggler.IPUtil.IPList;
import cn.tincat.smuggler.IPUtil.IPUtil;
import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class WebConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${white.ip.list}")
    private String whiteIPList;

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    //将自定义Filter加入过滤链
    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }

    public class MyFilter implements Filter {
        @Override
        public void destroy() {
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) srequest;
            String ip = IPUtil.getClientIpAddr(request);
            IPList ipList = new IPList(whiteIPList);
            String log = "this is MyFilter,url :" + request.getRequestURI();
            logger.info(log);
            logger.info("client ip:"+ip);
            if (ipList.isValidIP(ip)) {
                filterChain.doFilter(srequest, sresponse);
            }
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
        }
    }
}