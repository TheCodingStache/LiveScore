package dimitris.pallas.stoiximan.stoiximanapp.di

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dimitris.pallas.stoiximan.stoiximanapp.BuildConfig
import dimitris.pallas.stoiximan.stoiximanapp.SportAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(FragmentComponent::class)
class SportModule {
    @Provides
    fun sportsAPI(retrofit: Retrofit): SportAPI = retrofit.create(SportAPI::class.java)

    @Provides
    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}