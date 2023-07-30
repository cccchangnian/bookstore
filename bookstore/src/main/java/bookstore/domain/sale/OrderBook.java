package bookstore.domain.sale;

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
@Table(name = "order_books")
public class OrderBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7134677755721317212L;

	// 每产生一个订单 就会生成多个记录
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 订单号
	@NotNull
	@Column(name = "order_id")
	private String orderId;
	
	// 图书号
	@NotNull
	@Column(name = "book_id")
	private String bookId;
}
