package WebTalk;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


public class Message {
    private int msg_id;
    private int idFrom;
    private int idTo;
    private String subject;
    private String text;
    private String date;

    Message() {
        msg_id = -1;
        idFrom = -1;
        idTo = -1;
        subject = new String();
        text = new String();
    }

    Message(int from, int to, String newSubject, String newText) {
        this.setMessage(from, to, newSubject, newText, this.createDate());
    }

    Message(int newID, int from, int to, String newSubject, String newText, String newDate) {
        this.setMsg_id(newID);
        this.setMessage(from, to, newSubject, newText, newDate);
    }

    public static String createTableSQL() {
        return  //"drop table if exists message; " +
                "create table message(" +
                "id_msg int auto_increment, " +
                "idfrom int,  " +
                "idto int, " +
                "subject varchar(30), " +
                "text clob, " +
                "date varchar(40), " +
                "foreign key (idfrom) REFERENCES user (id_usr), " +
                "foreign key (idto) REFERENCES user (id_usr), " +
                ");";
    }

    public String getSQL() {
        return "insert into message(" +
                "idfrom, idto, subject, text, date) values(" +
                String.valueOf(idFrom) + ", " + String.valueOf(idTo) + ", '" +
                subject + "', '" + text + "', '" + date + "');";
    }

    public boolean save() {
        Database db = new Database();
        boolean result = false;

        if (!db.createConnection())
            return result;

        Connection conn = db.getConnection();
        String sql_str = "INSERT INTO message(idfrom, idto, subject, text, date) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql_str);
            ps.setInt(1, idFrom);
            ps.setInt(2, idTo);
            ps.setString(3, subject);
            ps.setString(4, text);
            ps.setString(5, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Закрытие соединения с БД
            db.closeConnection();
        }

        result = true;
        return result;
    }

    //Получение сообщения по его id
    public static Message getMessageById(int msg_id){
        Database db = new Database();
        Message newMessage = null;

        if (!db.createConnection())
            return newMessage;

        String query_txt = "SELECT * FROM MESSAGE WHERE ID_MSG = ?";
        Connection conn = db.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(query_txt);
            ps.setInt(1, msg_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                newMessage = new Message();
                newMessage.setMessage(rs.getInt("IDFROM"), rs.getInt("IDTO"), rs.getString("SUBJECT"),
                        rs.getString("TEXT"), rs.getString("DATE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return newMessage;
        } finally {
            db.closeConnection();
        }

        return newMessage;
    }

    private String createDate() {
        return (new SimpleDateFormat()).format(new Date());
    }

    public void setMessage(int from, int to, String newSubject, String newText, String newDate) {
        idFrom = from;
        idTo = to;
        subject = new String(newSubject);
        text = new String(newText);
        date = newDate;
    }


    public int getSender() {return idFrom;}
    public int getTo() {return idTo;}
    public String getSubject() {return subject;}
    public String getText() {return text;}
    public String getDate() {return date;}
    public int getMsg_id() {return msg_id;};
    public void setMsg_id(int newMsgId) {msg_id = newMsgId;}


}
