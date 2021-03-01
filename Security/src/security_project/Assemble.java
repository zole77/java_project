package security_project;

import java.io.*;

class Assemble{ //데이터를 합치고 분해하는 클래스
	
	public String BeforeAES(String msg, String ds, String ser) { // AES 하기 전 (메시지, 서명, 인증서를 하나로 묶기)

		String r = "message : "+ msg+"\n"+
		"digital sign : "+ds+"\n"+
		"certification : "+ser;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("deforeAES.txt")); //하나로 묶어 임시 저장할 txt파일
			bw.write(r);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public void BeforeSEND(String encrpted_msg, String denvelope) {				// 전송 하기 전 (암호화된 메세지와 디지털봉투 합치기)
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("beforeSend.txt")); //하나로 묶어 전송할 txt파일
			bw.write("encrypted message : "+encrpted_msg);
			bw.newLine();
			bw.write("digital envelope : "+denvelope);
			bw.newLine();
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String LoadFile() {	// 전송 후 파일 읽기
		String r="";
		try {
			File file = new File("beforeSend.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				r += tmp+"\n";
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r; //파일을 읽어 string값으로 리턴한다.
	}
	
	//저장내용 : 값 \n 저장내용 : 값 ... 이런식으로 저장되어 있음
	//그래서 : 로 나누어 배열을 만들면 r[0]에는 저장내용이  r[1]에는 값이 저장되어 있다. 그래서 모두 r[1]값 리턴
	
	public String GetEncrypted(String a) { // 읽은 파일에서 암호회된 메세지 얻어오기
		String tmp[] = a.split("\n");
		String r[] = tmp[0].split(": ");
		
		return r[1];
	}
	
	public String GetEnvelope(String a) { // 읽은 파일에서 봉투 얻어오기
		String tmp[] = a.split("\n");
		String r[] = tmp[1].split(": ");
		return r[1];
	}
	
	public String AfterAES_getMsg(String decrypted) { // AES 후, 메세지 얻기
		String tmp[] = decrypted.split("\n");
		String r[] = tmp[0].split(": ");
		return r[1];
	}
	
	public String AfterAES_getDS(String decrypted) { // AES 후, 서명 얻기
		String tmp[] = decrypted.split("\n");
		String r[] = tmp[1].split(": ");
		return r[1];
	}
	
	public String AfterAES_getCERT(String decrypted) { // AES 후, 인증서 얻기
		String tmp[] = decrypted.split("\n");
		String r[] = tmp[2].split(": ");
		return r[1];
	}
	
}
