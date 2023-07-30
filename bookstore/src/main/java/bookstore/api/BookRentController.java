package bookstore.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bookstore.domain.rent.RentedBookInventory;
import bookstore.domain.sale.BookInventory;
import bookstore.domain.sale.BookShoppingCart;
import bookstore.form.InventoryForm;
import bookstore.props.RentProps;
import bookstore.repository.rent.RentedBookInventoryRepository;

@RestController
public class BookRentController {

	@Autowired
	private RedisTemplate<String, HashMap<String, Long>> redisTemplateTemp;
	
	@Autowired
	private RentedBookInventoryRepository rentedBookInventoryRepository;

	@Autowired
	private RentProps rentProps;
	
	@Autowired
	private RepositoryEntityLinks repositoryEntityLinks;
	
	// 上传图书图片
	@PatchMapping(value = "/api/rentedBookInventories/{id}/icon", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<EntityModel<RentedBookInventory>> uploadIcon(@PathVariable("id") long id, MultipartFile file) throws IOException {
		Optional<RentedBookInventory> optional = rentedBookInventoryRepository.findById(id);
		if (optional.isPresent()) {
			RentedBookInventory bookInventory = optional.get();
			// get basePath
			String basePath = rentProps.getBasePath();
			File folder = new File(basePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			// try to delete the original file
			String oldFileUrlPath = bookInventory.getIcon();
			if (null != oldFileUrlPath && !oldFileUrlPath.isEmpty()) {
				File oldFile = new File(basePath + File.separator + oldFileUrlPath.substring(oldFileUrlPath.lastIndexOf("/") + 1));
				if (oldFile.exists()) {
					oldFile.delete();
				}
			}
			// save file
			String oldName = file.getOriginalFilename();
			String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
			file.transferTo(new File(folder, newName));
			
			// save bookInventory
			bookInventory.setIcon(rentProps.getUrlBasePath() + newName);
			rentedBookInventoryRepository.save(bookInventory);
			
			// return resource
			Link link = repositoryEntityLinks.linkToItemResource(BookInventory.class, id);
			EntityModel<RentedBookInventory> bookInventoryEntity = EntityModel.of(bookInventory, link);
			return new ResponseEntity<>(bookInventoryEntity, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	// 获取商品图片
	@GetMapping(path = "/api/upload/rent/icon/{filename}", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public BufferedImage getImage(@PathVariable("filename") String filename) throws IOException {
		File file = new File(rentProps.getBasePath(), filename);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			BufferedImage image = ImageIO.read(in);
			in.close();
			return image;
		} else {
			return null;
		}
	}
	
	@PostMapping(value = "/api/users/{id}/rent/tempShoppingCart")
	public void addToRentTempShoppingCart(@PathVariable("id") Long id, @Valid @RequestBody InventoryForm form) {
		// 将数据存储在redis
		ValueOperations<String, HashMap<String, Long>> valueOperations = redisTemplateTemp.opsForValue();
		// 不管有没有键 都会覆盖
		HashMap<String, Long> map = new HashMap<String, Long>(2);
		map.put("inventoryId", form.getInventoryId());
		map.put("amount", form.getAmount());
		// 时间设置为十分钟
		valueOperations.set("userTempRent" + String.valueOf(id), map, Duration.ofMinutes(10));
	}
	
	@GetMapping(value = "/api/users/{id}/rent/tempShoppingCart", produces = { MediaType.APPLICATION_JSON_VALUE })
	public BookShoppingCart getTempShoppingCart(@PathVariable("id") Long id) {
		// 从redis中获取数据
		ValueOperations<String, HashMap<String, Long>> valueOperations = redisTemplateTemp.opsForValue();
		HashMap<String, Long> map = valueOperations.get("userTempRent" + String.valueOf(id));
		if (null != map) {
			Long inventoryId = map.get("inventoryId");
			Optional<RentedBookInventory> optional = rentedBookInventoryRepository.findById(inventoryId);
			if (optional.isPresent()) {
				RentedBookInventory bookInventory = optional.get();
				String bookName = bookInventory.getBookName();
				BigDecimal price = bookInventory.getPrice();
				String author = bookInventory.getAuthor();
				Long amount = map.getOrDefault("amount", new Long(0L));
				// to json
				BookShoppingCart bookShoppingCart = new BookShoppingCart();
				bookShoppingCart.setInventoryId(inventoryId);
				bookShoppingCart.setBookName(bookName);
				bookShoppingCart.setPrice(price);
				bookShoppingCart.setAmount(amount);
				bookShoppingCart.setAuthor(author);
				return bookShoppingCart;
			}
		}
		return null;
	}
}
