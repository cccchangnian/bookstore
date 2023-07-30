package bookstore.domain.user;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	// 用户主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 用户名 唯一的
	@NotNull
	private String username;

	// 密码 密文存储
	@Size(min = 6, message = "password must be at least 6 characters!")
	private String password;

	// 用户状态
	private boolean enabled = true;

	// 姓
	@Column(name = "first_name")
	private String firstName = "";

	// 名
	@Column(name = "last_name")
	private String lastName = "";

	// 性别 规定 0 unknown 1 male 2 female
	private short gender = 0;

	// 手机号
	private String phone = "";
	
	// 邮箱
	@Email
	private String email;

	// 头像
	private String icon = "";
	
	// 所在地
	private String location = "";

	// 生日
	private Date birthday = new Date();

	// 加入日期
	@Column(name = "joined_date")
	private Date joinedDate = new Date();

	// 用户所拥有的权限
	@OneToMany(targetEntity = Authority.class, mappedBy = "user", cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	private List<Authority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> auths = new LinkedList<>();
		for (Authority authority : this.authorities) {
			auths.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}
		return auths;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}
}