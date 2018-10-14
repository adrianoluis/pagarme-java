package me.pagar;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class CustomerTest extends BaseTest {

	private final String CUSTOMER_NAME = "Test Create Customer",
			CUSTOMER_DOCUMENT_NUMBER = "11111111111",
			CUSTOMER_EMAIL = "customer1@pagar.me",
			CUSTOMER_ADDRESS_STREET = "Rua Jose da Silva",
			CUSTOMER_ADDRESS_STREET_NUMBER = "123",
			CUSTOMER_ADDRESS_COMPLEMENTARY = "Casa",
			CUSTOMER_ADDRESS_NEIGHBORHOOD = "Interlagos",
			CUSTOMER_ADDRESS_ZIP_CODE = "04840110",
			CUSTOMER_PHONE_DDD = "11",
			CUSTOMER_PHONE_NUMBER = "55284132";

	private final LocalDate CUSTOMER_BORN_AT = LocalDate.parse("1981-03-23");

	private Customer customer;

	@Before
	public void setUp() {
		super.setUp();
		customer = new Customer();
	}

	@Test
	public void testList() throws Throwable {
		final Collection<Customer> customers = customer.list();
		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void testSave() throws Throwable {
		final Address address = new Address(CUSTOMER_ADDRESS_STREET, CUSTOMER_ADDRESS_STREET_NUMBER,
				CUSTOMER_ADDRESS_NEIGHBORHOOD, CUSTOMER_ADDRESS_ZIP_CODE);
		address.setComplementary(CUSTOMER_ADDRESS_COMPLEMENTARY);

		final Phone phone = new Phone(CUSTOMER_PHONE_DDD, CUSTOMER_PHONE_NUMBER);

		customer.setName(CUSTOMER_NAME);
		customer.setEmail(CUSTOMER_EMAIL);
		customer.setDocumentNumber(CUSTOMER_DOCUMENT_NUMBER);
		customer.setBornAt(CUSTOMER_BORN_AT);

		customer.setAddress(address);
		customer.setPhone(phone);
		customer = customer.save();

		Assert.assertFalse(customer.getAddresses().isEmpty());
		Assert.assertFalse(customer.getPhones().isEmpty());
		Assert.assertNotNull(customer.getId());
	}


}
