import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BoardControl {

    String user_id;
    String B_no;
    public BoardControl(String user_id) {
        this.user_id = user_id;
    }

    public void Write() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            OpenDB B1 = new OpenDB();
            System.out.print("제목을 입력해주세요: ");
            String title = br.readLine();
            System.out.print("내용을 입력해주세요: ");
            String line = "";
            String plaintext ="";
            while(!line.equals("EOF")){
                line = br.readLine();
                plaintext = plaintext.concat(line + "\n");
                System.out.println(plaintext);
            }
            B1.Write(title, plaintext, this.user_id);
            B1.close();;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void Delete(){
        Scanner sc3 = new Scanner(System.in);
        OpenDB B1 = new OpenDB();
        System.out.print("삭제할 B_no를 입력해주세요: ");
        B_no = sc3.nextLine();
        B1.Delete(B_no);
    }

    public void Modify(){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            OpenDB B1 = new OpenDB();
            System.out.print("수정할 게시글의 번호를 입력해주십시오: ");
            int B_no = Integer.parseInt(br.readLine());
            System.out.print("내용을 입력해주세요: ");
            String line = "";
            String plaintext ="";
            while(!line.equals("EOF")){
                line = br.readLine();
                plaintext = plaintext.concat(line + "\n");
                System.out.println(plaintext);
            }
            B1.Modify(plaintext, B_no);
            B1.close();;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
