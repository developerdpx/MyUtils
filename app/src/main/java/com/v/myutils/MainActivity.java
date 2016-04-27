package com.v.myutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.view.OnChangedListener;
import com.view.SlipButton;

import org.w3c.dom.Document;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @ViewInject(R.id.mybuo)
    private Button mybuo;
    @ViewInject(R.id.img)
    private ImageView img;
    @ViewInject(R.id.gridview)
    private GridView gridview;
    private List<String> list;
    private SlipButton sb;
    private SlipButton sb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        sb= (SlipButton) findViewById(R.id.sb);
        sb.setNowChoose(true);
        sb1= (SlipButton) findViewById(R.id.sb1);
        sb.setOnChangeListener(new OnChangedListener() {
            @Override
            public void OnChanged(boolean CheckState) {
                String s;
                if(sb1.isNowChoose())
                {
                    s="Button2打开了...";
                }else{
                    s="Button2关闭了...";
                }
                if (CheckState)
                    Toast.makeText(MainActivity.this, "Button1打开了..."+s,
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Button1关闭了..."+s,
                            Toast.LENGTH_SHORT).show();
            }
        });
        //从数据库中获取数据
        getDbData();
        list = new ArrayList<String>();
        for (int i = 0; i < 21; i++) {
            list.add("第几个的=" + i);
        }

        Myadpter myadpter = new Myadpter(list);
        gridview.setAdapter(myadpter);
        RequestParams params = new RequestParams("http://blog.csdn.net/mobile/experts.html");
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Document doc=Jsoup.parse(result);
//                Document doc = Jsoup.parse(result);
//                Element div = doc.select("div.list_3").get(0);
//                Elements imgs = div.getElementsByTag("img");
//                for (int i = 0; i < imgs.size(); i++) {
//                    Element img = imgs.get(i);
//                    Log.i("liyuanjinglyj",img.attr("alt"));
//                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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


    }

    private void getDbData() {
        //从数据库中获取数据
        DbManager dbManager = x.getDb(((MyApplication) getApplicationContext()).getDaoConfig());

        try {
            List<Myperson> mypersons = dbManager.selector(Myperson.class).findAll();
            for (int i = 0; i < mypersons.size(); i++) {
                Log.e("mydp", "Myperson" + i + ".name=" + mypersons.get(i).getName());
                Log.e("mydp", "Myperson" + i + ".name=" + mypersons.get(i).getAge());
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

    }


    @Event(value = R.id.mybuo, type = View.OnClickListener.class)
    private void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.mybuo:
                getDate();
                Toast.makeText(getApplication(), "你好我是Xutil IOC功能", Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void getDate() {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))//图片大小
                .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片
                .build();
        x.image().bind(img, "http://pic.baike.soso.com/p/20090711/20090711101754-314944703.jpg", imageOptions);
    }

    class Myadpter extends BaseAdapter {
        List<String> list;

        public Myadpter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hadel hadel;
            if (convertView == null) {
                hadel = new Hadel();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_gridview, null);
                hadel.txt = (TextView) convertView.findViewById(R.id.txt);
                convertView.setTag(hadel);
            } else {
                hadel = (Hadel) convertView.getTag();
            }
            hadel.txt.setText(list.get(position).toString());
            return convertView;
        }

        class Hadel {
            TextView txt;
        }

    }


}
