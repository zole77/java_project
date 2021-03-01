package security_project;

import java.util.Scanner;
import java.security.*;

public class Main {

	public static void main(String[] args) {
		
		// 구현에 필요한 클래스 객체 호출
		Message_Digest MD = new Message_Digest();
		Digital_Signature Sig = new Digital_Signature();
		Certificate CA = new Certificate();
		Assemble Assem = new Assemble();
		Symmetric Sym = new Symmetric();
		RSA Rsa = new RSA();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("\n                   Security Project Team");
		System.out.println("\n                                   김민령, 손석준, 박범수, 장희제, 공지훈");
		System.out.println("\n                           Start");
		System.out.println("                          (Enter)");
		scan.nextLine();
		
		//Alice와 Bob의 공개키 개인키 쌍 생성 파트
		System.out.println("\n============================================================\n");
		System.out.println("                       Create Keypair");
		System.out.println("\n============================================================");
		System.out.println("Create Alice keypair...");
		KeyPair A_keypair = Sig.generateRsaKeyPair(); // 앨리스의 키 페어를 생성한다.
		PublicKey A_public = A_keypair.getPublic(); // 키 페어에서 공개키를 추출한다.
		PrivateKey A_private = A_keypair.getPrivate(); // 키 페어에서 개인키를 추출한다.
		System.out.println("completed\n Alice's public key : " + A_public); // 공개키만 출력한다. 개인키 비공개
		System.out.println("\nCreate Bob keypair...");
		KeyPair B_keypair = Sig.generateRsaKeyPair();
		PublicKey B_public = B_keypair.getPublic();
		PrivateKey B_private = B_keypair.getPrivate();
		System.out.println("completed\n Bob's public key : " + B_public);
		
		// Alice와 Bob의 공개키 인증서 생성
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
		
		
		// 해시함수 SHA256으로 메세지 압축
		System.out.println("\n******************** Message Digest ********************\n");
		System.out.println("Using SHA-256...\nHashing text is '" + plain_text + "'");
		System.out.println("\nMessage Digesting...");
		String hash_text = MD.Hashing(plain_text); // SHA256으로 해싱
		if(hash_text != null) // 성공여부 확인 - 성공하면 결과를 출력하고, 실패하면 프로그램을 종료한다.
			System.out.println("completed\nresult : " + hash_text);
		else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		// Alice 디지털 서명 부분
		System.out.println("\n******************** Digital Signature ********************\n");
		System.out.println("Signature with Alice private key...");
		String A_sig = Sig.signature(hash_text, A_private); //Alice의 개인키로 해시값을 디지털 서명한다.
		if(A_sig != null)
			System.out.println("completed\n result : " + A_sig);
		else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//AES암호화 하기 전 데이터를 모으는 과정
		System.out.println("\n******************** Assemble Data ********************\n");
		System.out.println("Message+Signature+Certification");
		//메세지 + 서명 + 인증서를 모아  txt파일로 저장하고, txt파일 내용을 Data에 저장한다.
		String Data = Assem.BeforeAES(plain_text, A_sig, A_cert);
		System.out.println("completed\n file name : beforeAES.txt\n\n" + Data); //txt파일 결과 출력

		
		//대칭키로 모은 데이터를 암호화
		System.out.println("\n******************** Encryption Data ********************\n");
		System.out.println("Create symmetric key...");
		String AESkey = Sym.getAESKey(); //대칭키를 생성하는 과정
		if(AESkey != null) {
			System.out.println("completed\n"); //대칭키암호는 비밀키이므로 공개안함 = 결과 출력 없음
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//대칭키로 모아둔 데이터를 암호화 한다.
		System.out.println("\nEncrypt Message...");
		String Encrypt_Message = Sym.encryptText(Data); //모아둔 데이터 AES암호화
		if(Encrypt_Message != null) {
			System.out.println("completed\n result : " + Encrypt_Message);
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		//Bob의 공개키로 대칭키를 암호화 (대칭키는 키 값으로 인식하지 않고 String으로 인식한다.)
		System.out.println("\nCreate Digiatal Envelope...");
		PublicKey get_B = CA.ver(B_cert, "Bob"); //Bob의 공개키 인증서에서 밥의 공개키를 얻는다.
		String Envelope = Rsa.Encryption(AESkey, get_B); //위에서 얻은 Bob의 공개키로 대칭키를 암호화
		if(Envelope != null) {
			System.out.println("completed\n result : " + Envelope);
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//결과를 txt파일로 전송
		System.out.println("\n******************** Transfer ********************\n");
		System.out.println("Assemble encrypt Message + Digital envelope");
		Assem.BeforeSEND(Encrypt_Message, Envelope); //전송 전 암호화된 메세지 + 디지털 봉투를 txt파일로 합치는 과정
		System.out.println("completed\n file name : beforeSend.txt");
		System.out.println("\nAlice sends data to Bob...");
		
		
		
		System.out.println("\n============================================================\n");
		System.out.println("                          Decryption");
		System.out.println("                           (Enter)");
		System.out.println("\n============================================================");
		
		
		//받은 파일에서 대칭키를 찾는 과정
		System.out.println("\n******************** Find Symmetric Key ********************\n");
		System.out.println("File Load...\n file name : beforeSend.txt\n");
		String file = Assem.LoadFile(); //Alice에게 받은 파일의 내용을 file변수에 저장한다.
		System.out.println(file);
		
		
		//디지털 봉투를 밥의 개인키로 복호화 하여 대칭키 string값을 얻는과정
		System.out.println("\nDecrytion Digital Envelope with Bob private key...");
		String get_Envelope = Assem.GetEnvelope(file); //file에서 디지털 봉투값을 추출
		String get_key = Rsa.Decryption(get_Envelope, B_private); //개인키로 디지털 봉투를 복호화 하는 과정
		if(get_key != null) {
			if(get_key.equals(AESkey))
				//get_key값은 복호화해서 얻는 대칭키 string값이고, AESkey는 앨리스가 사용한 대칭키의 string값이다.
				//디지털 봉투를 복호화 해 secretkey값이 아닌 string값을 return 받았기 때문에 format값이 일치하면 같은 대칭키로 간주한다.
				System.out.println("Find Symmetric Key!");
			else {
				System.out.println("NOT Symmetric Key");
				System.exit(0);
			}
				
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		//위에서 복호화 한 대칭키로 암호화된 메세지를 AES복호화 한다.
		System.out.println("\nDecrytion Encrypt Message with Symmetric key...");
		String get_Data = Assem.GetEncrypted(file); //전송받은 파일에서 암호화된 메세지 부분 추출
		String load = Sym.decryptText(get_Data); // 추출한 메세지를 AES복호화 해서 String값으로 저장
		if(load != null) {
			System.out.println("completed\n" + load);
		}else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		System.out.println("\n******************** Check Digital Signature ********************\n");
		String get_message = Assem.AfterAES_getMsg(load); //복호화한 암호화 메세지에서 평문메세지 추출
		System.out.println("Using SHA-256...\nHashing text is '" + get_message + "'");
		System.out.println("\nMessage Digesting...");
		String get_digest = MD.Hashing(get_message); //해시함수로 메세지 압축
		if(get_digest != null)
			System.out.println("completed\nresult : " + get_digest);
		else {
			System.out.println("Program error\nexit");
			System.exit(0);
		}
		
		
		System.out.println("Find Ailce public key...");
		String get_cert = Assem.AfterAES_getCERT(load); //복호화한 암호화 메세지에서 인증서 추출
		PublicKey get_public = CA.ver(get_cert, "Alice"); //인증서에서 앨리스의 공개키 추출
		
		
		//디지털 서명에서 얻은 해시값과 암호화된 메세지에서 얻은 메세지를 해시한 값이 일치하는지 확인하는 과정
		System.out.println("\n******************** Check Message Integrity ********************\n");
		System.out.println("Compare...");
		String get_ds = Assem.AfterAES_getDS(load); //복호화한 암호화 메세지에서 디지털 서명 추출
		
		//앨리스의 공개키로 디지털 서명 검증을 해서 얻은 해시값과 복호화한 암호화 메세지에서 추출한 평문을 해시한 값이 일치하는지 까지 확인해주는 과정
		String result = Sig.sig_check(get_digest, get_ds, get_public);
		System.out.println("\nresult : " + result); //일치했을 때 해시값을 result로 추출하고 마지막으로 출력한다.
		
		System.out.println("System exit");
		System.exit(0);
		
		scan.close();
		
		
		
	}

}
