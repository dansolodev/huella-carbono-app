package com.fs.huellacarbonoapp.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.fs.huellacarbonoapp.ui.theme.HuellaCarbonoAppTheme

@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    currentRoute: String,
    onNavigationItemSelected: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth()
    ) {
        items.forEach { navigationItem ->
            NavigationBarItem(
                selected = currentRoute == navigationItem.route,
                onClick = { onNavigationItemSelected(navigationItem) },
                icon = {
                    Icon(
                        imageVector = if (navigationItem.route == currentRoute) {
                            navigationItem.selectedIcon
                        } else {
                            navigationItem.unSelectedIcon
                        },
                        contentDescription = stringResource(navigationItem.titleIdRes)
                    )
                },
                label = {
                    Text(
                        text = stringResource(navigationItem.titleIdRes),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = if (navigationItem.route == currentRoute) {
                            MaterialTheme.typography.labelLarge
                        } else {
                            MaterialTheme.typography.labelMedium
                        }
                    )
                }
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun BottomNavigationBarPreview() {
    HuellaCarbonoAppTheme {
        BottomNavigationBar(
            items = navigationItemsList,
            currentRoute = navigationItemsList.first().route,
            onNavigationItemSelected = {}
        )
    }
}
