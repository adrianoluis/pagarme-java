package me.pagar;

import com.google.common.base.Strings;

public abstract class BaseTest {

	public void setUp() {
		String apiKey = System.getenv("PAGARME_API_KEY");

		if (Strings.isNullOrEmpty(apiKey)) {
			apiKey = "ak_test_Rw4JR98FmYST2ngEHtMvVf5QJW7Eoo";
		}

		PagarMe.init(apiKey);
	}

}
