package com.abc.stockmarket.presentation.companies.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abc.stockmarket.R
import com.abc.stockmarket.presentation.common.navigation.Routes
import com.abc.stockmarket.presentation.companies.event.CompaniesEvent
import com.abc.stockmarket.presentation.companies.state.CompaniesState
import com.abc.stockmarket.presentation.companies.viewModel.CompaniesViewModel
import com.abc.stockmarket.presentation.companyInfo.view.CompanyInfoScreen
import com.abc.stockmarket.util.collectAsEffect
import com.abc.stockmarket.util.toast
import kotlinx.serialization.Serializable


@Composable
fun CompaniesScreen(
    navController: NavHostController,
    viewModel: CompaniesViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    //region collect flows
    viewModel.navigateToCompanyInfo.collectAsEffect {
        navController.navigate(Routes.CompanyInfoScreen(it))
    }

    viewModel.error.collectAsEffect {
        context.toast(it)
    }
    //endregion

    CompaniesContent(
        state = viewModel.state,
        onEvent = { viewModel.onEvent(it) }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CompaniesContent(
    state: CompaniesState,
    onEvent: (CompaniesEvent) -> Unit,
) {

    val refreshState =
        rememberPullRefreshState(
            refreshing = state.isRefreshing,
            onRefresh = { onEvent(CompaniesEvent.Refresh) }
        )

    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 50.dp, horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { onEvent(CompaniesEvent.OnSearchQueryChange(it)) },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(id = R.string.search))
                },
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.companies.size) { i ->
                    CompanyListItem(company = state.companies[i],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEvent(CompaniesEvent.OnItemClicked(i))
                            })
                    if (i < state.companies.size) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

        }

        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

}

@Preview
@Composable
fun CompaniesPreview(modifier: Modifier = Modifier) {
    CompaniesContent(
        state = CompaniesState(),
        onEvent = {}
    )
}