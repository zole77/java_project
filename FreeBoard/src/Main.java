import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String menu;
        String user_id;
        String user_password;

        while(true){
            System.out.println("게시판 프로그램에 오신것을 환영합니다");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");

            Scanner sc = new Scanner(System.in);
            menu = sc.nextLine();
            if(menu.equals("1")){
                System.out.println("===== 로그인 =====");
                System.out.print("아이디: ");
                user_id = sc.nextLine();
                System.out.print("비밀번호: ");
                user_password = sc.nextLine();
                Check c1 = new Check(user_id, user_password);
                if (c1.Login_check()) {
                    clearScreen();
                    while(true){
                        System.out.println("===== 메인 메뉴 =====");
                        System.out.println("1. 글쓰기");
                        System.out.println("2. 글 수정하기");
                        System.out.println("3. 글 삭제하기");
                        System.out.println("4. 글 조회하기");
                        System.out.println("5. 종료");
                        menu = sc.nextLine();
                        BoardControl B1 = new BoardControl(user_id);
                        if(menu.equals("1")){
                            B1.Write();
                        }else if(menu.equals("2")){
                            B1.Modify();
                        }else if(menu.equals("3")){
                            B1.Delete();
                        }else if(menu.equals("4")){
                            B1.view();
                        }else if(menu.equals("5")){
                            return;
                        }
                    }
                }
            }
            else if(menu.equals("2")){
                membership m1 = new membership();
                m1.Signup();
            }
        }
    }

    private static void clearScreen() {
        for (int i = 0; i < 30; i++)
            System.out.println("");
    }
}