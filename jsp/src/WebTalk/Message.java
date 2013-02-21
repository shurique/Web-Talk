package WebTalk;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Message {
    private int idFrom;
    private int idTo;
    private String subject;
    private String text;
    private boolean type;		//false - исходящее, true - входящее
    private boolean read;		//false - не прочитанное, true - прочитанное
    private String date;

    Message() {
        idFrom = -1;
        idTo = -1;
        subject = new String();
        text = new String();
        type = false;
        read = false;
    }

    Message(int from, int to, String newSubject, String newText, boolean newType) {
        this.setMessage(from, to, newSubject, newText, newType, false);
    }

    public void createNewMessage(int from, int to, String newSubject, String newText) {
        this.setMessage(from, to, newSubject, newText, false, false);
    }

    public static String createTableSQL() {
        return  "drop table if exists message; " +
                "create table message(" +
                "id_msg int auto_increment, " +
                "idfrom int,  " +
                "idto int, " +
                "subject varchar(30), " +
                "text clob, " +
                "date varchar(40), " +
                "type boolean, " +
                "read boolean, " +
                "foreign key (idfrom) REFERENCES user (id_usr), " +
                "foreign key (idto) REFERENCES user (id_usr), " +
                ");";
    }

    public String getSQL() {
        return "insert into message(" +
                "idfrom, idto, subject, text, date, type, read) values(" +
                String.valueOf(idFrom) + ", " + String.valueOf(idTo) + ", '" +
                subject + "', '" + text + "', '" + date + "', " +
                String.valueOf(type) + ", " + String.valueOf(read) +
                ");";
    }

    /*public static Message[] getMessages(Database db) throws SQLException, ClassNotFoundException{
        db.createConnection();

    } */

    public void setMessage(int from, int to, String newSubject, String newText, boolean newType, boolean isRead) {
        idFrom = from;
        idTo = to;
        subject = new String(newSubject);
        text = new String(newText);

        //Редактирование даты
        date = (new SimpleDateFormat()).format(new Date());

        type = newType;
        read = isRead;
    }

    public void ReadMessage() {read = true;}
    public void UnreadMessage() {read = false;}

    public int getSender() {return idFrom;}
    public int getTo() {return idTo;}
    public String getSubject() {return subject;}
    public String getText() {return text;}
    public boolean isIncoming() {return type;}
    public boolean isRead() {return read;}


}
