package com.sumedh.upstoxcoin.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun FilterOptions(
    onFilter: (Boolean?, String?, Boolean?) -> Unit
) {
    val filterOptions = listOf("Active Coins", "Inactive Coins", "Only Tokens", "Only Coins", "New Coins")
    val selectedFilters = remember { mutableStateOf(setOf<String>()) }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        filterOptions.forEach { option ->
            FilterItem (
                label = option,
                isSelected = selectedFilters.value.contains(option),
                onClick = {
                    val updatedFilters = if (selectedFilters.value.contains(option)) {
                        selectedFilters.value - option // Deselect
                    } else {
                        selectedFilters.value + option // Select
                    }
                    selectedFilters.value = updatedFilters
                    onFilter(false, option, updatedFilters.contains(option))
                }
            )
        }
    }

//    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            CheckboxWithLabel(
//                label = "Active Coins",
//                checked = isActive == true,
//                onCheckedChange = {
//                    isActive = if (it) true else null
//                }
//            )
//
//            CheckboxWithLabel(
//                label = "Inactive Coins",
//                checked = isInActive == true,
//                onCheckedChange = { isInActive = if (it) true else null }
//            )
//        }
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            CheckboxWithLabel(
//                label = "New Coins",
//                checked = isNew == true,
//                onCheckedChange = { isNew = if (it) true else null }
//            )
//
//            CheckboxWithLabel(
//                label = "Only Coin",
//                checked = type == "coin",
//                onCheckedChange = { type = if (it) "coin" else null }
//            )
//
//            CheckboxWithLabel(
//                label = "Only Token",
//                checked = type == "token",
//                onCheckedChange = { type = if (it) "token" else null }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = { onFilter(isActive, type, isNew) },
//            modifier = Modifier.align(Alignment.End)
//        ) {
//            Text("Apply Filters")
//        }
//    }
}

//@Composable
//fun CheckboxWithLabel(
//    label: String,
//    checked: Boolean,
//    onCheckedChange: (Boolean) -> Unit
//) {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Checkbox(
//            checked = checked,
//            onCheckedChange = onCheckedChange
//        )
//        Text(text = label, style = MaterialTheme.typography.body1)
//    }
//}