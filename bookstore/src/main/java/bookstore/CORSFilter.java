package bookstore;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * This class exists to solve cross-domain problems
 * 这个类是用来解决跨域问题
 * @author 林日奋
 *
 */
@Configuration //声明该类是配置类
@Order(Ordered.HIGHEST_PRECEDENCE) //过滤器设置为具有最高优先级。这意味着这个过滤器会在其他过滤器之前先执行
@Profile("dev") //激活dev配置环境
//在 Servlet 容器中，过滤器可以用于在请求到达目标资源（如 Servlet 或 Controller）之前或之后执行一些操作。
public class CORSFilter implements Filter {


	//告诉编译器忽略未使用的警告信息
	@SuppressWarnings("unused")
	private FilterConfig config;

	//ServletRequest 表示一个客户端发送给服务器的 HTTP 请求。
	//它提供了访问请求的各种信息的方法，如请求的URL、请求的参数、请求头信息等。
	//通过 ServletRequest，Web 应用程序可以获取客户端发送的数据，并做相应的处理

	//ServletResponse 表示服务器对客户端的 HTTP 响应。
	//它提供了一系列方法，可以设置响应的状态码、响应头信息、响应内容等。
	//通过 ServletResponse，Web 应用程序可以向客户端发送数据，并响应客户端的请求。
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//强转是为了更方便使用 HttpServletRequest 和 HttpServletResponse 提供的方法，例如获取请求的URL、请求参数、请求头等。
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600"); //预检请求的有效期（单位为秒）
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
		if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
           httpServletResponse.setStatus(HttpServletResponse.SC_OK); //SC_OK = 200
        } else {
            chain.doFilter(request, response); //用于继续执行过滤器链中的下一个过滤器，或者如果没有下一个过滤器，则执行目标资源（通常是 Servlet 或静态资源）。
        }
	}
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }
}
