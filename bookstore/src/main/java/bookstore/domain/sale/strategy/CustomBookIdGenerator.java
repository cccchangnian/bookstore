package bookstore.domain.sale.strategy;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import bookstore.utils.SnowFlake;

// 用于生成销售图书主键的类
public class CustomBookIdGenerator extends UUIDGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		if (null != object) {
			SnowFlake snowFlake = new SnowFlake(1L, 1L);
			return (Serializable)("B" + snowFlake.nextId());
		}
		return super.generate(session, object);
	}
}
