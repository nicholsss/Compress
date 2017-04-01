
package fi.yussiv.squash;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class LZWTest {
    
    private LZW lzw;
    
    @Before
    public void setUp() {
        lzw = new LZW();
    }

    @Test
    public void stringUnchangedWhenEncodedAndDecoded() throws UnsupportedEncodingException {
        String testString = UUID.randomUUID().toString();
        
        byte[] encoded = lzw.encode(testString.getBytes("UTF-8"));
        byte[] decoded = lzw.decode(encoded);
        
        String returnedString = new String(decoded, "UTF-8");
        
        assertEquals(testString, returnedString);
        
        testString += testString;
        
        encoded = lzw.encode(testString.getBytes("UTF-8"));
        decoded = lzw.decode(encoded);
        
        returnedString = new String(decoded, "UTF-8");
        
        assertEquals(testString, returnedString);
    }
}
