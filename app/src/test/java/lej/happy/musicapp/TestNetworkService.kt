package lej.happy.musicapp

import junit.framework.TestCase
import lej.happy.musicapp.data.remote.NetworkService
import org.junit.Test

class TestNetworkService : TestCase() {

    @Test
    fun testGetApiUrl() {
        assertEquals("https://happyweatherapp.herokuapp.com/music/info/top",
            NetworkService.Api.LIST_TOP_API.path)
        assertEquals("https://happyweatherapp.herokuapp.com/music/info/new",
            NetworkService.Api.LIST_RECENT_API.path)
    }
}