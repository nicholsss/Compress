
package fi.yussiv.squash;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;


public class LZWTest {

    @Test
    public void stringUnchangedWhenEncodedAndDecoded() throws UnsupportedEncodingException {
        String testString = UUID.randomUUID().toString();
        
        byte[] encoded = LZW.encode(testString.getBytes("UTF-8"));
        byte[] decoded = LZW.decode(encoded);
        
        String returnedString = new String(decoded, "UTF-8");
        
        assertEquals(testString, returnedString);
        
        testString += testString;
        
        encoded = LZW.encode(testString.getBytes("UTF-8"));
        decoded = LZW.decode(encoded);
        
        returnedString = new String(decoded, "UTF-8");
        
        assertEquals(testString, returnedString);
    }
}
