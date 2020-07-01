package com.news.onlineprakasamapp.retrofit;


import com.news.onlineprakasamapp.modals.FullListDetails;
import com.news.onlineprakasamapp.modals.PrakasamPolitics;
import com.news.onlineprakasamapp.modals.SatireCorner;
import com.news.onlineprakasamapp.modals.SingleDetails;
import com.news.onlineprakasamapp.modals.SingleNewsDetail;
import com.news.onlineprakasamapp.modals.StateandNational;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;


public interface ApiInterface {


    @Headers("Content-Type: application/json")
    @GET("fact_check")
    Call<FullListDetails> processSuccessStories();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleFactCheck(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("people_right")
    Call<FullListDetails> processPeopleRight();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSinglePeopleRight(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("investigation_crime")
    Call<FullListDetails> processInvestigationCrime();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleInvestigationCrime(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("top_news_stories")
    Call<FullListDetails> processTopNewStories();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleTopStories(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("editorial")
    Call<FullListDetails> processEditorial();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleEditorial(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("satire_corner_list")
    Call<SatireCorner> processSatireCornerList();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleDetails> processSingleSatireCorner(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("state_national_list")
    Call<StateandNational> processStateandNational();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleDetails> processSingleStateandNational(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("prakasam_politics_list")
    Call<PrakasamPolitics> processPoliticsList();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleDetails> processSinglePolitics(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("about_officials")
    Call<FullListDetails> processAboutOfficials();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleOfficial(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("latest_news")
    Call<FullListDetails> processLatestNews();


    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleNews(@Url String url);


    @Headers("Content-Type: application/json")
    @GET("about_district")
    Call<FullListDetails> processAboutDistrict();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleDistrict(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("about_constituency")
    Call<FullListDetails> processAboutConstituency();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleConstituency(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("about_villages")
    Call<FullListDetails> processAboutVillages();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleVillage(@Url String url);

    @Headers("Content-Type: application/json")
    @GET("about_leaders")
    Call<FullListDetails> processAboutLeaders();

    @Headers("Content-Type: application/json")
    @GET
    Call<SingleNewsDetail> processSingleLeader(@Url String url);


}
