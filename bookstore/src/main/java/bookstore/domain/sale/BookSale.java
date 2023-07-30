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
@Table(name = "book_sales")
public class BookSale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1531966069652008663L;

	// 图书销售记录主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 图书主键
	@NotNull
	@Column(name = "book_id")
	private String bookId;
	
	// 购买者主键
	@NotNull
	@Column(name = "buyer_id")
	private long buyerId;
	
	// 购买日期
	@Column(name = "purchase_date")
	private Date purchaseDate = new Date();
}
