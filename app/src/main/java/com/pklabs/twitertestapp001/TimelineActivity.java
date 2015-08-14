package com.pklabs.twitertestapp001;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Kaushik on 8/13/15.
 */
public class TimelineActivity extends Activity {

    private ProgressDialog mProgressDialog;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        mListView = (ListView) findViewById(R.id.timelinelist);

        Intent intent = this.getIntent();
        //intent = null;
        if (null != intent) {
            TwitterAccess twitterAccess = (TwitterAccess) intent.getSerializableExtra(Constants.TwitterAccessIntentKey);
            new GetTimeLineAsyncTask().execute(twitterAccess);
        } else {
            String[] values = new String[]{
                    "item-1",
                    "item-2",
                    "item-3",
                    "item-4",
                    "item-5",
                    "item-6",
                    "item-7",
                    "item-8",
                    "item-9",
                    "item-10",
                    "item-11",
                    "item-12",
                    "item-13",
                    "item-14",
                    "item-15",
                    "item-16",
                    "item-17",
                    "item-18",
                    "item-19",
            };
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    //context
                    this,
                    //layout for the row
                    android.R.layout.simple_list_item_1,
                    //id of the textview
                    android.R.id.text1,
                    //values
                    values
            );
            mListView.setAdapter(arrayAdapter);
        }
    }

    class GetTimeLineAsyncTask extends AsyncTask<TwitterAccess, Void, List<Status>> {

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(TimelineActivity.this);
            mProgressDialog.setMessage("Getting twitter timeline...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected List<twitter4j.Status> doInBackground(TwitterAccess... twitterAccesses) {

            try {
                TwitterAccess twitterAccess = twitterAccesses[0];

                //consumer key
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setDebugEnabled(true)
                        .setOAuthConsumerKey(twitterAccess.getConsumerKey())
                        .setOAuthConsumerSecret(twitterAccess.getConsumerSecret())
                        .setOAuthAccessToken(twitterAccess.getoAuthTokenKey())
                        .setOAuthAccessTokenSecret(twitterAccess.getoAuthTokenSecret());
                Twitter twitter = new TwitterFactory(builder.build()).getInstance();

                //Get timeline
                //User user = twitter.verifyCredentials();
                Paging paging = new Paging();
                paging.setCount(200);
                return twitter.getHomeTimeline(paging);
            } catch (TwitterException e) {
                Log.d("timeline error", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<twitter4j.Status> statuses) {
            if (statuses != null) {
                ArrayList<String> arrayList = new ArrayList<String>();

                int i=1;
                for (twitter4j.Status currentStatus : statuses) {
                    arrayList.add(i + ": " +currentStatus.getText());
                    i++;
                }

                String[] values = arrayList.toArray(new String[arrayList.size()]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        //context
                        TimelineActivity.this,
                        //layout for the row
                        android.R.layout.simple_list_item_1,
                        //id of the textview
                        android.R.id.text1,
                        //values
                        values
                );
                mListView.setAdapter(arrayAdapter);
            } else {
                Toast.makeText(TimelineActivity.this, "statuses is NULL.", Toast.LENGTH_LONG).show();
            }
            mProgressDialog.dismiss();
        }
    }
}


