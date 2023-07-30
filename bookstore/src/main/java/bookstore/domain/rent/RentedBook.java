package bookstore.domain.rent;

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
@Table(name = "rented_books")
public class RentedBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303860758360336582L;

	// 可租借图书识别码 由算法自动生成
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-rented-book-id")
	// @GenericGenerator(name = "custom-rented-book-id", strategy = "bookstore.domain.rent.strategy.CustomRentedBookIdGenerator")
	private String id;
	
	// 可租借图书名 实则和对应库存中的图书名相同 此处冗余了 可以废弃
	@NotNull
	private String name;
	
	// 是否被租借 默认是false
	@Column(name = "is_rent")
	private boolean isRent = false;
	
	// 可租借图书录入时间
	@Column(name = "created_time")
	private Date createdTime = new Date();
	
	// 图书对应库存 从库存中可以得知很多信息 比如图书名
	@ManyToOne(targetEntity = RentedBookInventory.class, optional = false)
	@JoinColumn(name = "inventory_id")
	private RentedBookInventory inventory;
}
