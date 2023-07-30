package bookstore.form;

import javax.validation.constraints.Email;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmailForm {
	
	// 此类用于对邮箱做验证 接受提交上来的邮箱

	@Email
	private String email;
}
