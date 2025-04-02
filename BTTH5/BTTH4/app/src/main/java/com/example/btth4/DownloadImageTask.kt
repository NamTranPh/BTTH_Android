package com.example.btth4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.ProgressBar
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadImageTask(
    private val imageView: ImageView,
    private val progressBar: ProgressBar
) : AsyncTask<String, Int, Bitmap?>() {

    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.visibility = ProgressBar.VISIBLE // Hiển thị ProgressBar
    }

    override fun doInBackground(vararg urls: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(urls[0]) // Lấy URL
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)

            // Giả sử là tiến trình tải về (cho mục đích demo)
            for (i in 1..100) {
                Thread.sleep(20) // Giả lập việc tải dữ liệu
                publishProgress(i) // Cập nhật tiến trình
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        progressBar.progress = values[0] ?: 0 // Cập nhật tiến trình
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        progressBar.visibility = ProgressBar.GONE // Ẩn ProgressBar
        result?.let {
            imageView.setImageBitmap(it) // Hiển thị ảnh lên ImageView
        }
    }
}
