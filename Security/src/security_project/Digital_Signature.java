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
	
	public KeyPair generateRsaKeyPair() { //개인키 공개키 쌍을 생성해주는 메소드
		
		SecureRandom secureRandom = new SecureRandom();
		
		try {
					
			// 키페어 생성
			keyPairGen = KeyPairGenerator.getInstance("RSA");	 //	RSA를 인자로 해서 객체의 인스턴스 획득
			keyPairGen.initialize(1024,secureRandom);			 //키 사이즈 1024비트,secureRandom로 인해 실행될때마다 값 바뀌도록 키페어 초기화 
			keypair = keyPairGen.genKeyPair();		  			
			
			return keypair; 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//개인키로 메세지 다이제스트 서명
	public String signature(String Message_Digest, PrivateKey privateKey) {
		
		try {
			Signature sign = Signature.getInstance("SHA256withRSA"); //해시함수 SHA256으로 메세지 다이제스트 한 값을 RSA로 디지털서명
			sign.initSign(privateKey); //주어진 개인키로 서명을 초기화 한다.
			sign.update(Message_Digest.getBytes());  //서명 하고자 하는 데이터를 업데이트 한다.
			String str = Base64.getEncoder().encodeToString(sign.sign()); 
			//String으로 변환하기 위해 인코딩함 (txt파일에 byte값을 넣을 경우 추출했을 때 값이 달라지기 때문에 string사용)
			
			return str;  //업데이트 된 데이터에 대한 최종 서명의 base64인코딩 값을 리턴
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//메세지 다이제스트와 공개키로 서명을 복호화한것을 비교해서 일치하면 메세지 다이제스트 리턴
	public String sig_check(String Message_Digest, String Digital_sign, PublicKey publicKey ) {
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");//서명방법을  SHA256withRSA로 지정
			sign.initVerify(publicKey);	//주어진 공개키로 서명을 초기화
			sign.update(Message_Digest.getBytes()); //검증하려고 하는 데이터를 업데이트
			byte[] byt = Base64.getDecoder().decode(Digital_sign.getBytes());
			//디지털 서명을 할 때 인코딩 했기 때문에 디코딩 하는 과정을 거쳐준다. (검증은 byte타입으로 하기 때문)

			if(sign.verify(byt)) {	//메세지 다이제스트와 디지털 서명을 복호화 한 결과가 일치하는 경우
				System.out.println("Integrity verification SUCCESS"); //무결서 검증 성공
				return Message_Digest;		//메세지 다이제스트 리턴
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Integrity verification FAILED");
		return null;
	}
	
}