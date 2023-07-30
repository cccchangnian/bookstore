package bookstore.domain.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority implements Serializable {
	
	private static final long serialVersionUID = -1271128647094978200L;

	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 权限名 限定 比如 ROLE_USER ROLE_ADMIN
	@NotNull
	private String authority;
	
	// 对应的用户
	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "user_id")
	private User user;
}
