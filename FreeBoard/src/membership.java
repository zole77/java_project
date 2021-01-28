import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.sql.*;

public class membership {
    String user_name;
    int user_age;
    String user_id;
    String user_password;

//    public membership(String user_id, String user_password) {
//        this.user_id = user_id;
//        this.user_password = user_password;
//    }

    public void Signup() {
        System.out.println("회원가입을 시작합니다.");
        OpenDB S1 = new OpenDB();

        System.out.println("아이디를 입력해주세요.");
        Scanner sc = new Scanner(System.in);
        System.out.println("아이디: ");
        user_id = sc.nextLine();
        System.out.println("패스워드: ");
        user_password = sc.nextLine();
        System.out.println("이름: ");
        user_name = sc.nextLine();
        System.out.println("나이: ");
        user_age = sc.nextInt();
        sc.nextLine();
        System.out.println("회원가입이 완료되었습니다 !");
        System.out.println(user_id);
        System.out.println(user_password);
        System.out.println(user_name);
        System.out.println(user_age);

        S1.update(user_id, user_password, user_name, user_age);
        int i = 1;
        S1.select("SELECT * FROM member_info");
        try{
            while(S1.rs.next()){
                System.out.printf("===============\n %d번째 반복중입니다.\n ============= \n", i++);
                System.out.println(S1.rs.getString("id")+S1.rs.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        S1.close();

    }

    public void Login(){

    }
}
