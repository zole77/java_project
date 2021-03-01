package security_project;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;

class Digital_Signature {
	
	KeyPairGenerator keyPairGen;
	KeyPair keypair;
	
	public KeyPair generateRsaKeyPair() { //����Ű ����Ű ���� �������ִ� �޼ҵ�
		
		SecureRandom secureRandom = new SecureRandom();
		
		try {
					
			// Ű��� ����
			keyPairGen = KeyPairGenerator.getInstance("RSA");	 //	RSA�� ���ڷ� �ؼ� ��ü�� �ν��Ͻ� ȹ��
			keyPairGen.initialize(1024,secureRandom);			 //Ű ������ 1024��Ʈ,secureRandom�� ���� ����ɶ����� �� �ٲ�� Ű��� �ʱ�ȭ 
			keypair = keyPairGen.genKeyPair();		  			
			
			return keypair; 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//����Ű�� �޼��� ��������Ʈ ����
	public String signature(String Message_Digest, PrivateKey privateKey) {
		
		try {
			Signature sign = Signature.getInstance("SHA256withRSA"); //�ؽ��Լ� SHA256���� �޼��� ��������Ʈ �� ���� RSA�� �����м���
			sign.initSign(privateKey); //�־��� ����Ű�� ������ �ʱ�ȭ �Ѵ�.
			sign.update(Message_Digest.getBytes());  //���� �ϰ��� �ϴ� �����͸� ������Ʈ �Ѵ�.
			String str = Base64.getEncoder().encodeToString(sign.sign()); 
			//String���� ��ȯ�ϱ� ���� ���ڵ��� (txt���Ͽ� byte���� ���� ��� �������� �� ���� �޶����� ������ string���)
			
			return str;  //������Ʈ �� �����Ϳ� ���� ���� ������ base64���ڵ� ���� ����
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//�޼��� ��������Ʈ�� ����Ű�� ������ ��ȣȭ�Ѱ��� ���ؼ� ��ġ�ϸ� �޼��� ��������Ʈ ����
	public String sig_check(String Message_Digest, String Digital_sign, PublicKey publicKey ) {
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");//��������  SHA256withRSA�� ����
			sign.initVerify(publicKey);	//�־��� ����Ű�� ������ �ʱ�ȭ
			sign.update(Message_Digest.getBytes()); //�����Ϸ��� �ϴ� �����͸� ������Ʈ
			byte[] byt = Base64.getDecoder().decode(Digital_sign.getBytes());
			//������ ������ �� �� ���ڵ� �߱� ������ ���ڵ� �ϴ� ������ �����ش�. (������ byteŸ������ �ϱ� ����)

			if(sign.verify(byt)) {	//�޼��� ��������Ʈ�� ������ ������ ��ȣȭ �� ����� ��ġ�ϴ� ���
				System.out.println("Integrity verification SUCCESS"); //���Ἥ ���� ����
				return Message_Digest;		//�޼��� ��������Ʈ ����
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Integrity verification FAILED");
		return null;
	}
	
}