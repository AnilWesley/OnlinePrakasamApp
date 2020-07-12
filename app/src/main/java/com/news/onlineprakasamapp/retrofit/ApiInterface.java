package com.news.onlineprakasamapp.retrofit;


import com.google.gson.JsonObject;
import com.news.onlineprakasamapp.modals.FullListDetails;
import com.news.onlineprakasamapp.modals.PrakasamPolitics;
import com.news.onlineprakasamapp.modals.SingleDetails;
import com.news.onlineprakasamapp.modals.SingleNewsDetail;
import com.news.onlineprakasamapp.modals.StateandNational;
import com.news.onlineprakasamapp.modals.UpdateToken;
import com.news.onlineprakasamapp.modals.ViewsCount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiInterface {


    @Headers("Content-Type: application/json")
    @GET("api/investigation_crime")
    Call<FullListDetails> processInvestigationCrime();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleInvestigationCrime(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("api/top_news_stories")
    Call<FullListDetails> processTopNewStories();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleTopStories(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("api/editorial")
    Call<FullListDetails> processEditorial();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleEditorial(@Url String url);


    @Headers("Content-Type: application/json")
    @GET("api/state_national_list")
    Call<StateandNational> processStateandNational();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleDetails> processSingleStateandNational(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("api/prakasam_politics_list")
    Call<PrakasamPolitics> processPoliticsList();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleDetails> processSinglePolitics(@Url String url);


    @Headers("Content-Type: application/json")
    @GET("api/latest_news")
    Call<FullListDetails> processLatestNews();


    @Headers("Content-Type: application/json")
    @POST("app/ws/storeUserTokenandDeviceIds")
    Call<UpdateToken> processToken(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("app/ws/addUserViewCountLatestNews")
    Call<ViewsCount> processLatestNewsViews(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("app/ws/addUserViewCountStateandNational")
    Call<ViewsCount> processLatestStateViews(@Body JsonObject body);


}
