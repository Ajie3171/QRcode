package ajie.com.qrcode;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by 阿杰 on 2017/10/18.
 */

public class DrawerFragment extends Fragment {
    private EditText account;
    private EditText password;
    private ImageView userpic;
    private Button userlogin;
    private TextView userinfo;
    private JSONObject json;
    private String jsontext;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_layout, container, false);
        account = (EditText) view.findViewById(R.id.ed_account);
        password = (EditText) view.findViewById(R.id.ed_password);
        userpic = (ImageView) view.findViewById(R.id.iv_drawer);
        userlogin = (Button) view.findViewById(R.id.bt_login);
        userinfo = (TextView) view.findViewById(R.id.tv_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(account.getText()) && TextUtils.isEmpty(password.getText()))) {
                    final String useraccount = account.getText().toString();
                    String userpassword = password.getText().toString();
                    //TODO 连接网络用md5加密的密码上传判断登陆成功
                    //http://localhost:8080/ServletTest/RegisterServlet?account=17749711231&password=123456
                    String url = "http://192.168.42.88:8080/ServletTest/RegisterServlet?account=17749711231&password=123456";
                   /*HttpUtil.sendOkHttpRequest(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
//                            Toast.makeText(getActivity(), "网络链接出错", Toast.LENGTH_SHORT).show();
                            Log.d("http", "error");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().toString();
                            Log.d("response", responseText);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    account.setVisibility(View.INVISIBLE);
                                    password.setVisibility(View.INVISIBLE);
                                    userlogin.setVisibility(View.INVISIBLE);
                                    userinfo.setText(useraccount + "已登陆");
//                                    userinfo.setText(userinfo.getText().toString() + "/n" + responseText);

                                    if (responseText != null && responseText.startsWith("/ufeff")) {
                                        jsontext = responseText.substring(1);
                                    }
                                    try {
                                        json = new JSONObject(jsontext);
                                        userinfo.setText(userinfo.getText().toString() + "/n" + json.getString("resMsg"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("response", "json解析出错");
                                    }
                                }
                            });

                        }
                    });*/
//                    client.get(url, new TextHttpResponseHandler() {
//                        @Override
//                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                            Log.d("asyncHttp", "error");
//                        }
//
//                        @Override
//                        public void onSuccess(int i, Header[] headers, String s) {
//                            Log.d("asyncHttp", s);
//                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                    client.get(url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            super.onSuccess(statusCode, headers, response);
                            try {
                                account.setVisibility(View.INVISIBLE);
                                password.setVisibility(View.INVISIBLE);
                                userlogin.setVisibility(View.INVISIBLE);
                                userinfo.setVisibility(View.VISIBLE);
                                userinfo.setText("用户" + useraccount + "已登陆");
                                Log.d("JSONparser", response.getString("resMsg"));
                                Toast.makeText(getActivity(), response.getString("resMsg"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("JSONparser", "error");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.d("JSON", "internet fail");
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "账户或密码为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                userlogin.setVisibility(View.VISIBLE);
                userinfo.setVisibility(View.INVISIBLE);
            }
        });
    }
}
