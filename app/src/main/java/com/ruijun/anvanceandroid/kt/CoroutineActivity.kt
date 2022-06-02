package com.ruijun.anvanceandroid.kt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ruijun.anvanceandroid.R
import com.ruijun.anvanceandroid.databinding.ActivityKtBinding
import kotlinx.coroutines.*

class CoroutineActivity : AppCompatActivity() {
    companion object {
        const val TAG = "CoroutineActivity"
    }

    private lateinit var binding: ActivityKtBinding
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kt)


        val coroutineContext = Dispatchers.Main + CoroutineName("myContext")

        scope.async(coroutineContext) {
            val one = async { getResult(20) }
            val two = async { getResult(40) }
            Log.d(TAG, "getResult: ${Thread.currentThread().name}, ${this.coroutineContext.toString()}")
            binding.tvNum.text = (one.await() + two.await()).toString()
        }
    }

    private suspend fun getResult(num: Int): Int  = withContext(Dispatchers.IO) {
        Log.d(TAG, "getResult: ${Thread.currentThread().name}")
        delay(2000)
        num * num
    }

    // 3. 销毁的时候释放
    override fun onDestroy() {
        super.onDestroy()

        scope.cancel()
    }
}