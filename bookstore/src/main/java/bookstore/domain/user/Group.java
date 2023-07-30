package bookstore.domain.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "user_groups")
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -63681634856481619L;

	// 组群主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 组群名
	@NotNull
	@Column(name = "group_name")
	private String groupName;
	
	// 组群的权限集
	@OneToMany(targetEntity = GroupAuthority.class, mappedBy = "group", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private List<GroupAuthority> authorities;
	
	// 组群的用户集
	@ManyToMany(targetEntity = User.class)
	@JoinTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
}
