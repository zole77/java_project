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


class Certificate{																// 인증기관
	private static KeyPairGenerator keyPairGen;							// 기관의 키 생성기
	private static KeyPair keyPair;										// 기관의 키 쌍
	private static PublicKey publicKey;									// 기관의 공개키
	private static PrivateKey privateKey;								// 기관의 개인키
	private static List<String> user_name = new ArrayList<String>();	// 기관이 보유하는 유저 이름
	private static List<PublicKey> user_key = new ArrayList<PublicKey>();		// 기관의 보유하는 유저의 공개키 
																		// 둘 다 ArrayList를 사용하며 같은 index를 공유함

	public Certificate() {														// 기본 생성자
		SecureRandom secureRandom = new SecureRandom();					// 랜덤으로 키 생성하기 위한 랜덤함수
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");			// RSA 암호화 알고리즘
			keyPairGen.initialize(1024,secureRandom);
			keyPair = keyPairGen.genKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	} //인증기관으로 가정한다.


	public String make_cert(PublicKey userpubkey, String user) {			// 인증서 만들기
		// a 는 서명할 주인의 공개키, b 는 이름
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");	// 서명을 SHA256withRSA 방법으로 함
			sign.initSign(privateKey);									// 서명을 위해 개체 초기화
			sign.update(userpubkey.getEncoded());							// 유저의 공개키를 기관의 개인키로 서명
			byte[] cert = sign.sign();									// 서명을 byte배열에 할당. 이를 인증서로 사용할 예정
			String str = null;
			try {														// 텍스트파일을 생성하여 서명된 값을 String으로 넣어주는 과정 
				BufferedWriter bw = new BufferedWriter(new FileWriter(user+"_Certificate.txt"));
				bw.write(cert.toString());
				bw.newLine();
				bw.close();
				str = Base64.getEncoder().encodeToString(cert);
				//String으로 변환하기 위해 인코딩함 (txt파일에 byte값을 넣을 경우 추출했을 때 값이 달라지기 때문에 string사용)
				System.out.println("completed\n file name : "+user+"_Certificate.txt") ;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			user_key.add(userpubkey);									// 인증기관에 유저의 공개키 저장
			user_name.add(user);										// 인증기관에 유저이름 저장
			
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	

	public PublicKey ver(String certificate, String cert_own) {				// 인증서 검증
																		// byte배열의 인증서, 인증서의 주인을 파라미터로 받음 
		boolean ispass = false;											// 검증을 통과 했는지 아닌지 확인용 boolean 변수
		PublicKey cert_pubkey = null;										// 인증 기관에서 공개키를 돌려 줄 String 변수
		for (int i =0; i<user_key.size();i++) {							// 인증 기관에서 인증서의 주인을 찾아, 그 주인의 공개키를 받아옴
			if (user_name.get(i).equals(cert_own)) {
				cert_pubkey = user_key.get(i);
				break;
			}
		}
		try {															// 검증
			Signature sign = Signature.getInstance("SHA256withRSA");	// 검증을 SHA256withRSA 방법으로 함 
			sign.initVerify(publicKey);									// 검증을 위해 개체 초기화
			sign.update(cert_pubkey.getEncoded());							// 유저의 공개키를 기관의 공개키로 검증
			byte[] byt = Base64.getDecoder().decode(certificate.getBytes());
			//인증서를 인코딩 해서 리턴 했기 때문에 byte로 디코딩 해준다. (검증을 byte타입으로 하기 때문)
			ispass =  sign.verify(byt);							// 검증 결과
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ispass==true) {												// 검증 성공
			System.out.println("PASS");
			return cert_pubkey;
		}
		else {															// 검증 실패
			System.out.println("UNPASS");
			return null;
		}
	}
	
}
