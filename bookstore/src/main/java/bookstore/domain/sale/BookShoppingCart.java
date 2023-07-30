package bookstore.domain.sale;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookShoppingCart {
	
	// 购物车 数据库中不保存购物车信息 因此没有映射关系 购物车保存在redis

	private Long inventoryId;
	
	private String bookName;
	
	private BigDecimal price;
	
	private Long amount;
	
	private String author;

	private String icon;

	private double rating;

	private String description;
}
