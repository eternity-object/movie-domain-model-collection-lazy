package org.eternity.domainmodel.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.domainmodel.movie.domain.DiscountPolicy;
import org.eternity.domainmodel.movie.domain.PercentDiscountPolicy;
import org.eternity.domainmodel.movie.domain.SequenceCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

@DataJpaTest(showSql = false)
public class JpaLazyTest {
	@Autowired
	private EntityManager em;

	@Test
	public void add_discount_condition() {
		DiscountPolicy policy = new PercentDiscountPolicy(0.1, Set.of(new SequenceCondition(1)));
		em.persist(policy);
		em.flush();
		em.clear();

		DiscountPolicy loadedPolicy = em.find(DiscountPolicy.class, policy.getId());
		loadedPolicy.addDiscountCondition(new SequenceCondition(2));
		em.flush();
	}
}
