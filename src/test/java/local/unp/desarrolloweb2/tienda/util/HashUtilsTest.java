package local.unp.desarrolloweb2.tienda.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HashUtilsTest {

    @Test
    public void testSha256HexReturnsExpectedHash() {
        String hash = HashUtils.sha256Hex("desaweb2");
        assertEquals("b4d97856c0e4228bfcc3403dc610857d162a4d2fb18c1b01fd5a5037f2a18496", hash);
    }

    @Test
    public void testSha256HexForEmptyString() {
        String hash = HashUtils.sha256Hex("");
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", hash);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSha256HexThrowsForNull() {
        HashUtils.sha256Hex(null);
    }
}
