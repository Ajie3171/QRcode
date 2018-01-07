package ajie.com.qrcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        TextView tvDetail = (TextView) findViewById(R.id.tv_detail);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        Log.d("showActivity", "" + position);
        DBHelper dbHelper = new DBHelper(ShowActivity.this, "History.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("History", null, null, null, null, null, null);
        int count = cursor.getCount() - position;
        cursor.close();
        Cursor cr = db.rawQuery("select * from History where id = ?", new String[]{"" + count});
        if (cr.moveToFirst()) {
            do {
                String history = cr.getString(cursor.getColumnIndex("QRHistory"));
                tvDetail.setText(history);
            } while (cr.moveToNext());
        }
    }
}
