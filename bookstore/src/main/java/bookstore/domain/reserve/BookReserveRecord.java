package bookstore.domain.reserve;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "book_reserve_record")
public class BookReserveRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3542616520003676220L;

	// 每有一个预订订单生成 都会产生一条记录
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 预订订单主键
	@NotNull
	@Column(name = "reserve_id")
	private String reserveId;
	
	// 用户主键
	@NotNull
	@Column(name = "user_id")
	private long userId;
	
	// 定金
	@Min(value = 0, message = "price must be at least 0!")
	private BigDecimal price = new BigDecimal(0L);
	
	// 记录生成日期
	@Column(name = "created_time")
	private Date createdTime = new Date();
}
