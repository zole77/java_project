import java.util.Scanner;

public class membership {
    String user_name;
    int user_age;
    String user_id;
    String user_password;

    public void Signup(){
        System.out.println("회원가입을 시작합니다.");
        System.out.println("아이디를 입력해주세요.");
        Scanner sc = new Scanner(System.in);
        System.out.println("이름: ");
        user_name = sc.nextLine();
        System.out.println("나이: ");
        user_age = sc.nextInt();
        sc.nextLine();
        System.out.println("아이디: ");
        user_id = sc.nextLine();
        System.out.println("패스워드: ");
        user_password = sc.nextLine();

        System.out.println("회원가입이 완료되었습니다 !");
    }

}
