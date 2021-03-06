package com.shmily.tjz.swap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;
import com.shmily.tjz.swap.Utils.Xutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import github.ishaan.buttonprogressbar.ButtonProgressBar;

public class LoginActivity extends AppCompatActivity {
    private EditText upass,uname;
    private ButtonProgressBar button;
    TextView text;
    String name,pass;
    CircleImageView image;
    Xutils xutils=Xutils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uname= (EditText) findViewById(R.id.editText1);
        upass= (EditText) findViewById(R.id.editText2);
        button= (ButtonProgressBar) findViewById(R.id.loginbutton);
        text= (TextView) findViewById(R.id.registerbutton);
        image= (CircleImageView) findViewById(R.id.imageView5);
        upass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                    final String focus=uname.getText().toString().trim().replace(" ","");
                    if (!TextUtils.isEmpty(focus)){

                        String sql="select username from login where username='"+focus+"'"+" or phone='"+focus+"'";
                        String url="http://www.shmilyz.com/ForAndroidHttp/select.action";
                        Map<String, String> maps=new HashMap<String, String>();
                        maps.put("uname",sql);
                        xutils.post(url, maps, new Xutils.XCallBack() {
                            @Override
                            public void onResponse(String result) {

                                try {

                                    JSONObject json = new JSONObject(result);
                                    String results = json.getString("result");
                                    JSONArray jsonArray=new JSONArray(results);
                                    JSONObject re=jsonArray.getJSONObject(0);
                                   String  aa=re.getString("username");
                                    String urls="http://www.shmilyz.com/headimage/"+aa+".jpg";

                                    Glide.with(LoginActivity.this)
                                            .load(urls)
                                            .error(R.mipmap.camera)
                                            .diskCacheStrategy( DiskCacheStrategy.NONE )
                                            .into(image);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });



                    }
                    else{

                    }

                    } else {
// 此处为失去焦点时的处理内容
                }
            }
        });
        init();
    }

    private void init() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                name=uname.getText().toString().trim().replace(" ","");
                pass=upass.getText().toString().trim().replace(" ","");
                RequestParams params=new RequestParams("http://120.25.96.231/ForAndroidHttp/sign.action");
                params.addBodyParameter("uname",name);
                params.addBodyParameter("upass",pass);

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)){

                    x.http().post(params, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject json = new JSONObject(result);
                                String results = json.getString("result");
                                final String username=json.getString("username");
                                if (results.equals("1")) {
                                    button.startLoader();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferences prefs=getSharedPreferences("user", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor=prefs.edit();
                                            editor.putString("username",username);
                                            editor.putBoolean("denglu",true);
                                            editor.commit();
                                            new CookieBar.Builder(LoginActivity.this)
                                                    .setTitle("欢迎您")
                                                    .setMessage(username)
                                                    .setBackgroundColor(R.color.colorPrimary)
                                                    .setAction("确认", new OnActionClickListener() {
                                                        @Override
                                                        public void onClick() {

                                                        }
                                                    })
                                                    .show();

                                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                            intent.putExtra("username",username);
                                            button.stopLoader();
                                            startActivity(intent);
                                            LoginActivity.this.finish();

                                        }
                                    }, 1400);

                                } else {
                                    Snackbar.make(getWindow().getDecorView(),"密码错误",Snackbar.LENGTH_LONG)
                                            .show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(LoginActivity.this, "请您先连接网络！", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public boolean onCache(String result) {
                            return false;
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, "请输入正确的账号或密码！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,VerifyMobActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

}
