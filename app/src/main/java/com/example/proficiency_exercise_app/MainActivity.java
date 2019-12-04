package com.example.proficiency_exercise_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView list_item;
    private CustomAdapter customAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Row> rowarrayList;
    private APIInterface apiInterface;
    private ActionBar actionBar;
    private DownloadManager downloadManager;
    private int itemPosition;
    private long reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setTitle("");

        list_item = findViewById(R.id.list_item_view);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        //initialization of apiInterface object
        apiInterface = APIClient.getClient().create(APIInterface.class);

        rowarrayList = new ArrayList();

        getRowDetails();

        //setting an setOnRefreshListener on the SwipeDownLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRowDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }
   //calling retrofit api
    public void getRowDetails() {
        Call<ExerciseDetails> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<ExerciseDetails>() {
            @Override
            public void onResponse(Call<ExerciseDetails> call, Response<ExerciseDetails> response) {


                Log.d("TAG", response.code() + "");

                ExerciseDetails resource = response.body();
                if (TextUtils.isEmpty(resource.getTitle())) {
                    actionBar.setTitle("");
                } else {
                    actionBar.setTitle(resource.getTitle());
                }
                //clear the arraylist before adding details if you wont clear it will create duplicate record
                rowarrayList.clear();
                rowarrayList.addAll(resource.getRows());

                customAdapter = new CustomAdapter(getApplicationContext(), rowarrayList);
                list_item.setAdapter(customAdapter);
                Log.d("data","----"+  rowarrayList.toString());

                //setting an SetOnItemClickListener on the listview individual items
                customAdapter.SetOnItemClickListener(new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        itemPosition=position;
                        alertBox();
                    }
                });


            }

            @Override
            public void onFailure(Call<ExerciseDetails> call, Throwable t) {
                call.cancel();
            }
        });
    }
    public void alertBox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Setting the title manually
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to download this image file ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        startDownloadPdf(rowarrayList.get(itemPosition).getImageHref(), "Image");

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void startDownloadPdf(String path, String subject) {
        if (downloadManager == null)
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(path);

// execute this when the downloader must be fired
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(rowarrayList.get(itemPosition).getImageHref());
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "Image" + subject + rowarrayList.get(itemPosition).getImageHref());
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        reference = downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(), "Started Downloading ..", Toast.LENGTH_SHORT).show();
    }
}
