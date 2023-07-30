package bookstore.domain.sale;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "book_categories")
public class BookCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4682016180710637037L;

	// 分类主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 分类名
	@NotNull
	private String name;
	
	// 分类创建日期
	@Column(name = "created_time")
	private Date createdTime = new Date();
}
