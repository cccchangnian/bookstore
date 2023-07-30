package bookstore.domain.rent;

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
@Table(name = "rented_book_categories")
public class RentedBookCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2311703488485707230L;

	// 租借图书分类主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 租借图书分类名
	@NotNull
	private String name;
	
	// 租借图书分类创建日期
	@Column(name = "created_time")
	private Date createdTime = new Date();
}
