package security_project;

import java.util.Scanner;
import java.security.*;

public class Main {

	public static void main(String[] args) {
		
		// ������ �ʿ��� Ŭ���� ��ü ȣ��
		Message_Digest MD = new Message_Digest();
		Digital_Signature Sig = new Digital_Signature();
		Certificate CA = new Certificate();
		Assemble Assem = new Assemble();
		Symmetric Sym = new Symmetric();
		RSA Rsa = new RSA();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("\n                   Security Project Team");
		System.out.println("\n                                   ��η�, �ռ���, �ڹ���, ������, ������");
		System.out.println("\n                           Start");
		System.out.println("                          (Enter)");
		scan.nextLine();
		
		//Alice�� Bob�� ����Ű ����Ű �� ���� ��Ʈ
		System.out.println("\n============================================================\n");
		System.out.println("                       Create Keypair");
		System.out.println("\n============================================================");
		System.out.println("Create Alice keypair...");
		KeyPair A_keypair = Sig.generateRsaKeyPair(); // �ٸ����� Ű �� �����Ѵ�.
		PublicKey A_public = A_keypair.getPublic(); // Ű ���� ����Ű�� �����Ѵ�.
		PrivateKey A_private = A_keypair.getPrivate(); // Ű ���� ����Ű�� �����Ѵ�.
		System.out.println("completed\n Alice's public key : " + A_public); // ����Ű�� ����Ѵ�. ����Ű �����
		System.out.println("\nCreate Bob keypair...");
		KeyPair B_keypair = Sig.generateRsaKeyPair();
		PublicKey B_public = B_keypair.getPublic();
		PrivateKey B_private = B_keypair.getPrivate();
		System.out.println("completed\n Bob's public key : " + B_public);
		
		// Alice�� Bob�� ����Ű ������ ����
		System.out.println("\n============================================================\n");
		System.out.println("                    Create Certification");
		System.out.println("\n============================================================");
		System.out.println("Create Alice's public key certificate...");
		String A_cert = CA.make_cert(A_public, "Alice");
		System.out.println(" contents(Encoding) : " + A_cert);
		String B_cert = CA.make_cert(B_public, "Bob");
		System.out.println(" contents(Encoding) : " + B_cert);
		
		
		System.out.println("\n============================================================\n");
		System.out.println("                          Encryption");
		System.out.println("\n============================================================");
		System.out.print("Enter the Plain Text (only English) \n L ");
		String plain_text = scan.nextLine();
		
		
		// �ؽ��Լ� SHA256���� �޼��� ����
		System.out.println("\n******************** Message Digest ********************\n");
		System.out.println("Using SHA-256...\nHashing text is '" + plain_text + "'");
		System.out.println("\nMessage Digesting...");
		String hash_text = MD.Hashing(plain_text); // SHA256���� �ؽ�
		if(hash_text != null) // �������� Ȯ�� - �����ϸ� ����� ����ϰ�, �����ϸ� ���α׷��� �����Ѵ�.
			System.out.println("completed\nresult : " + hash_text);
		else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		// Alice ������ ���� �κ�
		System.out.println("\n******************** Digital Signature ********************\n");
		System.out.println("Signature with Alice private key...");
		String A_sig = Sig.signature(hash_text, A_private); //Alice�� ����Ű�� �ؽð��� ������ �����Ѵ�.
		if(A_sig != null)
			System.out.println("completed\n result : " + A_sig);
		else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//AES��ȣȭ �ϱ� �� �����͸� ������ ����
		System.out.println("\n******************** Assemble Data ********************\n");
		System.out.println("Message+Signature+Certification");
		//�޼��� + ���� + �������� ���  txt���Ϸ� �����ϰ�, txt���� ������ Data�� �����Ѵ�.
		String Data = Assem.BeforeAES(plain_text, A_sig, A_cert);
		System.out.println("completed\n file name : beforeAES.txt\n\n" + Data); //txt���� ��� ���

		
		//��ĪŰ�� ���� �����͸� ��ȣȭ
		System.out.println("\n******************** Encryption Data ********************\n");
		System.out.println("Create symmetric key...");
		String AESkey = Sym.getAESKey(); //��ĪŰ�� �����ϴ� ����
		if(AESkey != null) {
			System.out.println("completed\n"); //��ĪŰ��ȣ�� ���Ű�̹Ƿ� �������� = ��� ��� ����
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//��ĪŰ�� ��Ƶ� �����͸� ��ȣȭ �Ѵ�.
		System.out.println("\nEncrypt Message...");
		String Encrypt_Message = Sym.encryptText(Data); //��Ƶ� ������ AES��ȣȭ
		if(Encrypt_Message != null) {
			System.out.println("completed\n result : " + Encrypt_Message);
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		//Bob�� ����Ű�� ��ĪŰ�� ��ȣȭ (��ĪŰ�� Ű ������ �ν����� �ʰ� String���� �ν��Ѵ�.)
		System.out.println("\nCreate Digiatal Envelope...");
		PublicKey get_B = CA.ver(B_cert, "Bob"); //Bob�� ����Ű ���������� ���� ����Ű�� ��´�.
		String Envelope = Rsa.Encryption(AESkey, get_B); //������ ���� Bob�� ����Ű�� ��ĪŰ�� ��ȣȭ
		if(Envelope != null) {
			System.out.println("completed\n result : " + Envelope);
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//����� txt���Ϸ� ����
		System.out.println("\n******************** Transfer ********************\n");
		System.out.println("Assemble encrypt Message + Digital envelope");
		Assem.BeforeSEND(Encrypt_Message, Envelope); //���� �� ��ȣȭ�� �޼��� + ������ ������ txt���Ϸ� ��ġ�� ����
		System.out.println("completed\n file name : beforeSend.txt");
		System.out.println("\nAlice sends data to Bob...");
		
		
		
		System.out.println("\n============================================================\n");
		System.out.println("                          Decryption");
		System.out.println("                           (Enter)");
		System.out.println("\n============================================================");
		
		
		//���� ���Ͽ��� ��ĪŰ�� ã�� ����
		System.out.println("\n******************** Find Symmetric Key ********************\n");
		System.out.println("File Load...\n file name : beforeSend.txt\n");
		String file = Assem.LoadFile(); //Alice���� ���� ������ ������ file������ �����Ѵ�.
		System.out.println(file);
		
		
		//������ ������ ���� ����Ű�� ��ȣȭ �Ͽ� ��ĪŰ string���� ��°���
		System.out.println("\nDecrytion Digital Envelope with Bob private key...");
		String get_Envelope = Assem.GetEnvelope(file); //file���� ������ �������� ����
		String get_key = Rsa.Decryption(get_Envelope, B_private); //����Ű�� ������ ������ ��ȣȭ �ϴ� ����
		if(get_key != null) {
			if(get_key.equals(AESkey))
				//get_key���� ��ȣȭ�ؼ� ��� ��ĪŰ string���̰�, AESkey�� �ٸ����� ����� ��ĪŰ�� string���̴�.
				//������ ������ ��ȣȭ �� secretkey���� �ƴ� string���� return �޾ұ� ������ format���� ��ġ�ϸ� ���� ��ĪŰ�� �����Ѵ�.
				System.out.println("Find Symmetric Key!");
			else {
				System.out.println("NOT Symmetric Key");
				System.exit(0);
			}
				
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//������ ��ȣȭ �� ��ĪŰ�� ��ȣȭ�� �޼����� AES��ȣȭ �Ѵ�.
		System.out.println("\nDecrytion Encrypt Message with Symmetric key...");
		String get_Data = Assem.GetEncrypted(file); //���۹��� ���Ͽ��� ��ȣȭ�� �޼��� �κ� ����
		String load = Sym.decryptText(get_Data); // ������ �޼����� AES��ȣȭ �ؼ� String������ ����
		if(load != null) {
			System.out.println("completed\n" + load);
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		System.out.println("\n******************** Check Digital Signature ********************\n");
		String get_message = Assem.AfterAES_getMsg(load); //��ȣȭ�� ��ȣȭ �޼������� �򹮸޼��� ����
		System.out.println("Using SHA-256...\nHashing text is '" + get_message + "'");
		System.out.println("\nMessage Digesting...");
		String get_digest = MD.Hashing(get_message); //�ؽ��Լ��� �޼��� ����
		if(get_digest != null)
			System.out.println("completed\nresult : " + get_digest);
		else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		System.out.println("Find Ailce public key...");
		String get_cert = Assem.AfterAES_getCERT(load); //��ȣȭ�� ��ȣȭ �޼������� ������ ����
		PublicKey get_public = CA.ver(get_cert, "Alice"); //���������� �ٸ����� ����Ű ����
		
		
		//������ ������ ���� �ؽð��� ��ȣȭ�� �޼������� ���� �޼����� �ؽ��� ���� ��ġ�ϴ��� Ȯ���ϴ� ����
		System.out.println("\n******************** Check Message Integrity ********************\n");
		System.out.println("Compare...");
		String get_ds = Assem.AfterAES_getDS(load); //��ȣȭ�� ��ȣȭ �޼������� ������ ���� ����
		
		//�ٸ����� ����Ű�� ������ ���� ������ �ؼ� ���� �ؽð��� ��ȣȭ�� ��ȣȭ �޼������� ������ ���� �ؽ��� ���� ��ġ�ϴ��� ���� Ȯ�����ִ� ����
		String result = Sig.sig_check(get_digest, get_ds, get_public);
		System.out.println("\nresult : " + result); //��ġ���� �� �ؽð��� result�� �����ϰ� ���������� ����Ѵ�.
		
		System.out.println("System exit");
		System.exit(0);
		
		scan.close();
		
		
		
	}

}
