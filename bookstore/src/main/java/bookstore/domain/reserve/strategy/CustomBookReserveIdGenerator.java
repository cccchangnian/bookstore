package bookstore.domain.reserve.strategy;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import bookstore.utils.SnowFlake;

// 用于生成预订订单主键的类
public class CustomBookReserveIdGenerator extends UUIDGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		if (null != object) {
			SnowFlake snowFlake = new SnowFlake(1L, 1L);
			return (Serializable)("BR" + snowFlake.nextId());
		}
		return super.generate(session, object);
	}
}
