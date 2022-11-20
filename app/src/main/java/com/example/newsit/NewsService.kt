package com.example.newsit

import com.example.newsit.models.NewsApiResponse
import com.example.newsit.models.NewsHeadlines
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "ccb2e680af1e4ed0bf132ead97ef5453"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun callHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("apiKey") api_Key: String
    ): Call<NewsApiResponse>
}

object NewsService {

    val newsInstance: NewsInterface

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInstance = retrofit.create(NewsInterface::class.java)

       /* fun getNewsHeadlines(listener: OnFetchDataListener, category: String, query: String) {

            //newsInstance = retrofit.create(NewsInterface::class.java).callHeadlines("us", category, query, API_KEY)

            val call: Call<NewsApiResponse> =
                newsInstance.callHeadlines("us", category, query, API_KEY)

//            val callNewsApi: NewsInterface = retrofit.create(NewsInterface::class.java)
//            val call: Call<NewsHeadlines> =
//                callNewsApi.callHeadlines("us", category, query, API_KEY)
            try {
                call.enqueue(object : Callback<NewsApiResponse> {
                    override fun onResponse(
                        call: Call<NewsApiResponse>,
                        response: Response<NewsApiResponse>
                    ) {
                        if (!response.isSuccessful) {
                            Log.d("response", "ERROR")
                        }
                        listener.onFetchData(response.body()!!.articles, response.message())
                    }

                    override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                        listener.onError("Reqeust Failed")
                    }

                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/


    }
}

interface OnFetchDataListener {
    fun onFetchData(list: List<NewsHeadlines>, message: String)
    fun onError(message: String)
}