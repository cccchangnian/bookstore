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
@Table(name = "rented_book_return")
public class RentedBookReturn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1841203560346003981L;

	// 租借图书规划主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 租借图书主键
	@NotNull
	@Column(name = "book_id")
	private String bookId;
	
	// 所有者主键
	@NotNull
	@Column(name = "owner_id")
	private long ownerId;
	
	// 归还时间
	@Column(name = "return_time")
	private Date returnTime = new Date();
}
