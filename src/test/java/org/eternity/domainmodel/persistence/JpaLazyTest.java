package org.eternity.domainmodel.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.domainmodel.generic.Money;
import org.eternity.domainmodel.movie.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@DataJpaTest(showSql = false)
public class JpaLazyTest {
	@Autowired
	private EntityManager em;

	@Test
	public void add_discount_condition() {
		DiscountPolicy policy1 =
				new AmountDiscountPolicy(
						Money.wons(1000),
						Set.of(new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)),
								new SequenceCondition(1)));
		DiscountPolicy policy2 =
				new PercentDiscountPolicy(
						0.1,
						Set.of(new SequenceCondition(1),
								new SequenceCondition(3)));

		em.persist(policy1);
		em.persist(policy2);
		em.flush();
		em.clear();

		List<DiscountPolicy> policies = em.createQuery("select p from DiscountPolicy p").getResultList();

		for(DiscountPolicy policy : policies) {
			policy.getConditions().size();
		}
	}
}
