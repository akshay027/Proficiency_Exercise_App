package com.example.proficiency_exercise_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list_item;
    private CustomAdapter customAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_item=findViewById(R.id.list_item_view);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        arrayList=new ArrayList();

        customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        list_item.setAdapter(customAdapter);
        list_item.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (list_item.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(list_item.getFirstVisiblePosition() == 0 && list_item.getChildAt(0).getTop() == 0);
                }
            }
        });
    }
}
