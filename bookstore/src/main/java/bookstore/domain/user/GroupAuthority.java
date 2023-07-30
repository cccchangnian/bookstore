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
@Table(name = "group_authorities")
public class GroupAuthority implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5792431421556924268L;

	// 组群权限主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 组群权限名
	@NotNull
	private String authority;
	
	// 对应组群
	@ManyToOne(targetEntity = Group.class, optional = false)
	@JoinColumn(name = "group_id")
	private Group group;
}
