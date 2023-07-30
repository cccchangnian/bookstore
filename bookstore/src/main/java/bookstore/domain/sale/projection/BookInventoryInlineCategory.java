package bookstore.domain.sale.projection;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import bookstore.domain.sale.BookCategory;
import bookstore.domain.sale.BookInventory;

//数据投影，以定制在查询BookInventory实体时返回的数据内容。
//投影接口是Spring Data REST的特性
@Projection(name = "inlineCategory", types = { BookInventory.class })
public interface BookInventoryInlineCategory {

	long getId();
	
	String getBookName();
	
	BigDecimal getPrice();
	
	String getIcon();
	
	double getRating();
	
	String getDescription();
	
	String getAuthor();
	
	long getAmount();
	
	//将BookCategory实体的部分属性合并到BookInventory实体的投影中，从而在一次查询中返回两个实体的部分属性，实现了关联数据的展示。
	BookCategory getCategory();
	
	Date getCreatedTime();
}
