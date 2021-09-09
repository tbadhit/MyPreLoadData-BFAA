package id.tbadhit.mypreloaddata

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import id.tbadhit.mypreloaddata.databinding.ActivityMainBinding
import id.tbadhit.mypreloaddata.services.DataManagerService
import id.tbadhit.mypreloaddata.services.DataManagerService.Companion.CANCEL_MESSAGE
import id.tbadhit.mypreloaddata.services.DataManagerService.Companion.FAILED_MESSAGE
import id.tbadhit.mypreloaddata.services.DataManagerService.Companion.PREPARATION_MESSAGE
import id.tbadhit.mypreloaddata.services.DataManagerService.Companion.SUCCESS_MESSAGE
import id.tbadhit.mypreloaddata.services.DataManagerService.Companion.UPDATE_MESSAGE
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), HandlerCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mBoundService: Messenger
    private var mServiceBound: Boolean = false

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBoundService = Messenger(service)
            mServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServiceBound = false
        }

    }

    private class IncomingHandler(callback: HandlerCallback): Handler(Looper.getMainLooper()!!) {
        private var weakCallback: WeakReference<HandlerCallback> = WeakReference(callback)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                PREPARATION_MESSAGE -> weakCallback.get()?.onPreparation()
                UPDATE_MESSAGE -> {
                    val bundle = msg.data
                    val progress = bundle.getLong("KEY_PROGRESS")
                    weakCallback.get()?.updateProgress(progress)
                }
                SUCCESS_MESSAGE -> weakCallback.get()?.loadSuccess()
                FAILED_MESSAGE -> weakCallback.get()?.loadFailed()
                CANCEL_MESSAGE -> weakCallback.get()?.loadCancel()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mBoundServiceIntent = Intent(this@MainActivity, DataManagerService::class.java)
        val mActivityMessenger = Messenger(IncomingHandler(this))
        mBoundServiceIntent.putExtra(DataManagerService.ACTIVITY_HANDLER, mActivityMessenger)

        bindService(mBoundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onPreparation() {
        Toast.makeText(this, "MEMULAI MEMUAT DATA", Toast.LENGTH_LONG).show()
    }

    override fun updateProgress(progress: Long) {
        Log.d("PROGRESS", "updateProgress: $progress")
        binding.progressBar.progress = progress.toInt()
    }

    override fun loadSuccess() {
        Toast.makeText(this, "BERHASIL", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@MainActivity, MahasiswaActivity::class.java))
        finish()
    }

    override fun loadFailed() {
        Toast.makeText(this, "GAGAL", Toast.LENGTH_LONG).show()
    }

    override fun loadCancel() {
        finish()
    }

}

private interface HandlerCallback {
    fun onPreparation()

    fun updateProgress(progress: Long)

    fun loadSuccess()
    fun loadFailed()
    fun loadCancel()
}