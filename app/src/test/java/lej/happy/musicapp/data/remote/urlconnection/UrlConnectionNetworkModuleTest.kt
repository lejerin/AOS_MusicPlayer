package lej.happy.musicapp.data.remote.urlconnection

import junit.framework.TestCase
import lej.happy.musicapp.data.remote.NetworkService
import org.junit.Test
import java.lang.reflect.Method

class UrlConnectionNetworkModuleTest : TestCase() {
    private val urlConnectionNetworkModule = UrlConnectionNetworkModule()
    @Test
    fun testAddParamsUrl() {
        val params = mapOf<String, String>(
            "mck" to "1",
            "rank" to "2"
        )
        assertEquals("?mck=1&rank=2", reflection(urlConnectionNetworkModule, "addParamsUrl", arrayOf(params)))
    }

    private fun reflection(instance: Any, methodName: String, parameters: Array<Any>? = null) : Any? {
        val method: Method = instance.javaClass.getDeclaredMethod(methodName, null)
        method.isAccessible = true
        return method.invoke(instance, parameters)
    }
}