package me.pagar;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class TransactionTest extends BaseTest {

	private final int TRANSACTION_ID = 598075;

	private Transaction transaction;

	@Before
	public void setUp() {
		super.setUp();
		transaction = new Transaction();
		transaction.setId(TRANSACTION_ID);
	}

	@Test
	public void testFindAll() throws Throwable {
		Collection<Transaction> transactions = transaction.list(5, 1);
		Assert.assertNotEquals(0, transactions.size());
	}

	@Test
	public void testCardHashKey() throws Throwable {
		CardHashKey cardHashKey = transaction.cardHashKey();
		Assert.assertNotNull(cardHashKey);
	}

	@Test
	public void testTransactions() throws Throwable {
		Collection<Transaction> transactions = transaction.list();
		Assert.assertNotEquals(0, transactions.size());
	}

	@Test
	public void testAntifraudAnalysises() throws Throwable {
		Collection<AntifraudAnalysis> antifraudAnalysises = transaction.antifraudAnalysises();
		Assert.assertEquals(0, antifraudAnalysises.size());
	}

	@Test
	public void testPayables() throws Throwable {
		Collection<Payable> payables = transaction.payables();
		Assert.assertEquals(0, payables.size());
	}

	@Test
	public void testPostbacks() throws Throwable {
		Collection<Postback> postbacks = transaction.postbacks();
		Assert.assertEquals(0, postbacks.size());
	}

	@Test
	public void testSplitRules() throws Throwable {
		Collection<SplitRule> splitRules = transaction.splitRules();
		Assert.assertEquals(0, splitRules.size());
	}

}
