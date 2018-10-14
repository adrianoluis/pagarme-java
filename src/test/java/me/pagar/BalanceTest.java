package me.pagar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceTest extends BaseTest {

	private Balance balance;

	@Before
	public void setUp() {
		super.setUp();
		balance = new Balance();
	}

	@Test
	public void testRefresh() throws Throwable {
		balance.refresh();
		Assert.assertNotNull(balance.getAvailable());
	}


}
