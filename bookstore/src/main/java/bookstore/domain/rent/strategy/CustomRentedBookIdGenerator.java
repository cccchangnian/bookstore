package bookstore.domain.rent.strategy;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import bookstore.utils.SnowFlake;

// 生成租借图书主键的类
public class CustomRentedBookIdGenerator extends UUIDGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		if (null != object) {
			SnowFlake snowFlake = new SnowFlake(1L, 1L);
			return (Serializable)("RB" + snowFlake.nextId());
		}
		return super.generate(session, object);
	}
}
