/* 用户表 */
CREATE TABLE users (
	id bigint PRIMARY KEY AUTO_INCREMENT COMMENT "用户id",
    username varchar(50) UNIQUE NOT NULL COMMENT "用户名",
    password varchar(200) NOT NULL COMMENT "密码，密文存储",
    enabled boolean NOT NULL DEFAULT TRUE COMMENT "用户状态，spring security需要用到",
	first_name varchar(20) COMMENT "姓",
	last_name varchar(50) COMMENT "名",
	gender tinyint DEFAULT 0 COMMENT "性别，0表示暂定，1表示男，2表示女",
	phone varchar(20) COMMENT "手机号码",
	email varchar(20) COMMENT "邮箱",
	icon varchar(100) COMMENT "头像",
	location varchar(100) COMMENT "地址，配送图书要用",
	birthday timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "生日",
	joined_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "创建日期"
);

CREATE UNIQUE INDEX ix_users_username ON users (username);

/* 用户权限表 */
CREATE TABLE authorities (
	id bigint PRIMARY KEY AUTO_INCREMENT,
    authority varchar(50) NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(user_id) REFERENCES users(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);
/* 唯一索引，spring security查询的时候会在where上用到 */
CREATE UNIQUE INDEX ix_auth_user_id ON authorities (user_id, authority);

/* 触发器，在增加一位用户之后添加用户权限为ROLE_USER */
delimiter $
CREATE TRIGGER add_user_default_authority
AFTER INSERT ON users
FOR EACH ROW
BEGIN
	INSERT INTO authorities(authority, user_id) VALUES("ROLE_USER", NEW.id);
END $
delimiter ;

/* 用户组群表 */
CREATE TABLE user_groups (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    group_name varchar(50) NOT NULL
);

/* 组群权限表 */
CREATE TABLE group_authorities (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	authority varchar(50) NOT NULL,
    group_id bigint NOT NULL,
    CONSTRAINT fk_group_authorities_group FOREIGN KEY(group_id) REFERENCES user_groups(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* 组群 - 用户表 */
CREATE TABLE group_members (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    CONSTRAINT fk_group_members_group FOREIGN KEY(group_id) REFERENCES user_groups(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CONSTRAINT fk_group_members_user FOREIGN KEY(user_id) REFERENCES users(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* oauth部分 */
/* 客户端表，使用认证前需要向客户端表注册一个client */
CREATE TABLE oauth_client_details (
	client_id varchar(48) NOT NULL,
	resource_ids varchar(256) DEFAULT NULL,
	client_secret varchar(256) DEFAULT NULL,
	`scope` varchar(256) DEFAULT NULL,
	authorized_grant_types varchar(256) DEFAULT NULL,
	web_server_redirect_uri varchar(256) DEFAULT NULL,
	authorities varchar(256) DEFAULT NULL,
	access_token_validity int DEFAULT NULL,
	refresh_token_validity int DEFAULT NULL,
	additional_information text DEFAULT NULL,
	autoapprove varchar(256) DEFAULT NULL,
	PRIMARY KEY (client_id)
);

/* 向客户端表注册一个client，配置client_id，resource_ids，client_secret(密文存储，用的时候是明文)，允许访问资源的范围，授权模式等 */
INSERT INTO oauth_client_details VALUES("bookstore", "bookstore", "$2a$10$wDk9AuORolw2M1486MReXuwMZbJRCrtvzH/EHopvk/8EufqaAts86", "all", "authorization_code,password,refresh_token", "http://127.0.0.1:9001/", "ROLE_USER", 7200, 86400, NULL, "TRUE");

/* access token */
CREATE TABLE oauth_access_token (
   token_id varchar(256) DEFAULT NULL,
   token blob,
   authentication_id varchar(128) NOT NULL,
   `user_name` varchar(256) DEFAULT NULL,
   client_id varchar(256) DEFAULT NULL,
   authentication blob,
   refresh_token varchar(256) DEFAULT NULL,
   PRIMARY KEY (authentication_id)
);

/* 授权码 */
CREATE TABLE oauth_code (
   code varchar(256) DEFAULT NULL,
   authentication blob,
   create_ts timestamp NULL DEFAULT CURRENT_TIMESTAMP
);

/* approval */
CREATE TABLE oauth_approvals (
   userId varchar(256) DEFAULT NULL,
   clientId varchar(256) DEFAULT NULL,
   `scope` varchar(256) DEFAULT NULL,
   status varchar(10) DEFAULT NULL,
   expiresAt datetime DEFAULT NULL,
   lastModifiedAt datetime DEFAULT NULL
);

/* refresh token */
CREATE TABLE oauth_refresh_token (
	create_time timestamp DEFAULT CURRENT_TIMESTAMP,
	token_id varchar(256),
	token blob,
	authentication blob
);

CREATE INDEX token_id_index ON oauth_refresh_token (token_id);

/* （销售）图书分类表，表明某本图书的类别 */
CREATE TABLE book_categories (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL UNIQUE COMMENT "图书类别名字，比如文学",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "创建日期，是一种排序依据"
);

/* 创建多个图书分类 */
insert into book_categories(name) values
("文学"),
("神话"),
("科学"),
("地理"),
("小说"),
("编程"),
("生活"),
("哲学"),
("娱乐"),
("时尚");

/* 书店功能部分 */
/* （销售）图书库存表，图书库存表的作用是表明一种图书的库存量等信息 */
CREATE TABLE book_inventory (
	id bigint PRIMARY KEY AUTO_INCREMENT COMMENT "库存id",
	book_name varchar(50) NOT NULL COMMENT "一种图书的名字",
	price numeric(5, 2) DEFAULT 0 CHECK(price >= 0) COMMENT "一种图书的价格，统一价",
	icon varchar(100) COMMENT "图书图标",
	rating double DEFAULT 0 COMMENT "评分" CHECK(rating >=0 && rating <= 10),
	description varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT "简介",
	author varchar(30) COMMENT "图书作者",
	amount bigint DEFAULT 0 CHECK(amount >= 0) COMMENT "图书库存量",
	category_id bigint NOT NULL COMMENT "图书所属类别id",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "库存创建日期，作为一种排序依据",
	FOREIGN KEY (category_id) REFERENCES book_categories (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
)default charset=utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE INDEX book_inventory_book_name ON book_inventory (book_name);

/* （销售）图书表 */
CREATE TABLE books (
	id varchar(70) PRIMARY KEY COMMENT "图书识别码，由算法自动生成",
	name varchar(50) NOT NULL COMMENT "图书名",
	is_sold boolean DEFAULT FALSE COMMENT "图书是否已被卖掉",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "图书收录日期",
	inventory_id bigint NOT NULL COMMENT "图书所属库存id",
	FOREIGN KEY (inventory_id) REFERENCES book_inventory (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* 每当有一本图书录入的时候，其相应的库存量就会加1 */
delimiter $
CREATE TRIGGER add_book_inventory
AFTER INSERT ON books
FOR EACH ROW
begin
	UPDATE book_inventory SET amount = amount + 1 WHERE id = NEW.inventory_id;
end $
delimiter ;

/* 每当有图书的is_sold变成True的时候，对应库存量会减1 */
delimiter $
CREATE TRIGGER reduce_book_inventory_after_sale
AFTER UPDATE ON books
FOR EACH ROW
begin
	IF (NEW.is_sold = TRUE) THEN
		UPDATE book_inventory SET amount = amount - 1 WHERE id = NEW.inventory_id;
	END IF;
end $
delimiter ;


/* （销售）图书销售表，当有图书被卖出去则添加一条记录，只增不减 */
CREATE TABLE book_sales (
	id bigint PRIMARY KEY AUTO_INCREMENT COMMENT "购买记录id",
	book_id varchar(70) NOT NULL COMMENT "图书识别码",
	buyer_id bigint NOT NULL COMMENT "图书购买者",
	purchase_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "图书购买日期",
	FOREIGN KEY (book_id) REFERENCES books (id)
		ON UPDATE CASCADE,
	FOREIGN KEY (buyer_id) REFERENCES users (id)
		ON UPDATE CASCADE
);

/* （销售）图书订单表，一般是几本书一起支付时产生的 */
CREATE TABLE book_order (
	id varchar(70) PRIMARY KEY COMMENT "订单号，由算法自动生成",
	total_price numeric(8, 2) DEFAULT 0 COMMENT "支付总金额",
	phone varchar(20) NOT NULL COMMENT "收件人手机号码",
	buyer_id bigint NOT NULL COMMENT "订单支付者",
	location varchar(100) NOT NULL COMMENT "收货地址",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "订单生成日期",
	FOREIGN KEY (buyer_id) REFERENCES users (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* （销售）订单-图书表，虽然是一对多的关系可以合并到多的那一方，但是这里还是用一张表额外存储 */
/* 在订单成功支付后，会批量将记录insert进去 */
CREATE TABLE order_books (
	id bigint PRIMARY KEY COMMENT "表本身的id",
	order_id varchar(70) NOT NULL COMMENT "订单id",
	book_id varchar(70) NOT NULL COMMENT "图书识别码",
	FOREIGN KEY (order_id) REFERENCES book_order (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* （租借）图书分类表，表明某本图书的类别 */
CREATE TABLE rented_book_categories (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL UNIQUE COMMENT "图书类别名字，比如文学",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "创建日期，是一种排序依据"
);

insert into rented_book_categories(name) values
("文学"),
("神话"),
("科学"),
("地理"),
("小说"),
("编程"),
("生活"),
("哲学"),
("娱乐"),
("时尚");

/* （租借）图书库存表，图书库存表的作用是表明一种图书的库存量等信息 */
CREATE TABLE rented_book_inventory (
	id bigint PRIMARY KEY AUTO_INCREMENT COMMENT "库存id",
	book_name varchar(50) NOT NULL COMMENT "一种图书的名字",
	price numeric(5, 2) DEFAULT 0 CHECK(price >= 0) COMMENT "一种图书租借价格，单位为元/每周，统一价",
	icon varchar(100) COMMENT "图书图标",
	rating double DEFAULT 0 COMMENT "评分" CHECK(rating >=0 && rating <= 10),
	description varchar(200) COMMENT "简介",
	author varchar(30) COMMENT "图书作者",
	amount bigint DEFAULT 0 CHECK(amount >= 0) COMMENT "图书库存量",
	category_id bigint NOT NULL COMMENT "图书所属类别id",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "库存创建日期，作为一种排序依据",
	FOREIGN KEY (category_id) REFERENCES rented_book_categories (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE INDEX retend_book_inventory_book_name ON rented_book_inventory (book_name);

/* （租借）图书表 */
CREATE TABLE rented_books (
	id varchar(70) PRIMARY KEY COMMENT "图书识别码，由算法自动生成",
	name varchar(50) NOT NULL COMMENT "图书名",
	is_rent boolean DEFAULT FALSE COMMENT "是否被借出，作为挑选查询依据",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "图书收录日期",
	inventory_id bigint NOT NULL COMMENT "图书所属库存id",	
	FOREIGN KEY (inventory_id) REFERENCES rented_book_inventory (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* 每当有一本图书录入的时候，其相应的库存量就会加1 */
delimiter $
CREATE TRIGGER add_rented_book_inventory
AFTER INSERT ON rented_books
FOR EACH ROW
begin
	UPDATE rented_book_inventory SET amount = amount + 1 WHERE id = NEW.inventory_id;
end $
delimiter ;

/* 每当有图书的is_rent变成True的时候，对应库存量会减1 */
delimiter $
CREATE TRIGGER reduce_rented_book_inventory_after_rent
AFTER UPDATE ON rented_books
FOR EACH ROW
begin
	IF (NEW.is_rent = TRUE) THEN
		UPDATE rented_book_inventory SET amount = amount - 1 WHERE id = NEW.inventory_id;
	END IF;
end $
delimiter ;

/* 每当有图书的is_rent变成False的时候，对应库存量加1 */
delimiter $
CREATE TRIGGER add_rented_book_inventory_after_return
AFTER UPDATE ON rented_books
FOR EACH ROW
begin
	IF (NEW.is_rent = FALSE) THEN
		UPDATE rented_book_inventory SET amount = amount + 1 WHERE id = NEW.inventory_id;
	END IF;
end $
delimiter ;

/* （租借）图书租表，成功租出去以后会产生一条记录，只增不减 */
CREATE TABLE rented_book_rent (
	id bigint PRIMARY KEY AUTO_INCREMENT COMMENT "出租记录id",
	book_id varchar(70) NOT NULL COMMENT "图书识别码",
	owner_id bigint NOT NULL COMMENT "图书租用者",
	borrow_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "图书租用日期时间",
	FOREIGN KEY (book_id) REFERENCES rented_books (id)
		ON UPDATE CASCADE,
	FOREIGN KEY (owner_id) REFERENCES users (id)
		ON UPDATE CASCADE
);

/* （租借）图书还表，成功还回一本书后会产生一条记录 */
CREATE TABLE rented_book_return (
	id bigint PRIMARY KEY AUTO_INCREMENT COMMENT "归还记录id",
	book_id varchar(70) NOT NULL COMMENT "图书识别码",
	owner_id bigint NOT NULL COMMENT "图书归还者",
	return_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "图书归还日期时间",
	FOREIGN KEY (book_id) REFERENCES rented_books (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (owner_id) REFERENCES users (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* (租借）图书租借订单表，在还书时会产生订单并进行支付 */
CREATE TABLE rented_book_order (
	id varchar(70) PRIMARY KEY COMMENT "订单号，由算法自动生成",
	pre_price numeric(5, 2) DEFAULT 0 CHECK(pre_price >= 0) COMMENT "押金",
	total_price numeric(8, 2) DEFAULT 0 CHECK(total_price >= 0) COMMENT "实际支付金额，一开始是0",
	price numeric(5, 2) DEFAULT 0 CHECK(price >= 0) COMMENT "每周需支付金额",
	owner_id bigint NOT NULL COMMENT "订单支付者",
	phone varchar(20) NOT NULL COMMENT "收件人手机号码",
	location varchar(100) NOT NULL COMMENT "收货地址",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "订单生成日期",
	FOREIGN KEY (owner_id) REFERENCES users (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* （租借）订单-图书表，便于查询一个订单中有哪些图书 */
CREATE TABLE rented_order_books (
	id bigint PRIMARY KEY COMMENT "表本身的id",
	order_id varchar(70) NOT NULL COMMENT "订单id",
	book_id varchar(70) NOT NULL COMMENT "图书识别码",
	FOREIGN KEY (order_id) REFERENCES rented_book_order (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (book_id) REFERENCES rented_books (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* （预订）预订表，只针对可销售图书而言，先支付定金，图书到了以后再支付尾款，并设置is_completed为true */
CREATE TABLE book_reserve (
	id varchar(70) PRIMARY KEY COMMENT "预订订单号，由算法自动生成",
	is_completed boolean DEFAULT false COMMENT "订单是否处理完成",
	user_id bigint NOT NULL COMMENT "预订用户id",
	inventory_id bigint NOT NULL COMMENT "预订书籍库存的id",
	amount int DEFAULT 0 CHECK(amount >= 0) COMMENT "预订图书数量",
	location varchar(100) NOT NULL COMMENT "收货地址",
	phone varchar(20) NOT NULL COMMENT "收货人手机号码",
	pre_price numeric(5, 2) DEFAULT 0 CHECK(pre_price >= 0) COMMENT "定金",
	total_price numeric(8, 2) DEFAULT 0 CHECK(total_price >= 0) COMMENT "实际支付金额，一开始的数值为定金",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "预订订单的生成日期",
	FOREIGN KEY (user_id) REFERENCES users (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (inventory_id) REFERENCES book_inventory (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* （预订）预订支付表，记载了因为预订所产生的支付记录，支付定金时添加一条记录，付尾款时添加一条记录 */
CREATE TABLE book_reserve_record (
	id bigint PRIMARY KEY COMMENT "记录本身id",
	reserve_id varchar(70) NOT NULL COMMENT "订单id",
	user_id bigint NOT NULL COMMENT "用户id",
	total_price numeric(8, 2) DEFAULT 0 COMMENT "支付金额",
	created_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT "支付发生的时间",
	FOREIGN KEY (reserve_id) REFERENCES book_reserve (id)
		ON UPDATE CASCADE,
	FOREIGN KEY (user_id) REFERENCES users (id)
		ON UPDATE CASCADE
);


