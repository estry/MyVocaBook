package com.example.myvocabook.ui.web

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myvocabook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WebFragment : Fragment() {
    val webViewModel: WebViewModel by viewModels({ requireParentFragment() })
    var word = "apple"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            webViewModel.selectedWord.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                word = it
                Log.d("word_test",word)
            })
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(10L)
            val webView = view.findViewById<WebView>(R.id.webView)
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true
            webView.settings.builtInZoomControls = true
            webView.settings.defaultTextEncodingName = "utf-8"
            webView.loadUrl("https://en.dict.naver.com/#/search?query=" + word)
        }
    }
}