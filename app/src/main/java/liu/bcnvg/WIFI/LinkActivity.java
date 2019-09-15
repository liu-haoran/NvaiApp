package liu.bcnvg.WIFI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import liu.bcnvg.MainActivity;
import liu.bcnvg.R;

/**
 * Created by Administrator on 2019/8/12.
 */

public class LinkActivity extends Activity {

    Intent intent;
    EditText IP, PORT;
    TextView showMessage=null;
    Button link;
    private String mIp = "192.168.4.1";
    private int mPort = 8888;
    private SendThread sendthread;
    String receive_Msg;
    String l;
    String SendSring;
    Toast toast;
    static PrintWriter mPrintWriterClient = null;
    static BufferedReader mBufferedReaderClient	= null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port);
        initview();
        set_listener();
    }

    private  void show_tip(String string){
        toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }


    private  void initview(){
        IP = findViewById(R.id.IP_Adrress);
        PORT = findViewById(R.id.IP_Port);
        showMessage = findViewById(R.id.text_show);
        link = findViewById(R.id.link);
    }

    private  void set_listener(){
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(IP.getText().toString().trim()) || "".equals(PORT.getText().toString().trim())){
                    showtip("请输入正确的格式！！！");
                }else {
                    mIp = IP.getText().toString();
                    mPort = Integer.parseInt(PORT.getText().toString());
                    sendthread = new SendThread(mIp, mPort, mHandler);
                    showtip("IP:" + mIp + "   PORT:" + mPort);
                    Thread1();
                }
            }
        });
    }

    private void showtip(String str){
        toast.setText(str);
        toast.show();
    }
    private class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    private void Thread1() {
        new Thread(sendthread).start();//创建一个新线程
    }

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 0x00) {

                Log.i("mr_收到的数据： ", msg.obj.toString());
                receive_Msg = msg.obj.toString();
                l = receive_Msg;
                //Receive.setText(l);
            }
        }
    };
}
