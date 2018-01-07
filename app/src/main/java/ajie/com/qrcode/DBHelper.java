package ajie.com.qrcode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 阿杰 on 2017/11/19.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREAT_HISTORY = "create table History("
            + "id integer primary key autoincrement, "
            + "QRData text,"
            + "QRImage integer,"
            + "QRHistory text)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
