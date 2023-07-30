package bookstore.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "props.user.icon")
@Data
public class UserProps {

	// 一些环境属性 保存图片时会用到 变量在application.yaml
	
	private String basePath = "";
	private String urlBasePath = "";
}
