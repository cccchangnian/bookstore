package bookstore.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InventoryForm {

	// 添加进购物车需要的类 需要库存主键和数量 用户不需要是因为进行此操作时用户已经存在
	
	@NotNull
	private Long inventoryId;
	
	@Min(value = 0, message = "amount must be at least 0!")
	private Long amount;
}
