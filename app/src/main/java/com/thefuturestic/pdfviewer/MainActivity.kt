package com.thefuturestic.pdfviewer

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var pdfView: PDFView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pdfView = findViewById<View>(R.id.pdfView) as PDFView

        val link ="https://firebasestorage.googleapis.com/v0/b/etquiz.appspot.com/o/Sadaqat%20Ali%201%20copy.pdf?alt=media&token=87ba3757-22e2-4e77-9c93-88be645824a3"
        RetrivePdfStream().execute(link)
    }

    internal inner class RetrivePdfStream : AsyncTask<String, Void, InputStream>() {
        override fun doInBackground(vararg strings: String): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(strings[0])
                val urlConnection = url.openConnection() as HttpURLConnection
                if (urlConnection.responseCode == 200) {
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            } catch (e: IOException) {
                return null
            }

            return inputStream
        }

        override fun onPostExecute(inputStream: InputStream) {
            pdfView!!.fromStream(inputStream).load()
        }

    }
}