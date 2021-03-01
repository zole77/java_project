package security_project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Message_Digest {
	
	public String Hashing(String str) {	// 메인에서 평문을 넘겨 받아서 해싱하는 메소드
        String hashString = "";	// hashString 변수 초기화
        try {
        	
            // java.security.MessageDigest 안에 있는 MessageDigest클래스
            // MD2, MD4, MD5, SHA-1, SHA-256, SHA-512 선택가능
            MessageDigest sh = MessageDigest.getInstance("SHA-256");	// SHA-256 선택
            sh.update(str.getBytes()); //update()는 byte타입으로  다이제스트를 갱신
            
            byte byteData[] = sh.digest();	
            // 패딩과 같은 최종 작업을 수행, 해시 계산을 마무리하는 클래스
            // 호출이 완료되면 다이제스트가 재설정됩니다.
            StringBuffer sb = new StringBuffer();	//	스트링버퍼 생성자
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                // 16진수 문자열로 변환하여 스트링버퍼에 추가하는 과정
            }
            hashString = sb.toString();	// 초기화했던 변수 hashString 에 스트링버퍼 sb 값을 저장
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            hashString = null;
        }
        return hashString;	// hashString 값 리턴
    }
}
