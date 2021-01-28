import java.sql.*;

public class OpenDB {
    java.sql.Connection conn;

    PreparedStatement pstmt = null;
    PreparedStatement pstmt_check = null;
    String sql = "INSERT INTO member_info(id, password, name, age)" + "VALUES(?,?,?,?)";
    String sql2 = "SELECT * FROM member_info WHERE id = ?";
    java.sql.ResultSet rs;
    PreparedStatement ps = null;

    OpenDB(){
        connect();
    }

    void connect() {
        String dbinfo = "jdbc:mysql://localhost:3307/Member";
        String dbid = "root";
        String dbps = "135156";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = java.sql.DriverManager.getConnection(dbinfo, dbid, dbps);
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt_check = this.conn.prepareStatement(sql2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void update(String id, String password, String name, int age){
        try{
            pstmt.setString(1,id);
            pstmt.setString(2,password);
            pstmt.setString(3,name);
            pstmt.setInt(4,age);
            this.pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void select(String dbSelect){
        try{
            ps = conn.prepareStatement(dbSelect, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            this.rs = ps.executeQuery();
            this.rs.beforeFirst();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void check(String user_id, String user_password){
        try {
            pstmt_check.setString(1, user_id);
            this.rs = pstmt_check.executeQuery();
            if(this.rs.next()){
                String tmp_pass = rs.getString("password");

                if(tmp_pass.equals(user_password)){
                    System.out.println("로그인이 성공하였습니다 !!!");
                }else{
                    System.out.println("ID 또는 비밀번호가 틀렸습니다 !!!");
                }
            }else{
                System.out.println("ID 또는 비밀번호가 틀렸습니다 !!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void close(){
        try{
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
