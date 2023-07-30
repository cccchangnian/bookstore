package bookstore.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmailUpdateForm {

	// 此类用于更新邮箱 需要邮箱和验证码
	
	@Email
	private String email;
	
	@NotNull
	private String verificationCode;
}
