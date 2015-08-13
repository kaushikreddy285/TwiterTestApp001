package com.pklabs.twitertestapp001;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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
        if (null != intent) {
            TwitterAccess twitterAccess = (TwitterAccess) intent.getParcelableExtra(Constants.TwitterAccessIntentKey);
            new GetTimeLineAsyncTask().execute(twitterAccess);
        } else {
            String[] values = new String[]{
                    "Empty list..."
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
            ProgressDialog progressDialog = new ProgressDialog(TimelineActivity.this);
            progressDialog.setMessage("Getting twitter timeline...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<twitter4j.Status> doInBackground(TwitterAccess... twitterAccesses) {

            try {
                TwitterAccess twitterAccess = twitterAccesses[0];

                //consumer key
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(twitterAccess.getConsumerKey());
                builder.setOAuthConsumerSecret(twitterAccess.getConsumerSecret());

                //access token
                AccessToken accessToken = new AccessToken(twitterAccess.getoAuthTokenSecret(), twitterAccess.getoAuthTokenKey());
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                //Get timeline
                User user = twitter.verifyCredentials();
                List<twitter4j.Status> statuses = twitter.getHomeTimeline();
                return statuses;

            } catch (TwitterException e) {
                Log.d("timeline error", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<twitter4j.Status> statuses) {
            ArrayList<String> arrayList = new ArrayList<String>();

            for (twitter4j.Status currentStatus: statuses)
            {
                arrayList.add(currentStatus.toString());
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
            mProgressDialog.dismiss();
        }
    }
}


