package security_project;

import java.security.PrivateKey;
import java.security.PublicKey;
 
import javax.crypto.Cipher;
import java.util.Base64;

public class RSA { //공개키 알고리즘

	public String Encryption(String secret , PublicKey publickey){
        try {
            Cipher cipher = Cipher.getInstance("RSA"); //RSA알고리즘 선택
            cipher.init(Cipher.ENCRYPT_MODE, publickey); // 암호화 초기화
            byte[] arrCipherData = cipher.doFinal(secret.getBytes()); // 암호화된 데이터(byte 배열) 
            String str = Base64.getEncoder().encodeToString(arrCipherData);
            //암호화 결과를 string으로 인코딩 (txt파일에 저장하기 위해)
            
            return str;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    public String Decryption(String arrCipherData , PrivateKey privatekey){
    	 try {
    	 	Cipher cipher = Cipher.getInstance("RSA");
    	 	byte[] byteEncrypted = Base64.getDecoder().decode(arrCipherData.getBytes());
    	 	//암호화 결과를 string으로 리턴 했기 때문에 byte타입으로 디코딩한다.
            cipher.init(Cipher.DECRYPT_MODE, privatekey);
            byte[] bytePlain = cipher.doFinal(byteEncrypted);
            String str = new String(bytePlain, "utf-8"); //복호화 한 값을 string으로 리턴(다음 과정에 string값이 필요하기 때문)

            return str;

        } catch (Exception e) {
            e.printStackTrace();
        }
    	 return null;
    }

}
