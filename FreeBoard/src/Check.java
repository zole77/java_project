public class Check {

    String user_id;
    String user_password;

    public Check(String user_id, String user_password) {
        this.user_id = user_id;
        this.user_password = user_password;

        OpenDB C1 = new OpenDB();

        C1.check(this.user_id, this.user_password);
        // SELECT id
        // FROM member_info
        // WHERE user_id;

        C1.close();
    }

}
