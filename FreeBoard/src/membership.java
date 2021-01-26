import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class membership {
    String user_name;
    String user_age;
    String user_id;
    String user_password;

    public membership(String user_id, String user_password) {
        this.user_id = user_id;
        this.user_password = user_password;
    }

    public void Signup() {
        System.out.println("회원가입을 시작합니다.");

        try {
            FileWriter fw = new FileWriter("C:/Users/82109/IdeaProjects/FreeBoard/src/membership.txt", true);
            System.out.println("아이디를 입력해주세요.");
            Scanner sc = new Scanner(System.in);
            System.out.println("이름: ");
            user_name = sc.nextLine();
            System.out.println("나이: ");
            user_age = sc.nextLine();
            System.out.println("아이디: ");
            user_id = sc.nextLine();
            System.out.println("패스워드: ");
            user_password = sc.nextLine();
            System.out.println("회원가입이 완료되었습니다 !");
            System.out.println(user_name);
            System.out.println(user_age);
            System.out.println(user_id);
            System.out.println(user_password);
            fw.write(user_name + "\r\n");
            fw.write(user_age + "\r\n");
            fw.write(user_id + "\r\n");
            fw.write(user_password + "\r\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Login(){

    }
}
