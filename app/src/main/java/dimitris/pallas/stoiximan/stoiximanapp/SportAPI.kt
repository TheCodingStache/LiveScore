package dimitris.pallas.stoiximan.stoiximanapp

import retrofit2.http.GET

interface SportAPI {
    @GET("/sports")
    suspend fun fetchSport(): List<SportsModel>
}