package fi.omaralm.w1d3_networkandthreads

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.*
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import fi.omaralm.w1d3_networkandthreads.ui.theme.W1d3_NetworkAndThreadsTheme
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : ComponentActivity() {

    var text: String by mutableStateOf("")

    // for checking network availability
    private fun isNetworkAvailable(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.isDefaultNetworkActive
        if (networkInfo) {
            Log.e(TAG, "The default network is now : $networkInfo")
        }
        return networkInfo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            W1d3_NetworkAndThreadsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // print and display the data from network
                    Greeting(name = text)

                    if (isNetworkAvailable()) {
                        val myRunnable = MyConnection(
                            myHandler
                        )
                        val myThread = Thread(myRunnable)
                        myThread.start()
                        Log.e(TAG,"my main thread :$myThread")


                        val myThread2 = Thread(MyConnection(
                            myHandler
                        ))
                        myThread2.start()
                       Log.e(TAG,"my second thread :$myThread2")
                    }
                }
            }
        }
    }

    private val myHandler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(inputMessage: Message) {
            if (inputMessage.what == 0) {

                text = inputMessage.obj.toString()

                Log.e(TAG, "  in myHandler: The result of display is now: => $text")
            }
        }
    }


}

// main thread for communications
class MyConnection(myHand: Handler ) : Runnable {
    private val myHandler = myHand

    override fun run() {
        try {
            val myUrl = URL(" https://users.metropolia.fi/~jarkkov/koe.txt")
            val myConn = myUrl.openConnection() as HttpURLConnection
            myConn.requestMethod = "POST"
            myConn.doOutput = true

            val istream: InputStream = myConn.getInputStream()
            val allText = istream.bufferedReader().use {
                it.readText()
            }
            val result = StringBuilder()
            result.append(allText)
            val str = result.toString()
            val msg = myHandler.obtainMessage()
            msg.what = 0
            msg.obj = str
            myHandler.sendMessage(msg)

        } catch (e: Exception) {
            Log.i(TAG, " error with URL  :$e")

        }
    }
}


        @Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
/*
fun establishUrlConnection( url: String){
    var successful= false;
    var  downloadURL: URL? = null;
    var  connecation: HttpURLConnection ? = null;
    var inputSteam :InputStream?= null;
    try {
        downloadURL = URL(url);

    }catch (e: Exception) {
        Log.i(TAG," error with URL establishUrlConnection function :$e")

    }
}

 */
/*
*

class MainActivity : ComponentActivity() {

    var text: String by mutableStateOf("")

    // for checking network availability
    private fun isNetworkAvailable(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.isDefaultNetworkActive
        if (networkInfo) {
            Log.e(TAG, "The default network is now: $networkInfo")
        }
        return networkInfo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            W1d3_NetworkAndThreadsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // print and display the data from network
                    Greeting(name = text)

                    if (isNetworkAvailable()) {
                        val myRunnable = MyConnection(
                            myHandler,
                            "John",
                            "Doe"
                        )
                        val myThread = Thread(myRunnable)
                        myThread.start()
                        Log.e(TAG,"my main thread :$myThread")


                        val myThread2 = Thread(MyConnection(
                            myHandler,
                            "Omar",
                            "Ome"
                        ))
                        myThread2.start()
                       Log.e(TAG,"my second thread :$myThread2")
                    }
                }
            }
        }
    }

    private val myHandler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(inputMessage: Message) {
            if (inputMessage.what == 0) {

                text = inputMessage.obj.toString()

                Log.e(TAG, "  in myHandler: The result of display is now: => $text")
            }
        }
    }


}

// main thread for communications
class MyConnection(
    myHand: Handler,
    private val fname: String,
    private val lname: String
) : Runnable {
    private val myHandler = myHand

    override fun run() {
        try {
            val myUrl = URL(" https://users.metropolia.fi/~jarkkov/koe.txt")
            val myConn = myUrl.openConnection() as HttpURLConnection
            myConn.requestMethod = "POST"
            myConn.doOutput = true

            val ostream = myConn.getOutputStream()
            ostream.bufferedWriter().use {
                it.write("fn=${fname}&ln=${lname}")


            }
            val istream: InputStream = myConn.getInputStream()
            val allText = istream.bufferedReader().use {
                it.readText()
            }
            val result = StringBuilder()
            result.append(allText)
            val str = result.toString()
            val msg = myHandler.obtainMessage()
            msg.what = 0
            msg.obj = str
            myHandler.sendMessage(msg)
        } catch (e: Exception) {
            Log.i(TAG, " error with URL  :$e")

        }
    }
}


        @Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
* */