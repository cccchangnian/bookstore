package bookstore.domain.rent;

import java.io.Serializable;

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
@Table(name = "rented_order_books")
public class RentedOrderBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6906051548658027352L;

	// 一个订单可以有多个图书
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 订单主键
	@NotNull
	@Column(name = "order_id")
	private String orderId;
	
	// 图书主键
	@NotNull
	@Column(name = "book_id")
	private String bookId;
}
