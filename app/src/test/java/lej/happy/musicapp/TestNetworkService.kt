package lej.happy.musicapp

import junit.framework.TestCase
import org.junit.Test

class TestNetworkService : TestCase() {

    @Test
    fun testGetApiUrl() {
        var type = ListApi.TOP
        assertEquals("https://happyweatherapp.herokuapp.com/music/info/top",
            NetworkService.instance.getApi(type).path)
        type = ListApi.RECENT
        assertEquals("https://happyweatherapp.herokuapp.com/music/info/new",
            NetworkService.instance.getApi(type).path)
    }
}