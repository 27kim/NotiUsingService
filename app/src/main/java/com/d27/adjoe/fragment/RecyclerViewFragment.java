package com.d27.adjoe.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.d27.adjoe.AlbumsData;
import com.d27.adjoe.R;
import com.d27.adjoe.RecyclerViewAdapter;
import com.d27.adjoe.RequestHttpUrlConnection;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressbar;


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mProgressbar = (ProgressBar) view.findViewById(R.id.progress);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        NetworkTask networkTask = new NetworkTask("https://jsonplaceholder.typicode.com/albums");
        networkTask.execute();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        String url;

        NetworkTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = null;
            RequestHttpUrlConnection requestHttpURLConnection = new RequestHttpUrlConnection();

            try {
                result = requestHttpURLConnection.sendGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressbar.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(result)) {
                AlbumsData[] array = new Gson().fromJson(result, AlbumsData[].class);
                List<AlbumsData> list = Arrays.asList(array);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), list);
                mRecyclerView.setAdapter(adapter);
            } else {

            }
        }
    }
}
