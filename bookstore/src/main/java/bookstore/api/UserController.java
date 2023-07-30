package bookstore.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bookstore.domain.user.User;
import bookstore.form.EmailForm;
import bookstore.form.EmailUpdateForm;
import bookstore.form.RetrievePasswordForm;
import bookstore.props.UserProps;
import bookstore.repository.user.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserProps userProps;
	
	@Autowired
	private RepositoryEntityLinks repositoryEntityLinks;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	// 上传头像
	@PatchMapping(path = "/api/users/{id}/icon", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<EntityModel<User>> uploadIcon(@PathVariable("id") long id, MultipartFile file) throws IOException {
		Optional<User> optional = userRepo.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();
			// get base path
			String basePath = userProps.getBasePath();
			File folder = new File(basePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			// try to delete the original file
			String oldFileUrlPath = user.getIcon();
			if (null != oldFileUrlPath && !oldFileUrlPath.isEmpty()) {
				File oldFile = new File(basePath + File.separator + oldFileUrlPath.substring(oldFileUrlPath.lastIndexOf("/") + 1));
				if (oldFile.exists()) {
					oldFile.delete();
				}
			}
			//save file
			String oldName = file.getOriginalFilename();
			String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
			file.transferTo(new File(folder, newName));
			
			// save user
			user.setIcon(userProps.getUrlBasePath() + newName);
			userRepo.save(user);
			
			// return resource
			Link link = repositoryEntityLinks.linkToItemResource(User.class, id);
			EntityModel<User> userEntity = EntityModel.of(user, link);
			return new ResponseEntity<>(userEntity, HttpStatus.OK);
		} else {
			throw new UsernameNotFoundException("User '" + id + "' not found!");
		}
	}
	
	// 获取头像
	@GetMapping(path = "/api/upload/user/icon/{filename}", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public BufferedImage getImage(@PathVariable("filename") String filename) throws IOException {
		File file = new File(userProps.getBasePath(), filename);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			BufferedImage image = ImageIO.read(in);
			in.close();
			return image;
		} else {
			return null;
		}
	}
	
	// 发送邮件
	//POST请求的映射，对应的路径是/api/email
	//produces指定响应的媒体类型为application/hal+json，表示返回的响应是JSON格式的数据。
	@PostMapping(path = "/api/email", produces = "application/hal+json")
	public void sendEmail(@Valid @RequestBody EmailForm form) {
		//@Valid注解用于验证EmailForm对象的属性是否符合约束
		//@RequestBody注解用于从请求体中获取EmailForm对象的数据
		SimpleMailMessage message = new SimpleMailMessage(); //创建一个SimpleMailMessage对象，用于构造简单的邮件消息
		message.setFrom("2864710463@qq.com"); //设置发件人的邮箱地址，这里使用的是QQ邮箱
		message.setTo(form.getEmail()); //设置收件人的邮箱地址，通过EmailForm对象获取
		message.setSubject("Please Verify your email!"); //设置邮件主题
		StringBuffer content = new StringBuffer();
		Random random = new Random();
		//生成6位随机数字作为验证码
		for (int i = 0; i < 6; ++i) {
			content.append(random.nextInt(10));
		}
		message.setText(content.toString()); //设置邮件内容为生成的验证码。
		try {
			javaMailSender.send(message); //使用javaMailSender发送邮件消息
			ValueOperations<String, String> valueOperations = redisTemplate.opsForValue(); //获取redisTemplate中用于操作字符串值的ValueOperations对
			valueOperations.set(form.getEmail(), content.toString(), Duration.ofMinutes(3)); //将生成的验证码存储在Redis中，以邮箱地址为键，验证码为值，并设置过期时间为3分钟。
		} catch (MailException mailException) {
			throw mailException;
		}
	}
	
	// 更新邮箱
	@PatchMapping(path = "/api/users/{id}/email", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EntityModel<User>> updateEmail(@PathVariable("id") long id,
			@Valid @RequestBody EmailUpdateForm form) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String code = valueOperations.get(form.getEmail());
		if (code.equals(form.getVerificationCode())) {
			Optional<User> optional = userRepo.findById(id);
			if (optional.isPresent()) {
				User user = optional.get();
				user.setEmail(form.getEmail());
				userRepo.save(user);
				Link link = repositoryEntityLinks.linkToItemResource(User.class, user.getUsername());
				EntityModel<User> userEntity = EntityModel.of(user, link);
				return new ResponseEntity<>(userEntity, HttpStatus.OK);
			} else {
				throw new UsernameNotFoundException("User '" + id + "' not found!");
			}
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}
	
	// 找回密码
	@PostMapping(path = "/api/password/retrieve", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> updatePassword(@Valid @RequestBody RetrievePasswordForm form) {
		// This method is used to reset a forgotten password
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String code = valueOperations.get(form.getEmail());
		if (code.equals(form.getVerificationCode())) {
			Optional<User> optional = userRepo.findByUsername(form.getUsername());
			if (optional.isPresent()) {
				User user = optional.get();
				// need to encrypt password
				user.setPassword(passwordEncoder.encode(form.getPassword()));
				userRepo.save(user);
				return new ResponseEntity<>(null, HttpStatus.OK);
			} else {
				throw new UsernameNotFoundException("User '" + form.getUsername() + "' not found!");
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
	}
}
