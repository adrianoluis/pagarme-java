package me.pagar;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class BankAccountTest extends BaseTest {

    private BankAccount bankAccount;

    @Before
    public void setUp() {
        super.setUp();
        bankAccount = new BankAccount();
    }

    @Test
    public void testFindAll() throws Throwable {
        final Collection<BankAccount> bankAccounts = bankAccount.find(5, 1);
        Assert.assertNotEquals(0, bankAccounts.size());
    }

    @Test
    public void testSave() throws Throwable {
        bankAccount.setBankCode("341");
        bankAccount.setAgencia("8888");
        bankAccount.setConta("34123");
        bankAccount.setContaDv("1");
        bankAccount.setDocumentType(BankAccount.DocumentType.CPF);
        bankAccount.setDocumentNumber("75068255971");
        bankAccount.setLegalName("JOAO JOSE JOAQUIM");
        bankAccount.save();
    }

}
