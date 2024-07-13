package com.vishnu.whatsappcleaner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {

    var directoryList = viewModel.getDirectoryList().observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Title(
                Modifier
                    .padding(0.dp)
                    .align(Alignment.Start),
                stringResource(R.string.app_name)
            )

            Banner(Modifier.padding(16.dp),
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 24.sp)) {
                        append("512")
                    }
                    withStyle(SpanStyle(fontSize = 12.sp)) {
                        append(" MB")
                    }
                })

            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp, 16.dp, 16.dp, 8.dp),
                text = "Select to Explore",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            LazyColumn(Modifier.weight(1f)) {
                items(directoryList.value!!, key = { it.name }) {
                    SingleCard(it, navController)
                }
            }
        }
    }
}

@Composable
fun Banner(modifier: Modifier, text: AnnotatedString) {
    Card(
        modifier
            .fillMaxWidth()
            .aspectRatio(2f),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier
                    .size(128.dp)
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.secondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }

            TextButton(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(64.dp),
                contentPadding = PaddingValues(12.dp),
                onClick = { }
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSecondary)) {
                            append("Cleanup")
                        }
                    }, fontSize = 24.sp, fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleCard(
    listDirectory: ListDirectory,
    navController: NavHostController
) {

    val modifier = if (listDirectory.path.contains("com.vishnu.whatsappcleaner.loading"))
        Modifier.shimmer()
    else
        Modifier

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.apply {
                set(Constants.DETAILS_LIST_ITEM, null)
            }
            navController.navigate(Constants.SCREEN_DETAILS)
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.20f)
                    .aspectRatio(1f),
                imageVector = ImageVector.vectorResource(id = listDirectory.icon),
                contentDescription = "icon"
            )

            Column(
                Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(4.dp),
                    text = listDirectory.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(4.dp),
                    text = listDirectory.size,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}