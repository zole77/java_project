package security_project;

import java.security.PrivateKey;
import java.security.PublicKey;
 
import javax.crypto.Cipher;
import java.util.Base64;

public class RSA { //����Ű �˰���

	public String Encryption(String secret , PublicKey publickey){
        try {
            Cipher cipher = Cipher.getInstance("RSA"); //RSA�˰��� ����
            cipher.init(Cipher.ENCRYPT_MODE, publickey); // ��ȣȭ �ʱ�ȭ
            byte[] arrCipherData = cipher.doFinal(secret.getBytes()); // ��ȣȭ�� ������(byte �迭) 
            String str = Base64.getEncoder().encodeToString(arrCipherData);
            //��ȣȭ ����� string���� ���ڵ� (txt���Ͽ� �����ϱ� ����)
            
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
    	 	//��ȣȭ ����� string���� ���� �߱� ������ byteŸ������ ���ڵ��Ѵ�.
            cipher.init(Cipher.DECRYPT_MODE, privatekey);
            byte[] bytePlain = cipher.doFinal(byteEncrypted);
            String str = new String(bytePlain, "utf-8"); //��ȣȭ �� ���� string���� ����(���� ������ string���� �ʿ��ϱ� ����)

            return str;

        } catch (Exception e) {
            e.printStackTrace();
        }
    	 return null;
    }

}
