package security_project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;


class Certificate{																// �������
	private static KeyPairGenerator keyPairGen;							// ����� Ű ������
	private static KeyPair keyPair;										// ����� Ű ��
	private static PublicKey publicKey;									// ����� ����Ű
	private static PrivateKey privateKey;								// ����� ����Ű
	private static List<String> user_name = new ArrayList<String>();	// ����� �����ϴ� ���� �̸�
	private static List<PublicKey> user_key = new ArrayList<PublicKey>();		// ����� �����ϴ� ������ ����Ű 
																		// �� �� ArrayList�� ����ϸ� ���� index�� ������

	public Certificate() {														// �⺻ ������
		SecureRandom secureRandom = new SecureRandom();					// �������� Ű �����ϱ� ���� �����Լ�
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");			// RSA ��ȣȭ �˰���
			keyPairGen.initialize(1024,secureRandom);
			keyPair = keyPairGen.genKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	} //����������� �����Ѵ�.


	public String make_cert(PublicKey userpubkey, String user) {			// ������ �����
		// a �� ������ ������ ����Ű, b �� �̸�
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");	// ������ SHA256withRSA ������� ��
			sign.initSign(privateKey);									// ������ ���� ��ü �ʱ�ȭ
			sign.update(userpubkey.getEncoded());							// ������ ����Ű�� ����� ����Ű�� ����
			byte[] cert = sign.sign();									// ������ byte�迭�� �Ҵ�. �̸� �������� ����� ����
			String str = null;
			try {														// �ؽ�Ʈ������ �����Ͽ� ����� ���� String���� �־��ִ� ���� 
				BufferedWriter bw = new BufferedWriter(new FileWriter(user+"_Certificate.txt"));
				bw.write(cert.toString());
				bw.newLine();
				bw.close();
				str = Base64.getEncoder().encodeToString(cert);
				//String���� ��ȯ�ϱ� ���� ���ڵ��� (txt���Ͽ� byte���� ���� ��� �������� �� ���� �޶����� ������ string���)
				System.out.println("completed\n file name : "+user+"_Certificate.txt") ;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			user_key.add(userpubkey);									// ��������� ������ ����Ű ����
			user_name.add(user);										// ��������� �����̸� ����
			
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	

	public PublicKey ver(String certificate, String cert_own) {				// ������ ����
																		// byte�迭�� ������, �������� ������ �Ķ���ͷ� ���� 
		boolean ispass = false;											// ������ ��� �ߴ��� �ƴ��� Ȯ�ο� boolean ����
		PublicKey cert_pubkey = null;										// ���� ������� ����Ű�� ���� �� String ����
		for (int i =0; i<user_key.size();i++) {							// ���� ������� �������� ������ ã��, �� ������ ����Ű�� �޾ƿ�
			if (user_name.get(i).equals(cert_own)) {
				cert_pubkey = user_key.get(i);
				break;
			}
		}
		try {															// ����
			Signature sign = Signature.getInstance("SHA256withRSA");	// ������ SHA256withRSA ������� �� 
			sign.initVerify(publicKey);									// ������ ���� ��ü �ʱ�ȭ
			sign.update(cert_pubkey.getEncoded());							// ������ ����Ű�� ����� ����Ű�� ����
			byte[] byt = Base64.getDecoder().decode(certificate.getBytes());
			//�������� ���ڵ� �ؼ� ���� �߱� ������ byte�� ���ڵ� ���ش�. (������ byteŸ������ �ϱ� ����)
			ispass =  sign.verify(byt);							// ���� ���
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ispass==true) {												// ���� ����
			System.out.println("PASS");
			return cert_pubkey;
		}
		else {															// ���� ����
			System.out.println("UNPASS");
			return null;
		}
	}
	
}
