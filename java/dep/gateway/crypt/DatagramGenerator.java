package dep.gateway.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Formatter;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-29
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class DatagramGenerator {

    private static Logger logger = LoggerFactory.getLogger(DatagramGenerator.class);
    private static DatagramGenerator instance;

    private DatagramGenerator() {
    }

    public static DatagramGenerator getInstance() {
        if (instance == null) {
            instance = new DatagramGenerator();
        }
        return instance;
    }

    /**
     * 加密
     *
     * @param strMing
     * @return 密文
     * @throws Exception
     */
    public String encrypt(String strMing) {
        return null;
    }

    /**
     * 解密
     *
     * @param encrypedStr
     * @return 明文
     * @throws Exception
     */
    public String decrypt(String encrypedStr) throws Exception {

       return null;
    }


}
