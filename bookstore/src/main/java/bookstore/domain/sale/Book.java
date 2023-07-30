package bookstore.domain.sale;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "books")
public class Book implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4675638759029497073L;

	// 销售图书主键 由算法自动生成
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-book-id")
	// @GenericGenerator(name = "custom-book-id", strategy = "bookstore.domain.sale.strategy.CustomBookIdGenerator")
	private String id;

	// 图书名 和库存中的图书名保持一致
	@NotNull
	private String name;
	
	// 是否被卖出
	@Column(name = "is_sold")
	private boolean isSold = false;
	
	// 销售图书录入时间
	@Column(name = "created_time")
	private Date createdTime = new Date();
	
	// 销售图书对应库存
	@ManyToOne(targetEntity = BookInventory.class, optional = false)
	@JoinColumn(name = "inventory_id")
	private BookInventory inventory;
	
}
