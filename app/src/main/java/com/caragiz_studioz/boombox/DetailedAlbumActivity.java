package com.caragiz_studioz.boombox;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caragiz_studioz.boombox.adapters.TrackListAdapter;
import com.caragiz_studioz.boombox.dataObjects.AlbumData;
import com.caragiz_studioz.boombox.dataObjects.GlobalResource;
import com.caragiz_studioz.boombox.dataObjects.SongInfo;
import com.caragiz_studioz.boombox.helper.ResizeAnimationHelper;

/**
 * Created by caragiz on 24-08-2016.
 */
public class DetailedAlbumActivity extends AppCompatActivity {

    ImageView albumArt;
    TextView albumName;
    ListView trackListView;

    static int minVisibleItem = 10000;
    static int prevFirstVisibleItem = 0;
    static boolean shrinked;

    ResizeAnimationHelper resizeAnimationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(null);
        setContentView(R.layout.detailedcardview);

        albumArt = (ImageView)findViewById(R.id.albumArt);
        albumName = (TextView) findViewById(R.id.albumName);
        trackListView = (ListView)findViewById(R.id.tracksListView);
        albumName.setText(SongInfo.albumInfo.get(GlobalResource.albumCardPosition).getAlbumName());
        Drawable albumArtDrawable = SongInfo.albumInfo.get(GlobalResource.albumCardPosition).getAlbumArt();
        if (albumArtDrawable != null) {
            albumArt.setImageDrawable(albumArtDrawable);

        }

        trackListView.setAdapter(new TrackListAdapter(this ));
        trackListView.setOnScrollListener(new ScrollListener());
    }

    class ScrollListener implements AbsListView.OnScrollListener{
        private int visibleItemCount;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            Log.i("Prev First Visible Item" , ":"+prevFirstVisibleItem);
            Log.i("Shrinked" , ":"+shrinked);
            if((prevFirstVisibleItem > 0 &&  shrinked)){
                resizeAnimationHelper = new ResizeAnimationHelper(albumArt , albumArt.getMaxHeight() , albumArt.getMaxHeight()+350);
                resizeAnimationHelper.setDuration(1500);
                albumArt.startAnimation(resizeAnimationHelper);

/*                shrinked = false;
                int width = albumArt.getWidth();
                int height = 600;
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
                albumArt.setLayoutParams(parms);
            }
            else if(minVisibleItem != visibleItemCount && prevFirstVisibleItem != 0 && !shrinked){
                resizeAnimationHelper = new ResizeAnimationHelper(albumArt , albumArt.getHeight() , albumArt.getMaxHeight()-350);
                resizeAnimationHelper.setDuration(1500);
                albumArt.startAnimation(resizeAnimationHelper);
                shrinked = true;
                Log.i("Height Change:","true");
                int width = albumArt.getWidth();
                int height = 200;
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
                albumArt.setLayoutParams(parms);
  */          }


        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            minVisibleItem = minVisibleItem > visibleItemCount ? visibleItemCount : minVisibleItem;
            prevFirstVisibleItem = firstVisibleItem;

            this.visibleItemCount= visibleItemCount;

            if((prevFirstVisibleItem > 0 )) {
                resizeAnimationHelper = new ResizeAnimationHelper(albumArt, albumArt.getHeight()-500, albumArt.getHeight());
                resizeAnimationHelper.setDuration(1500);
                albumArt.startAnimation(resizeAnimationHelper);
            }
                Log.i("First Visible Item" , ":"+firstVisibleItem);
            Log.i("Visible Item Count" , ":"+visibleItemCount);
            Log.i("Total Item Counte" , ":"+totalItemCount);
        }
    }

    public void onLayoutClick(View view) {
        Log.i("Status :", "Player Touched");
        Activity activity = this;
        Toast.makeText(activity, "Player touched", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(activity, PlayerDetailed.class);

        //View view= linearLayoutManager.findViewByPosition(GlobalResource.albumCardPosition);
        //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity , Pair.create(albumArt, "albumartplayer"), Pair.create(trackName , "trackname") , Pair.create(playButton , "playbutton"));
        //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity , GlobalResource.albumArt , "albumart");
        //activity.startActivity(intent , options.toBundle());
    }
}

