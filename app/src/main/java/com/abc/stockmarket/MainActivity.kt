package com.abc.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abc.stockmarket.domain.model.CompanyListing
import com.abc.stockmarket.presentation.companies.view.CompaniesScreen
import com.abc.stockmarket.presentation.companyInfo.view.CompanyInfoScreen
import com.abc.stockmarket.ui.theme.StockMarketTheme
import com.abc.stockmarket.util.CustomNavType
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockMarketTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = CompaniesScreen) {
                    composable<CompaniesScreen> {
                        CompaniesScreen(navController)
                    }
                    composable<CompanyInfoScreen>(
                        typeMap = mapOf(
                            typeOf<CompanyListing>() to CustomNavType(
                                CompanyListing::class.java,
                                CompanyListing.serializer()
                            )
                        )
                    ) {
                        CompanyInfoScreen(navController)
                    }
                }

            }
        }
    }
}