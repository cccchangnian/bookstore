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
@Table(name = "rented_book_rent")
public class RentedBookRent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2514929006668315290L;

	// 租借图书出租记录主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 租借图书主键
	@NotNull
	@Column(name = "book_id")
	private String bookId;
	
	// 拥有者主键
	@NotNull
	@Column(name = "owner_id")
	private long ownerId;
	
	// 借书时间
	@Column(name = "borrow_time")
	private Date borrowTime = new Date();
}
