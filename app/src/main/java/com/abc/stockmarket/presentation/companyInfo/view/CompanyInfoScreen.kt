package com.abc.stockmarket.presentation.companyInfo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abc.stockmarket.domain.model.CompanyListing
import com.abc.stockmarket.presentation.companyInfo.event.CompanyInfoEvent
import com.abc.stockmarket.presentation.companyInfo.state.CompanyInfoState
import com.abc.stockmarket.presentation.companyInfo.viewModel.CompanyInfoViewModel
import com.abc.stockmarket.util.collectAsEffect
import com.abc.stockmarket.util.toast
import kotlinx.serialization.Serializable


@Composable
fun CompanyInfoScreen(
    navController: NavHostController,
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    //region collect flows

    viewModel.error.collectAsEffect {
        context.toast(it)
    }
    //endregion

    CompanyInfoContent(
        state = viewModel.state,
        onEvent = { viewModel.onEvent(it) }
    )
}


@Composable
private fun CompanyInfoContent(
    state: CompanyInfoState,
    onEvent: (CompanyInfoEvent) -> Unit,
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 50.dp, horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = state.companyInfo.name, fontWeight = FontWeight.Bold, fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = state.companyInfo.country, fontWeight = FontWeight.Light, fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = state.companyInfo.symbol, fontWeight = FontWeight.Light, fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = state.companyInfo.industry, fontWeight = FontWeight.Light, fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Divider()
            Text(
                text = state.companyInfo.description,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Preview
@Composable
fun CompanyInfoPreview(modifier: Modifier = Modifier) {
    CompanyInfoContent(
        state = CompanyInfoState(),
        onEvent = {}
    )
}