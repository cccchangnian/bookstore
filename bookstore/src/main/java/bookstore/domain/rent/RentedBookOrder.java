package bookstore.domain.rent;

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
@Table(name = "rented_book_order")
public class RentedBookOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6097840575480646142L;

	// 订单号 由算法自动生成 租借的订单号在前端生成 因为需要展示给用户看
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-rented-book-order-id")
	// @GenericGenerator(name = "custom-rented-book-order-id", strategy = "bookstore.domain.rent.strategy.CustomRentedBookOrderIdGenerator")
	private String id;
	
	// 定金
	@Min(value = 0, message = "pre price must be at least 0!")
	@Column(name = "pre_price")
	private BigDecimal prePrice;
	
	// 租借产生的实际金额 默认为0
	@NotNull
	@Column(name = "total_price")
	private BigDecimal totalPrice = new BigDecimal(0);
	
	// 订单内所有图书每周产生的金额
	@NotNull
	@Min(value = 0, message = "price must be at least 0!")
	private BigDecimal price;
	
	// 收获地址 不一定和用户地址相同
	@NotNull
	private String location;
	
	@NotNull
	private String phone;

	// 订单创建日期
	@Column(name = "created_time")
	private Date createdTime = new Date();
	
	// 订单持有者
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "owner_id")
	private User owner;
}
