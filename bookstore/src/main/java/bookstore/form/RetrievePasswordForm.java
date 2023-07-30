package bookstore.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RetrievePasswordForm {

	// 找回密码用类
	
	@NotNull
	private String username;
	
	@Size(min=6, message="password must be at least 6 characters!")
	private String password;
	
	@Email
	private String email;
	
	@NotNull
	private String verificationCode;
}

