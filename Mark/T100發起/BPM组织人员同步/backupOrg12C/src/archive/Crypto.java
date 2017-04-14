package archive;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;
import java.security.NoSuchAlgorithmException;
import java.net.URLEncoder;
import sun.misc.BASE64Decoder;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.net.URLDecoder;
import javax.crypto.Cipher;
import javax.crypto.spec.DESedeKeySpec;

public class Crypto {

	/**
	 * 資料加密程式，呼叫後，傳入的參數將被 hash 成無意義的字串。加密時
	 * 加入 "salt" 以預防攻擊者以對照字典的方式進行破解
	 * This method uses SHA1 to hash the input string and then output the base
	 * 64 encoded string of the hashed string.
	 * @param dataToHash the string to be hashed
	 * @return the base 64 hashed string
	 * @author Steven Shih
	 * @since NaNa 0.8.0
	 */
	public static String hashSHA1String(String tDataToHash) {
		String tRtnTmp = null;
		String tSalt = "abcedefghijklmnopqrstuvwxyz";
		try {
			MessageDigest tMd = MessageDigest.getInstance("SHA1");
			tMd.update(tSalt.getBytes());
			byte[] tByteTmpe = tMd.digest(tDataToHash.getBytes());
			//base 64 encoding, using sun's internal lib
			BASE64Encoder tB64encoder = new BASE64Encoder();
			tRtnTmp = tB64encoder.encode(tByteTmpe);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Message digest no such algorithm, " + e);
		}
		return tRtnTmp;
	}

	/** 用來加密的Key */
	private static final String ENCRYPT_KEY ="abcdefghijklmn1234567890";

	/**
	 * 3-DES加密
	 * @param String tData 要進行加密的String
	 * @param String tEncryptkey用來加密的Key
	 * @return String 3-DES加密後的String
	 * @author 施雅心
	 * @since NaNa 1.2
	 */
	public String get3DESEncrypt(String tData) {
		String tEncryptedValue = "";
		try {
			//取得Key
			byte[] tEncryptKey = getEnKey(ENCRYPT_KEY);
			byte[] tByteData = tData.getBytes("UTF-16LE");
			//進行3-DES加密
			byte[] tEncryptedData = Encrypt(tByteData, tEncryptKey);
			//進行BASE64編碼
			String tBbase64String = getBase64Encode(tEncryptedData);
			//BASE64編碼去除换行符號
			String tBase64Encrypt = filter(tBbase64String);
			//對BASE64編碼中的HTML控制碼轉換
			tEncryptedValue = getURLEncode(tBase64Encrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tEncryptedValue;
	}

	/**
	 * 進行MD5加密
	 * @param String 原始的資料
	 * @return byte[] 指定加密方式為md5後的byte[]
	 */
	private byte[] md5(String tKey) {
		byte[] tByteValue = null;
		try {
			MessageDigest tMd5 = MessageDigest.getInstance("MD5");
			tByteValue = tMd5.digest(tKey.getBytes("GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tByteValue;
	}

	/**
	 * 得到3-DES的Key
	 * @param String 原始的KEY
	 * @return byte[] 指定加密方式為md5後的byte[]
	 */

	private byte[] getEnKey(String tKey) {
		byte[] tDesKey = null;
		try {
			byte[] tDesByteKey = md5(tKey);
			tDesKey = new byte[24];
			int i = 0;
			while (i < tDesByteKey.length && i < 24) {
				tDesKey[i] = tDesByteKey[i];
				i++;
			}
			if (i < 24) {
				tDesKey[i] = 0;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tDesKey;
	}

	/**
	 * 3-DES加密
	 * @param byte[] tData 要進行3-DES加密的byte[]
	 * @param byte[] tKey 3-DES加密Key
	 * @return byte[] 3-DES加密後的byte[]
	 */

	private byte[] Encrypt(byte[] tData, byte[] tKey) {
		byte[] tEncryptedData = null;
		try {
			DESedeKeySpec tDks = new DESedeKeySpec(tKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey tkey = keyFactory.generateSecret(tDks);
			Cipher tCipher = Cipher.getInstance("DESede");
			tCipher.init(Cipher.ENCRYPT_MODE, tkey);
			tEncryptedData = tCipher.doFinal(tData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tEncryptedData;
	}

	/**
	 * 對字符串進行Base64編碼
	 * @param byte[] src 要進行編碼的字符
	 * @return String 進行編碼後的字符串
	 */
	private String getBase64Encode(byte[] tData) {
		String tEncodedValue = "";
		try {
			BASE64Encoder tBase64en = new BASE64Encoder();
			tEncodedValue = tBase64en.encode(tData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tEncodedValue;
	}

	/**
	 * 去掉字符串的换行符号
	 * @param String 要去除換行符號的字串
	 * @return String 去除換行符號後的字串
	 */
	private String filter(String tData) {
		String tOutput = null;
		StringBuffer tStringBuffer = new StringBuffer();
		for (int i = 0; i < tData.length(); i++) {
			int asc = tData.charAt(i);
			if (asc != 10 && asc != 13) {
				tStringBuffer.append(tData.subSequence(i, i + 1));
			}
		}
		tOutput = new String(tStringBuffer);
		return tOutput;
	}

	/**
	 * 对字串進行URLDecoder.encode(strEncoding)編碼
	 * @param String tData 要進行編碼的字符串
	 * @return String 進行編碼後的字符串
	 */
	private String getURLEncode(String tData) {
		String tRequestValue = "";
		try {
			tRequestValue = URLEncoder.encode(tData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tRequestValue;
	}

	/**
	 * 3-DES解密
	 * @param String src 要進行3-DES解密的String
	 * @return String 3-DES解密後的String
	 */
	public String get3DESDecrypt(String tData) {
		String tRequestValue = "";
		try {
			//URLDecoder.decodeTML解密
			String tURLValue = getURLDecoderdecode(tData);
			//進行3-DES解密
			BASE64Decoder tBase64Decode = new BASE64Decoder();
			byte[] tBase64DValue = tBase64Decode.decodeBuffer(tURLValue);
			//利用Key解密
			tRequestValue = deCrypt(tBase64DValue, ENCRYPT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tRequestValue;
	}

	/**
	 * 進行3-DES解密
	 * @param byte[] tData 要進行3-DES解密byte[]
	 * @param String tKey 解密的Key
	 * @return String 3-DES解密后的String
	 */
	private String deCrypt(byte[] tData, String tKey) {
		String tDecodeString = null;
		Cipher tCipher = null;
		try {
			tCipher = Cipher.getInstance("DESede");
			byte[] tBytekey = getEnKey(tKey);
			DESedeKeySpec tDESedeKeySpec = new DESedeKeySpec(tBytekey);
			SecretKeyFactory tKeyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey tSecretKey = tKeyFactory.generateSecret(tDESedeKeySpec);
			tCipher.init(Cipher.DECRYPT_MODE, tSecretKey);
			byte tCiphertext[] = tCipher.doFinal(tData);
			tDecodeString = new String(tCiphertext, "UTF-16LE");
		} catch (Exception ex) {
			tDecodeString = "";
			ex.printStackTrace();
		}
		return tDecodeString;
	}

	/**
	 * 對字串進行URLDecoder.decode(strEncoding)解碼
	 * @param String src 要進行解碼的字串
	 * @return String 進行解碼後的字串
	 */
	private String getURLDecoderdecode(String tData) {
		String tRequestValue = "";
		try {
			tRequestValue = URLDecoder.decode(tData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tRequestValue;
	}

}
