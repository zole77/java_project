package security_project;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

 
public class Symmetric { //��ĪŰ �˰���
        
	private SecretKey AESkey; //��ĪŰ�� ������ ���� (Ŭ���� �������� ��� ����)
	
	public String getAESKey(){
        try{
        	KeyGenerator generator = KeyGenerator.getInstance("AES");   // AES Key Generator ��ü ���� 
        	generator.init(128);    // AES Key size ����
        	SecretKey secKey = generator.generateKey(); // AES ��ȣȭ �˰��򿡼� ����� ��ĪŰ ����
        	String str = Base64.getEncoder().encodeToString(secKey.getEncoded());
        	//����Ű�� ��Ī�̸� ��ȣȭ �� ��, �޼����� �����ϱ� ������ stringŸ������ ���ڵ� �Ͽ� �����Ѵ�.
        	
        	this.AESkey = secKey;
        	return str;
        	
        }catch(NoSuchAlgorithmException e){
        	e.printStackTrace();
        }return null;
    }
    
    public String encryptText(String plainText){ //AES ��ȣȭ
        try{
        	Cipher aesCipher = Cipher.getInstance("AES"); // AES �˰����� �̿��� ��ȣ�� ����
        	aesCipher.init(Cipher.ENCRYPT_MODE, AESkey); // Ű ���� �̿��ؼ� ��ȣ�� �ʱ�ȭ
            byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());    // ��ȣ�� ����
            String str = Base64.getEncoder().encodeToString(byteCipherText);
            //txt���Ͽ� �����ϱ� ���� string������ ���ڵ��Ͽ� �����Ѵ�.
            
            return str;
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return null;
    }
   
    public String decryptText(String CipherText){ //AES��ȣȭ
    	try{
    		Cipher aesCipher = Cipher.getInstance("AES"); // AES �˰����� �̿��� ��ȭȭ ����
    		aesCipher.init(Cipher.DECRYPT_MODE, AESkey);    // ��ȣȭ ��� �ʱ�ȭ
    		byte[] byt = Base64.getDecoder().decode(CipherText.getBytes());
    		byte[] bytePlainText = aesCipher.doFinal(byt);   // ��ȣ�� -> ������ ��ȣȭ
    		String str = new String(bytePlainText, "utf-8"); //��ȣȭ �� ���� String���� ��ȯ�Ѵ�. (���� ������ string���� �ʿ��ϱ� ����)
    		
    		return str;
    	}
        catch(Exception e){
        	e.printStackTrace();
        }
    	return null;
    }
}