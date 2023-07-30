package bookstore.form;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ShoppingCartDeleteForm {

	// 从购物车中删除指定图书时需要用到
	
	@NotNull
	private Long inventoryId;
}
