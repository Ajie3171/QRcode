package ajie.com.qrcode;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    //    private Button but_start;
    private TextView tvInfo;
    private ImageView mImageView;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    //    private HistoryAdapter adapter;
    private ListView listView;
    private HisAdapter hisAdapter;
    //    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout mDrawerLayout;

    private Bitmap photo;

    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    private long exitTime = 0;
    private int n = 0;

    private List<HistoryText> hisList = new ArrayList<>();

    private AsyncHttpClient client = new AsyncHttpClient();

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        initData();

//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//            }
//        });

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new HistoryAdapter(hisList);
//        recyclerView.setAdapter(adapter);

        hisAdapter = new HisAdapter(MainActivity.this, R.layout.his_item, hisList);
        listView.setAdapter(hisAdapter);

//        pref = getSharedPreferences("data", MODE_PRIVATE);
//        if (pref != null) {
//            String result = pref.getString("result", "");
//            textView.setText(result);
//        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    capture();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView tv = (TextView) parent.getChildAt(position).findViewById(R.id.tv_his);
//                String history = (String) tv.getText();
//                tv.setVisibility(View.INVISIBLE);
//                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
//                intent.putExtra("position", position);
//                startActivity(intent);

                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;//返回false，那么长按和点击事件都会响应；所以也就有了返回true的时候，不会产生点击事件的响应
            }
        });
    }

//    //TODO 下拉刷新事件逻辑
//    private void refreshData() {
//        initData();
//        Collections.reverse(hisList);
//        //TODO 通知listview数据变了&swipeRefresh控件改变
//    }

    private void capture() {
        //try catch快捷键Ctrl+alt+t
        try {
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    capture();
                } else {
                    Toast.makeText(MainActivity.this, "拒绝权限请求", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
//                editor = getSharedPreferences("his", MODE_PRIVATE).edit();
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    final String result = bundle.getString("result");
//                    int random = new Random().nextInt(3);
//                    int image = chooseImage(random);
//                    Log.d("Result", result);
//                    editor.putString("histext", result);
//                    editor.putInt("hisimage", random);
//                    editor.commit();
//                    HistoryText hisresult = new HistoryText(result, image);
//                    hisList.add(0, hisresult);
//                    hisAdapter.notifyDataSetChanged();
//                    Log.d("adapter", "adapter Refresh");
                    String url = "http://192.168.42.88:8080/ServletTest/CompareServlet?data=" + result;
                    Log.d("capture", url);
                    client.get(url, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    try {
                                        if ("301".equals(response.getString("resCode"))) {
//                                            int image = chooseImage(Integer.parseInt(response.getString("resMsg")));
                                            int image = chooseImage(response.getInt("resMsg"));
                                            String nowTime = getNowTime();
                                            HistoryText hisresult = new HistoryText(nowTime, image);
                                            hisList.add(0, hisresult);
                                            hisAdapter.notifyDataSetChanged();
                                            dbHelper = new DBHelper(MainActivity.this, "History.db", null, 1);
                                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                                            ContentValues values = new ContentValues();
                                            values.put("QRData", result);
                                            values.put("QRImage", response.getInt("resMsg"));
                                            values.put("QRHistory", nowTime);
                                            db.insert("History", null, values);
                                            tvInfo.setVisibility(View.GONE);
                                            //TODO 关闭数据库
                                        } else if ("302".equals(response.getString("resCode"))) {
                                            Log.d("查询失败", "return errorcode 302");
                                            AlertDialog.Builder bulider = new AlertDialog.Builder(MainActivity.this);
                                            bulider.setTitle("ERROR");
                                            bulider.setMessage("解锁失败，是否重新扫码?");
                                            bulider.setPositiveButton("是", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    capture();
                                                }
                                            });
                                            bulider.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                            bulider.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    super.onFailure(statusCode, headers, responseString, throwable);
                                    Toast.makeText(MainActivity.this, "网络请求出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                    );
//                    Uri uri = data.getData();
//                    if (uri != null) {
//                        photo = BitmapFactory.decodeFile(uri.getPath());
//                        setImage(photo);
//                    } else {
//                        Toast.makeText(MainActivity.this, "图片解析失败", Toast.LENGTH_SHORT).show();
//                    }

                }
        }
//        if (requestCode==1)
//        {
//            Bundle bundle = data.getExtras();
//            String result = bundle.getString("result");
//            textView.setText(result);
//        }
    }

    private String getNowTime() {
        Date now = new Date();
        java.text.DateFormat day = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT);
        return day.format(now);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                dbHelper = new DBHelper(MainActivity.this, "History.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("History", null, null);
                hisList.clear();
                hisAdapter.notifyDataSetChanged();
                tvInfo.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return true;
    }

//    private void clearText() {
//        if (!TextUtils.isEmpty(textView.getText())) {
//            if (pref != null) {
//                editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                editor.clear().commit();
//            }
//            textView.setText("");
//            Toast.makeText(MainActivity.this, "文本内容已清除", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MainActivity.this, "文本内容为空，请不要重复点击", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("扫码");
        setSupportActionBar(toolbar);

        // 这两句显示左边的三条杠，如果要变为白色在toolbar的布局文件里添加这两句：
        // android:popupTheme="@style/ThemeOverlay.AppCompat.Light"
        // app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                fab.show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                fab.hide();
            }
        };
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
//        but_start = (Button) findViewById(R.id.but_start);
//        textView = (TextView) findViewById(R.id.textView);
//        mImageView = (ImageView) findViewById(R.id.imageView);
//        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvInfo.setVisibility(View.GONE);
//        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        listView = (ListView) findViewById(R.id.lv_his);
    }

    private void initData() {
        dbHelper = new DBHelper(MainActivity.this, "History.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("History", null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            //TODO textview 显示记录为空
            tvInfo.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "本地记录为空", Toast.LENGTH_SHORT).show();
            cursor.close();
//            HistoryText historyText = new HistoryText("测试1", R.drawable.scooter_64);
//            hisList.add(0, historyText);
        } else {
            if (cursor.moveToFirst()) {
                do {
                    int choose = cursor.getInt(cursor.getColumnIndex("QRImage"));
                    int image = chooseImage(choose);
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String history = cursor.getString(cursor.getColumnIndex("QRHistory"));
                    Log.d("本地存储", "" + id + history);
                    HistoryText historyText = new HistoryText(history, image);
                    hisList.add(id - 1, historyText);
                } while (cursor.moveToNext());
            }
            cursor.close();
            Collections.reverse(hisList);
//        pref = getSharedPreferences("his", MODE_PRIVATE);
//        if (pref != null) {
//
//            String data = pref.getString("histext", "");
//            int choose = pref.getInt("hisimage", 1);
//            int image;
//            image = chooseImage(choose);
//            HistoryText historyText = new HistoryText(data, image);
//            hisList.add(historyText);
////            HistoryText back = new HistoryText("测试" + i, R.drawable.bicycle_64);
////            hisList.add(back);
////            HistoryText camera = new HistoryText("测试" + i, R.drawable.motorcycle_64);
////            hisList.add(camera);
////            HistoryText delete = new HistoryText("测试" + i, R.drawable.scooter_64);
////            hisList.add(delete);
//
//        } else {
//            Toast.makeText(MainActivity.this, "没有骑行历史", Toast.LENGTH_SHORT).show();
//        }
        }
    }

    private int chooseImage(int image) {

        switch (image) {
            case 1:
                image = R.drawable.bicycle_64;
                return image;

            case 2:
                image = R.drawable.motorcycle_64;
                return image;
            default:
                image = R.drawable.scooter_64;
                return image;
        }
    }

//    private void setImage(Bitmap photo) {
//        mImageView.setImageBitmap(photo);
//    }
//
//    private void setText(String result) {
//        textView.setText(textView.getText().toString() + " " + result);
////                    textView.append("//n"+result);
////                    String text= (String) textView.getText();
//        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//        editor.putString("result", textView.getText().toString());
//        editor.apply();
//    }
}
