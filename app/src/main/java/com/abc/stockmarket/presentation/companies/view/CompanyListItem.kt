package com.abc.stockmarket.presentation.companies.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abc.stockmarket.domain.model.CompanyListing

@Composable
fun CompanyListItem(company: CompanyListing, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .border(1.dp, Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = company.name, fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = company.exchange, fontWeight = FontWeight.Light, fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "(${company.symbol})", fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light, fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Preview()
@Composable
fun CompanyListItemPreview(modifier: Modifier = Modifier) {
    CompanyListItem(company = CompanyListing(name = "Apple", symbol = "AAPL", exchange = "NASDAQ"))
}