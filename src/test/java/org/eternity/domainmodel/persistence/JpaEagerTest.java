package org.eternity.domainmodel.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.AmountDiscountPolicy;
import org.eternity.domainmodel.movie.domain.DiscountPolicy;
import org.eternity.domainmodel.movie.domain.PeriodCondition;
import org.eternity.domainmodel.movie.domain.SequenceCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@DataJpaTest(showSql = false)
public class JpaEagerTest {
	@Autowired
	private EntityManager em;

	@Test
	public void add_discount_condition() {
		DiscountPolicy policy =
				new AmountDiscountPolicy(Money.wons(1000),
					Set.of(new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)),
							new SequenceCondition(1),
							new SequenceCondition(3)),
					Set.of(Money.wons(1000), Money.wons(200), Money.wons(300)));
		em.persist(policy);
		em.flush();
		em.clear();

		em.find(DiscountPolicy.class, policy.getId());
	}
}
