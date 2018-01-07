package ajie.com.qrcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvDetail = (TextView) findViewById(R.id.tv_detail);
        ImageView ivDetail = (ImageView) findViewById(R.id.iv_detail);
        AnimationDrawable drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation);
        ivDetail.setBackgroundDrawable(drawable);
        drawable.start();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        Log.d("showActivity", "" + position);
        DBHelper dbHelper = new DBHelper(ScrollingActivity.this, "History.db", null, 1);
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
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                finish();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
}
