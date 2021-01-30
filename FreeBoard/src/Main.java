import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        membership m1 = new membership();
//        m1.Signup();

        Scanner sc = new Scanner(System.in);

        String user_id;
        String user_password;

        System.out.println("===== 로그인 =====");
        System.out.print("아이디: ");
        user_id = sc.nextLine();
        System.out.print("비밀번호: ");
        user_password = sc.nextLine();

        Check c1 = new Check(user_id, user_password);

        BoardControl B1 = new BoardControl(user_id);
        B1.Write();
        B1.Delete();
    }
}
