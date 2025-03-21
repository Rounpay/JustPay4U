package com.solution.app.justpay4u.Shopping.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.app.justpay4u.Api.Shopping.Response.SearchKeywordResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ShoppingEndPointInterface;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.SearchListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


public class SearchResultShoppingActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    ImageView voiceBtn;
    EditText searchEt;
    ArrayList<SearchKeywordResponse> mSearchLists = new ArrayList<>();
    ArrayList<SearchKeywordResponse> mRecentSearchLists = new ArrayList<>();
    SearchListShoppingAdapter mSearchListAdapter;

    CustomLoader loader;
    RecyclerView mRecyclerView;
    boolean isNotFirstTime;
    private View noDataView;
    private View noNetworkView;
    private String searchString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_search_result);
            Toolbar mToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.setCancelable(false);

            searchEt = findViewById(R.id.search);
            voiceBtn = findViewById(R.id.voiceBtn);
            View retryBtn = findViewById(R.id.retryBtn);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mSearchListAdapter = new SearchListShoppingAdapter(this, mSearchLists);
            mRecyclerView.setAdapter(mSearchListAdapter);

            voiceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promptSpeechInput();
                }
            });
            mSearchListAdapter.setClickListener(new SearchListShoppingAdapter.ItemClickListener() {
                @Override
                public void viewClick(int position, View view) {

                    setRecentSearch(position);

                    startActivity(new Intent(SearchResultShoppingActivity.this, SearchProductShoppingActivity.class)
                            .putExtra("SCREEN_DATA", mSearchLists.get(position))
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }
            });
            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSearchApi(searchString);
                }
            });


            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    searchString = s.toString();

                    if (searchString.length() > 0) {

                        getSearchApi(s.toString());
                    } else {
                        setRecentData();
                    }

                }
            });
            setRecentData();

        });

    }


    void setRecentSearch(int position) {

        boolean isExit = false;
        for (int i = 0; i < mRecentSearchLists.size(); i++) {
            if (mRecentSearchLists.get(i).getKeywordId() == mSearchLists.get(position).getKeywordId()
                    && mRecentSearchLists.get(i).getKeyword().equalsIgnoreCase(mSearchLists.get(position).getKeyword())
                    && mRecentSearchLists.get(i).getSubcategoryName().equalsIgnoreCase(mSearchLists.get(position).getSubcategoryName())) {
                isExit = true;
                break;
            }
        }
        if (!isExit) {
            mRecentSearchLists.add(0, mSearchLists.get(position));
            if (mRecentSearchLists.size() > 10) {
                mRecentSearchLists.remove(mRecentSearchLists.size() - 1);

            }
            ApiShoppingUtilMethods.INSTANCE.setRecentSearch(this, new Gson().toJson(mRecentSearchLists));

        }
    }



    void setRecentData() {
        mRecentSearchLists = new Gson().fromJson(ApiShoppingUtilMethods.INSTANCE.getRecentSearch(this), new TypeToken<List<SearchKeywordResponse>>() {
        }.getType());
        if (mRecentSearchLists != null && mRecentSearchLists.size() > 0) {
            mSearchLists.clear();
            mSearchLists.addAll(mRecentSearchLists);

        } else {
            mRecentSearchLists = new ArrayList<>();
        }

        if (mSearchLists.size() == 0) {
            hideShowErrorView(View.VISIBLE, View.GONE);
        } else {
            hideShowErrorView(View.GONE, View.GONE);
        }
        mSearchListAdapter.notifyDataSetChanged();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchEt.setText(result.get(0));
                    searchEt.setSelection(searchEt.getText().length());
                }
                break;
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go activity_home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void getSearchApi(String str) {

        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {

                ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
                Call<ArrayList<SearchKeywordResponse>> call = git.getKeywordList(str,
                        ApiShoppingUtilMethods.INSTANCE.mWebsiteID + "");
                call.enqueue(new Callback<ArrayList<SearchKeywordResponse>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SearchKeywordResponse>> call, final retrofit2.Response<ArrayList<SearchKeywordResponse>> response) {

                        if (response != null) {
                            try {

                                ArrayList<SearchKeywordResponse> data = response.body();
                                if (data != null) {
                                    if (searchString == null || searchString.isEmpty()) {
                                        mSearchLists.clear();
                                        mSearchLists.addAll(mRecentSearchLists);
                                        mSearchListAdapter.notifyDataSetChanged();
                                    } else {
                                        if (data.size() > 0) {
                                            mSearchLists.clear();
                                            mSearchLists.addAll(data);
                                            mSearchListAdapter.notifyDataSetChanged();
                                        } else {
                                            mSearchLists.clear();
                                            mSearchListAdapter.notifyDataSetChanged();
                                        }
                                    }


                                } else {
                                    mSearchLists.clear();
                                    mSearchListAdapter.notifyDataSetChanged();
                                }


                                if (mSearchLists.size() == 0) {
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                } else {
                                    hideShowErrorView(View.GONE, View.GONE);
                                }
                            } catch (Exception ex) {
                                if (mSearchLists.size() == 0) {
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                } else {
                                    hideShowErrorView(View.GONE, View.GONE);
                                }

                            }
                        } else {
                            if (mSearchLists.size() == 0) {
                                hideShowErrorView(View.VISIBLE, View.GONE);
                            } else {
                                hideShowErrorView(View.GONE, View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SearchKeywordResponse>> call, Throwable t) {


                        try {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {
                                    if (mSearchLists.size() == 0) {
                                        hideShowErrorView(View.GONE, View.VISIBLE);
                                    }

                                } else {
                                    if (mSearchLists.size() == 0) {
                                        hideShowErrorView(View.VISIBLE, View.GONE);
                                    } else {
                                        hideShowErrorView(View.GONE, View.GONE);
                                    }
                                }

                            } else {
                                if (mSearchLists.size() == 0) {
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                } else {
                                    hideShowErrorView(View.GONE, View.GONE);
                                }
                            }
                        } catch (IllegalStateException ise) {
                            if (mSearchLists.size() == 0) {
                                hideShowErrorView(View.VISIBLE, View.GONE);
                            } else {
                                hideShowErrorView(View.GONE, View.GONE);
                            }
                        }
                    }
                });
            } catch (Exception ex) {
                if (mSearchLists.size() == 0) {
                    hideShowErrorView(View.VISIBLE, View.GONE);
                } else {
                    hideShowErrorView(View.GONE, View.GONE);
                }
            }
        } else {
            if (mSearchLists.size() == 0) {
                hideShowErrorView(View.VISIBLE, View.GONE);
            } else {
                hideShowErrorView(View.GONE, View.GONE);
            }
        }
    }

    void hideShowErrorView(int noDataViewVisibility, int noNetworkVisibility) {

        noDataView.setVisibility(noDataViewVisibility);
        noNetworkView.setVisibility(noNetworkVisibility);

    }

}
