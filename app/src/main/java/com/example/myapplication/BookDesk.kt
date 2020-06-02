package com.example.myapplication

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.frames.ModelList
import androidx.compose.frames.modelListOf
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet
import androidx.ui.material.Button
import androidx.ui.unit.dp


@Model
class BookDesk {
    val books: ModelList<Book> = modelListOf()
}

@Composable
fun BookDeskView(bookDesc: BookDesk) {
    Column {
        for ((i, book) in bookDesc.books.withIndex()) {
            Box(padding = 10.dp) {
                BookView(i, book) { bookDesc.books.removeAt(i) }
            }
        }
        Box(padding = 10.dp) {
            Button(onClick = { bookDesc.books.add(Book("")) }) {
                Text("New book")
            }
        }
    }
}

@Composable
fun BookDeskViewWithIndicator(bookDesc: BookDesk) {
    val duplicated = bookDesc.books.distinctBy { it.name }.size < bookDesc.books.size
    val empty = bookDesc.books.find { it.name.isBlank() } != null

    ConstraintLayout(
        constraintSet = ConstraintSet {
            val contentTag = tag("content")
            contentTag.top constrainTo parent.top

            val errorTag = tag("error")
            errorTag.top constrainTo contentTag.bottom
            errorTag.bottom constrainTo parent.bottom
        }
    ) {
        Box(modifier = Modifier.tag("content")) {
            BookDeskView(bookDesc)
        }
        Box(modifier = Modifier.tag("error"), padding = 15.dp) {
            if (empty) {
                Text(text = "Book desk has an unnamed book!", color = Color.Red)
            } else if (duplicated) {
                Text(text = "Book desk has duplicated books!", color = Color.Red)
            }
        }
    }
}
