public class Check {

    String user_id;
    String user_password;

    public Check(String user_id, String user_password) {
        this.user_id = user_id;
        this.user_password = user_password;
    }

    public boolean Login_check() {
        OpenDB C1 = new OpenDB();
        if(C1.check(this.user_id, this.user_password)){
            return true;
        }else{
            return false;
        }
    }

}