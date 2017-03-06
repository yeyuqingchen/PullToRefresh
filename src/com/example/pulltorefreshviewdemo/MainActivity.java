package com.example.pulltorefreshviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pulltorefreshviewdemo.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltorefreshviewdemo.PullToRefreshView.OnHeaderRefreshListener;

public class MainActivity extends Activity {

    public static final int REFRESH_DELAY = 1000;

    private PullToRefreshView mPullToRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Map<String, Integer> map;
        List<Map<String, Integer>> sampleList = new ArrayList<Map<String, Integer>>();

        int[] icons = {
                R.drawable.gif_1,
                R.drawable.gif_2,
                R.drawable.gif_3,
                R.drawable.gif_4,
                R.drawable.gif_5,
                R.drawable.gif_6,
                R.drawable.gif_7};

        int[] colors = {
                R.color.saffron,
                R.color.eggplant,
                R.color.sienna,
                R.color.saffron,
                R.color.eggplant,
                R.color.sienna,
                R.color.saffron};

        for (int i = 0; i < icons.length; i++) {
            map = new HashMap<String, Integer>();
            map.put(SampleAdapter.KEY_ICON, icons[i]);
            map.put(SampleAdapter.KEY_COLOR, colors[i]);
            sampleList.add(map);
        }

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new SampleAdapter(this, R.layout.list_item, sampleList));

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				// TODO Auto-generated method stub
				view.postDelayed(new Runnable() {
					public void run() {
						
						mPullToRefreshView.onHeaderRefreshComplete();
					}
				}, 1000);
			}
		});
        mPullToRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
			
			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				// TODO Auto-generated method stub
				view.postDelayed(new Runnable() {
					public void run() {
						
						mPullToRefreshView.onFooterRefreshComplete();
					}
				}, 1000);
			}
		});
        mPullToRefreshView.setFooter(true);
    }

    class SampleAdapter extends ArrayAdapter<Map<String, Integer>> {

        public static final String KEY_ICON = "gif";
        public static final String KEY_COLOR = "color";

        private final LayoutInflater mInflater;
        private final List<Map<String, Integer>> mData;

        public SampleAdapter(Context context, int layoutResourceId, List<Map<String, Integer>> data) {
            super(context, layoutResourceId, data);
            mData = data;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_item, parent, false);
                viewHolder.imageViewIcon = (ImageView) convertView.findViewById(R.id.image_view_icon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.imageViewIcon.setImageResource(mData.get(position).get(KEY_ICON));
            convertView.setBackgroundResource(mData.get(position).get(KEY_COLOR));

            return convertView;
        }

        class ViewHolder {
            ImageView imageViewIcon;
        }

    }

}

