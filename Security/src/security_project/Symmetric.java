package security_project;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

 
public class Symmetric { //대칭키 알고리즘
        
	private SecretKey AESkey; //대칭키를 저장할 변수 (클래스 내에서만 사용 가능)
	
	public String getAESKey(){
        try{
        	KeyGenerator generator = KeyGenerator.getInstance("AES");   // AES Key Generator 객체 생성 
        	generator.init(128);    // AES Key size 지정
        	SecretKey secKey = generator.generateKey(); // AES 암호화 알고리즘에서 사용할 대칭키 생성
        	String str = Base64.getEncoder().encodeToString(secKey.getEncoded());
        	//공개키로 대칭이를 암호화 할 때, 메세지로 간주하기 때문에 string타입으로 인코딩 하여 리턴한다.
        	
        	this.AESkey = secKey;
        	return str;
        	
        }catch(NoSuchAlgorithmException e){
        	e.printStackTrace();
        }return null;
    }
    
    public String encryptText(String plainText){ //AES 암호화
        try{
        	Cipher aesCipher = Cipher.getInstance("AES"); // AES 알고리즘을 이용해 암호를 생성
        	aesCipher.init(Cipher.ENCRYPT_MODE, AESkey); // 키 값을 이용해서 암호를 초기화
            byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());    // 암호문 생성
            String str = Base64.getEncoder().encodeToString(byteCipherText);
            //txt파일에 저장하기 위해 string값으로 인코딩하여 리턴한다.
            
            return str;
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return null;
    }
   
    public String decryptText(String CipherText){ //AES복호화
    	try{
    		Cipher aesCipher = Cipher.getInstance("AES"); // AES 알고리즘을 이용해 복화화 세팅
    		aesCipher.init(Cipher.DECRYPT_MODE, AESkey);    // 복호화 모드 초기화
    		byte[] byt = Base64.getDecoder().decode(CipherText.getBytes());
    		byte[] bytePlainText = aesCipher.doFinal(byt);   // 암호문 -> 평문으로 복호화
    		String str = new String(bytePlainText, "utf-8"); //복호화 된 평문을 String으로 변환한다. (다음 과정에 string값이 필요하기 때문)
    		
    		return str;
    	}
        catch(Exception e){
        	e.printStackTrace();
        }
    	return null;
    }
}