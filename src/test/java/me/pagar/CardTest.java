package me.pagar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class CardTest extends BaseTest {

    private final int TRANSACTION_ID = 598075;

    private Card card;

    private Transaction transaction;

    @Before
    public void setUp() {
        super.setUp();
        card = new Card();
        transaction = new Transaction();
        transaction.setId(TRANSACTION_ID);
    }

    @Test
    public void testFindAll() throws Throwable {
        Collection<Card> cards = card.list();
        Assert.assertNotEquals(0, cards.size());
    }

    @Test
    public void testEncrypt() throws Throwable {
        card = new Card("4901720080344448",
                "Aardvark Silva",
                "1213",
                "314");

        final CardHashKey cardHashKey = transaction.cardHashKey();
        Assert.assertNotNull(cardHashKey);

        final String encryptedCreditCard = card.encrypt(cardHashKey);

        Assert.assertNotNull(encryptedCreditCard);

        card.save();
        Assert.assertNotNull(card.getId());
    }

    @Test
    public void testEncryptWithNullData() throws Throwable {
        card = new Card("4901720080344448",
                "Aardvark Silva",
                "1213",
                null);

        final CardHashKey cardHashKey = transaction.cardHashKey();
        Assert.assertNotNull(cardHashKey);

        final String encryptedCreditCard = card.encrypt(cardHashKey);

        Assert.assertNull(encryptedCreditCard);
    }

}
