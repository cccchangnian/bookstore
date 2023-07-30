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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "rented_book_inventory")
public class RentedBookInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6957194112676832532L;

	// 租借图书库存主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// 租借图书库存图书名
	@NotNull
	@Column(name = "book_name")
	private String bookName;
	
	// 租借图书库存价格 单位为 人名币每周 默认为0
	@NotNull
	private BigDecimal price = new BigDecimal(0);
	
	// 租借图书库存图标 是一个url
	private String icon;
	
	// 租借图书库存评分
	@Min(value = 0, message = "rating must be at least 0!")
	@Max(value = 5, message = "rating must be at more 5!")
	private double rating = 0.0;
	
	// 租借图书库存简介
	private String description;
	
	// 租借图书库存作者
	private String author;
	
	// 租借图书库存总量
	@Min(value = 0L, message = "amount must be at least 0!")
	private long amount = 0L;
	
	// 租借库存创建时间
	@NotNull
	@Column(name = "created_time")
	private Date createdTime = new Date();

	// 租借图书库存分类
	@ManyToOne(targetEntity = RentedBookCategory.class, optional = false)
	@JoinColumn(name = "category_id")
	private RentedBookCategory category;
	
}
