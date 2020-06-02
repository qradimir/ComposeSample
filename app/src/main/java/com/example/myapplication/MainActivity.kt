package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.lifecycle.*
import androidx.ui.core.setContent
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val bookDesk by state { testBookDesk() }

            MaterialTheme {
                BookDeskViewWithIndicator(bookDesk)
            }
        }
    }
}

@Preview
@Composable
fun testBookDescView() {
    val bookDesk by state { testBookDesk() }


    val mutableLiveData = MutableLiveData<Int>()
    observe(mutableLiveData)
    BookDeskView(bookDesk)
}

// copied
@Composable
fun <T> observe(data: LiveData<T>): T? {
    var result by state { data.value }
    val observer = remember { Observer<T> { result = it } }

    onCommit(data) {
        data.observeForever(observer)
        onDispose { data.removeObserver(observer) }
    }

    return result
}

@Preview
@Composable
fun testBookDescViewDuplicated() {
    val bookDesk by state { duplicatedBooks() }

    BookDeskViewWithIndicator(bookDesk)
}

private fun testBookDesk() = BookDesk().apply {
    books.add(Book("1984"))
    books.add(Book("Thinking, Fast and Slow"))
}

private fun duplicatedBooks() = BookDesk().apply {
    repeat(2) {
        books.add(Book("1984"))
    }
}