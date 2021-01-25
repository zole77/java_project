import java.io.BufferedReader;
import java.util.Scanner;

public class BoardControl {
    public void Write(){
        String title;
        String mt;
        System.out.println("=== 게시판 글쓰기 ===");
        System.out.printf("게시판 제목: ");
        Scanner sc2 = new Scanner(System.in);
        title = sc2.nextLine();
        System.out.printf("");
        mt = sc2.nextLine();
    }
}
