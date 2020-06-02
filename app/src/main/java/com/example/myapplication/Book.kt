package com.example.myapplication

import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet
import androidx.ui.layout.preferredWidth
import androidx.ui.material.Button
import androidx.ui.material.Surface
import androidx.ui.unit.dp



@Model
class Book(var name: String)

@Composable
fun BookView(index: Int, book: Book, removeCallback: () -> Unit) {
    var bookField by state { TextFieldValue(book.name) }

    Surface(
        shape = RoundedCornerShape(5.dp),
        color = Color.White,
        border = Border(1.dp, Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier.preferredWidth(400.dp),
            constraintSet = ConstraintSet {
                val bookNumber = tag("bookNumber")
                val bookNameField = tag("bookNameField")
                val buttonField = tag("buttonField")

                bookNumber.left constrainTo parent.left
                bookNameField.left constrainTo bookNumber.right
                buttonField.right constrainTo parent.right
            }
        ) {
            Box(
                modifier = Modifier.tag("bookNumber"),
                padding = 5.dp
            ) {
                Text("Book #$index")
            }
            Box(
                modifier = Modifier.tag("bookNameField"),
                padding = 5.dp
            ) {
                TextField(
                    bookField,
                    onValueChange = {
                        bookField = it
                        book.name = it.text
                    },
                    modifier = Modifier.preferredWidth(200.dp)
                )
            }
            Box(
                modifier = Modifier.tag("buttonField"),
                padding = 5.dp
            ) {
                Button(onClick = removeCallback) {
                    Text("Remove book")
                }
            }
        }
    }
}