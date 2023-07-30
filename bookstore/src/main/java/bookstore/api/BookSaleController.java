package bookstore.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bookstore.domain.sale.BookInventory;
import bookstore.domain.sale.BookShoppingCart;
import bookstore.form.InventoryForm;
import bookstore.form.ShoppingCartDeleteForm;
import bookstore.props.SaleProps;
import bookstore.repository.sale.BookInventoryRepository;

@RestController
public class BookSaleController {
	
	@Autowired
	private RedisTemplate<String, LinkedList<HashMap<String, Long>>> redisTemplate;
	
	@Autowired
	private RedisTemplate<String, HashMap<String, Long>> redisTemplateTemp;
	
	@Autowired
	private BookInventoryRepository bookInventoryRepository;
	
	@Autowired
	private SaleProps saleProps;
	
	@Autowired
	private RepositoryEntityLinks repositoryEntityLinks;
	
	// 上传图书图片
	@PatchMapping(value = "/api/bookInventories/{id}/icon", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<EntityModel<BookInventory>> uploadIcon(@PathVariable("id") long id, MultipartFile file) throws IOException {
		Optional<BookInventory> optional = bookInventoryRepository.findById(id);
		if (optional.isPresent()) {
			BookInventory bookInventory = optional.get();
			// get basePath
			String basePath = saleProps.getBasePath();
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
			bookInventory.setIcon(saleProps.getUrlBasePath() + newName);
			bookInventoryRepository.save(bookInventory);
			
			// return resource
			Link link = repositoryEntityLinks.linkToItemResource(BookInventory.class, id);
			EntityModel<BookInventory> bookInventoryEntity = EntityModel.of(bookInventory, link);
			return new ResponseEntity<>(bookInventoryEntity, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	// 获取商品图片
	@GetMapping(path = "/api/upload/sale/icon/{filename}", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public BufferedImage getImage(@PathVariable("filename") String filename) throws IOException {
		File file = new File(saleProps.getBasePath(), filename);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			BufferedImage image = ImageIO.read(in);
			in.close();
			return image;
		} else {
			return null;
		}
	}
	
	
	// 加入购物车
	@PostMapping(value = "/api/users/{id}/shoppingCart", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void addToShoppingCart(@PathVariable("id") Long id, @Valid @RequestBody InventoryForm form) {
		// get list from redis by user id
		ValueOperations<String, LinkedList<HashMap<String, Long>>> valueOperations = redisTemplate.opsForValue();
		LinkedList<HashMap<String, Long>> list = valueOperations.get("user" + String.valueOf(id));
		if (null == list) {
			list = new LinkedList<HashMap<String, Long>>();
		}
		// compare form's inventory id and map's id
		boolean flag = false;
		Iterator<HashMap<String, Long>> iterator = list.iterator();
		while (iterator.hasNext()) {
			HashMap<String, Long> map = iterator.next();
			// key is exists
			if (map.getOrDefault("inventoryId", new Long(1L)).longValue() == form.getInventoryId().longValue()) {
				map.put("amount", map.getOrDefault("amount", new Long(0L)) + form.getAmount());
				// break
				flag = true;
				break;
			}
		}
		if (flag == false) {
			// add a new inventory
			HashMap<String, Long> map = new HashMap<String, Long>(2);
			map.put("inventoryId", form.getInventoryId());
			map.put("amount", form.getAmount());
			list.add(map);
		}
		// write to redis
		valueOperations.set("user" + String.valueOf(id), list);
		// return JSONObject
	}
	
	// 获取购物车信息
	@GetMapping(value = "/api/users/{id}/shoppingCart", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<BookShoppingCart> getShoppingCart(@PathVariable("id") Long id) {
		// get list from redis
		ValueOperations<String, LinkedList<HashMap<String, Long>>> valueOperations = redisTemplate.opsForValue();
		LinkedList<HashMap<String, Long>> list = valueOperations.get("user" + String.valueOf(id));
		if (null == list) {
			list = new LinkedList<HashMap<String, Long>>();
		}
		// create a shopping list
		List<BookShoppingCart> manifest = new LinkedList<BookShoppingCart>();
		// get iterator
		Iterator<HashMap<String, Long>> iterator = list.iterator();
		while (iterator.hasNext()) {
			HashMap<String, Long> map = iterator.next();
			// get all inventory message
			Long inventoryId = map.get("inventoryId");
			Optional<BookInventory> optional = bookInventoryRepository.findById(inventoryId);
			if (optional.isPresent()) {
				BookInventory bookInventory = optional.get();
				String bookName = bookInventory.getBookName();
				BigDecimal price = bookInventory.getPrice();
				String author = bookInventory.getAuthor();
				String icon = bookInventory.getIcon();
				double rating = bookInventory.getRating();
				String description = bookInventory.getDescription();
				Long amount = map.getOrDefault("amount", new Long(0L));
				// to json
				BookShoppingCart bookShoppingCart = new BookShoppingCart();
				bookShoppingCart.setInventoryId(inventoryId);
				bookShoppingCart.setBookName(bookName);
				bookShoppingCart.setPrice(price);
				bookShoppingCart.setAmount(amount);
				bookShoppingCart.setAuthor(author);
				bookShoppingCart.setIcon(icon);
				bookShoppingCart.setRating(rating);
				bookShoppingCart.setDescription(description);
				manifest.add(bookShoppingCart);
			}
		}
		return manifest;
	}
	
	// 从购物车中删除指定商品
	@DeleteMapping(value = "/api/users/{id}/shoppingCart")
	public void deleteFromShoppingCart(@PathVariable("id") Long id, @Valid @RequestBody ShoppingCartDeleteForm form) {
		// get list from redis
		ValueOperations<String, LinkedList<HashMap<String, Long>>> valueOperations = redisTemplate.opsForValue();
		LinkedList<HashMap<String, Long>> list = valueOperations.get("user" + String.valueOf(id));
		if (null == list) {
			list = new LinkedList<HashMap<String, Long>>();
		}
		// compare and delete
		Iterator<HashMap<String, Long>> iterator = list.iterator();
		while (iterator.hasNext()) {
			HashMap<String, Long> map = iterator.next();
			// get inventory id
			Long inventoryId = map.get("inventoryId");
			if (inventoryId.longValue() == form.getInventoryId().longValue()) {
				iterator.remove();
				break;
			}
		}
		// write the left to redis
		valueOperations.set("user" + String.valueOf(id), list);
	}
	
	// 点击立即购买 将商品加入到临时购物车
	@PostMapping(value = "/api/users/{id}/tempShoppingCart")
	public void addToTempShoppingCart(@PathVariable("id") Long id, @Valid @RequestBody InventoryForm form) {
		// get map from redis by user id
		ValueOperations<String, HashMap<String, Long>> valueOperations = redisTemplateTemp.opsForValue();
		// Whether or not there is data in Redis, it will be overwritten
		HashMap<String, Long> map = new HashMap<String, Long>(2);
		map.put("inventoryId", form.getInventoryId());
		map.put("amount", form.getAmount());
		valueOperations.set("userTemp" + String.valueOf(id), map, Duration.ofMinutes(10));
	}
	
	// 从临时购物车中取出商品
	@GetMapping(value = "/api/users/{id}/tempShoppingCart", produces = { MediaType.APPLICATION_JSON_VALUE })
	public BookShoppingCart getFromTempShoppingCart(@PathVariable("id") Long id) {
		// get map from redis by user id
		ValueOperations<String, HashMap<String, Long>> valueOperations = redisTemplateTemp.opsForValue();
		HashMap<String, Long> map = valueOperations.get("userTemp" + String.valueOf(id));
		if (null != map) {
			Long inventoryId = map.get("inventoryId");
			Optional<BookInventory> optional = bookInventoryRepository.findById(inventoryId);
			if (optional.isPresent()) {
				BookInventory bookInventory = optional.get();
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
