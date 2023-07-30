package bookstore.domain.reserve;

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

import bookstore.domain.sale.BookInventory;
import bookstore.domain.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "book_reserve")
public class BookReserve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723318194320070741L;

	// 图书预订主键 由算法自动生成
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-book-reserve-id")
	@GenericGenerator(name = "custom-book-reserve-id", strategy = "bookstore.domain.reserve.strategy.CustomBookReserveIdGenerator")
	private String id;

	// 预订的图书是否已送达
	@Column(name = "is_completed")
	private boolean isCompleted = false;
	
	@Min(value = 0, message = "pre price must be at least 0!")
	@Column(name = "pre_price")
	private BigDecimal prePrice = new BigDecimal(0);

	@Min(value = 0, message = "total price must be at least 0!")
	@Column(name = "total_price")
	private BigDecimal totalPrice = new BigDecimal(0);
	
	// 预订数量
	@Min(value = 0, message = "amount must be at least 0!")
	private int amount = 0;

	@NotNull
	private String phone = "";

	@NotNull
	private String location = "";
	
	// 订单创建时间
	@Column(name = "created_time")
	private Date createdTime = new Date();
	
	// 订单所有者
	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	// 预订的销售图书库存
	@ManyToOne(targetEntity = BookInventory.class, optional = false)
	@JoinColumn(name = "inventory_id")
	private BookInventory inventory;
}
