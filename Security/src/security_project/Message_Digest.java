package security_project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Message_Digest {
	
	public String Hashing(String str) {	// ���ο��� ���� �Ѱ� �޾Ƽ� �ؽ��ϴ� �޼ҵ�
        String hashString = "";	// hashString ���� �ʱ�ȭ
        try {
        	
            // java.security.MessageDigest �ȿ� �ִ� MessageDigestŬ����
            // MD2, MD4, MD5, SHA-1, SHA-256, SHA-512 ���ð���
            MessageDigest sh = MessageDigest.getInstance("SHA-256");	// SHA-256 ����
            sh.update(str.getBytes()); //update()�� byteŸ������  ��������Ʈ�� ����
            
            byte byteData[] = sh.digest();	
            // �е��� ���� ���� �۾��� ����, �ؽ� ����� �������ϴ� Ŭ����
            // ȣ���� �Ϸ�Ǹ� ��������Ʈ�� �缳���˴ϴ�.
            StringBuffer sb = new StringBuffer();	//	��Ʈ������ ������
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                // 16���� ���ڿ��� ��ȯ�Ͽ� ��Ʈ�����ۿ� �߰��ϴ� ����
            }
            hashString = sb.toString();	// �ʱ�ȭ�ߴ� ���� hashString �� ��Ʈ������ sb ���� ����
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            hashString = null;
        }
        return hashString;	// hashString �� ����
    }
}
