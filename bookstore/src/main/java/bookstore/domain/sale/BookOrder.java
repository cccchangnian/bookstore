package bookstore.domain.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import bookstore.domain.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "book_order")
public class BookOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2344068535195926955L;

	// 销售图书订单主键 由算法自动生成
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-book-order-id")
	// @GenericGenerator(name = "custom-book-order-id", strategy = "bookstore.domain.sale.strategy.CustomBookOrderIdGenerator")
	private String id;
	
	
	// 订单产生的总交易额
	@Min(value = 0, message = "total price must be at least 0!")
	@Column(name = "total_price")
	private BigDecimal totalPrice;
	
	// 收货地址
	@NotNull
	private String location;

	@NotNull
	private String phone;
	
	// 订单生成日期
	@Column(name = "created_time")
	private Date createdTime = new Date();
	
	// 图书购买者
	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "buyer_id")
	private User buyer;
}
