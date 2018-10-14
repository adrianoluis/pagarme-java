package me.pagar;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PagarMeTest extends BaseTest {

	private String payload = "{\"sample\":\"payload\",\"value\":true}";

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testValidateRequestSignature() throws Throwable {
		assertTrue(PagarMe.validateRequestSignature(payload, "sha1=7820fcb6d03ec8f721c14596654d24623af9e7de"));

		assertFalse(PagarMe.validateRequestSignature(payload, "sha1=hash_errado"));
	}

}
