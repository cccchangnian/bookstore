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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "book_inventory")
public class BookInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700987618227123128L;

	// 销售图书库存主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// 销售图书库存名
	@NotNull
	@Column(name = "book_name")
	private String bookName;
	
	// 销售图书库存价格 是销售价格 默认为0
	@NotNull
	private BigDecimal price = new BigDecimal(0L);
	
	// 销售图书图标
	private String icon;
	
	// 销售图书评分
	@Min(value = 0, message = "rating must be at least 0!")
	@Max(value = 10, message = "rating must be at more 10!")
	private double rating = 0.0;
	
	// 销售图书简介
	private String description;
	
	// 销售图书作者
	private String author;
	
	// 销售图书库存量
	@Min(value = 0, message = "amount must be at least 0!")
	private long amount = 0L;
	
	// 创建时间
	@Column(name = "created_time")
	private Date createdTime = new Date();
	
	// 销售图书分类
	@ManyToOne(targetEntity = BookCategory.class, optional = false)
	@JoinColumn(name = "category_id")
	private BookCategory category;
}
