package security_project;

import java.io.*;

class Assemble{ //�����͸� ��ġ�� �����ϴ� Ŭ����
	
	public String BeforeAES(String msg, String ds, String ser) { // AES �ϱ� �� (�޽���, ����, �������� �ϳ��� ����)

		String r = "message : "+ msg+"\n"+
		"digital sign : "+ds+"\n"+
		"certification : "+ser;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("deforeAES.txt")); //�ϳ��� ���� �ӽ� ������ txt����
			bw.write(r);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public void BeforeSEND(String encrpted_msg, String denvelope) {				// ���� �ϱ� �� (��ȣȭ�� �޼����� �����к��� ��ġ��)
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("beforeSend.txt")); //�ϳ��� ���� ������ txt����
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
	
	public String LoadFile() {	// ���� �� ���� �б�
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
		return r; //������ �о� string������ �����Ѵ�.
	}
	
	//���峻�� : �� \n ���峻�� : �� ... �̷������� ����Ǿ� ����
	//�׷��� : �� ������ �迭�� ����� r[0]���� ���峻����  r[1]���� ���� ����Ǿ� �ִ�. �׷��� ��� r[1]�� ����
	
	public String GetEncrypted(String a) { // ���� ���Ͽ��� ��ȣȸ�� �޼��� ������
		String tmp[] = a.split("\n");
		String r[] = tmp[0].split(": ");
		
		return r[1];
	}
	
	public String GetEnvelope(String a) { // ���� ���Ͽ��� ���� ������
		String tmp[] = a.split("\n");
		String r[] = tmp[1].split(": ");
		return r[1];
	}
	
	public String AfterAES_getMsg(String decrypted) { // AES ��, �޼��� ���
		String tmp[] = decrypted.split("\n");
		String r[] = tmp[0].split(": ");
		return r[1];
	}
	
	public String AfterAES_getDS(String decrypted) { // AES ��, ���� ���
		String tmp[] = decrypted.split("\n");
		String r[] = tmp[1].split(": ");
		return r[1];
	}
	
	public String AfterAES_getCERT(String decrypted) { // AES ��, ������ ���
		String tmp[] = decrypted.split("\n");
		String r[] = tmp[2].split(": ");
		return r[1];
	}
	
}
