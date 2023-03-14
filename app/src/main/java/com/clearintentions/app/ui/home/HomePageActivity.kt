package com.clearintentions.app.ui.home

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.clearintentions.app.R
import com.clearintentions.app.data.model.BasicUser
import com.clearintentions.app.data.model.ParcelableString
import com.clearintentions.app.ui.login.LoginActivity
import com.clearintentions.app.utils.changeActivity

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val user = remember { mutableStateOf<BasicUser?>(null) }
            intent.extras?.getParcelable<BasicUser>("user").let { user.value = it }
            UserDisplay(user)
        }
    }
}

@Composable
fun UserDisplay(userLoggedIn: MutableState<BasicUser?>) {
    if (userLoggedIn.value == null) {
        changeActivity(
            LocalContext.current,
            LoginActivity::class.java,
            arrayOf("message"),
            arrayOf(ParcelableString("A problem occurred with your account- please sign in again!"))
        )
    } else {
        val user = userLoggedIn.value!!
        val context = LocalContext.current
        Column {
            Text(text = "${stringResource(R.string.display_name_label)}: ${user.displayName}")
            Text(text = "${stringResource(R.string.age_label)}: ${user.age}")
            Text(text = "${stringResource(R.string.email_label)}: ${user.email}")
            Text(text = "${stringResource(R.string.gender_label)}: ${user.gender}")
            Text(text = "${stringResource(R.string.phone_number_label)}: ${user.phoneNumber}")
            Button(onClick = { goToMap(context, user) }) {
                Text("Map test page")
            }
        }
    }
}

fun goToMap(context: Context, basicUser: BasicUser) {
    changeActivity(context, TestMapActivity::class.java, arrayOf("user"), arrayOf(basicUser))
}
