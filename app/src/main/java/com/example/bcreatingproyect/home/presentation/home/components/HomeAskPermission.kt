package com.example.bcreatingproyect.home.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.bcreatingproyect.core.presentation.HabitButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeAskPermission(
    permission: String,
    modifier: Modifier = Modifier
) {
    val permissionState =
        rememberPermissionState(permission = permission)
    LaunchedEffect(key1 = Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.shouldShowRationale) {
        AlertDialog(
            onDismissRequest = { },
            modifier = modifier,
            confirmButton = {
                HabitButton(text = "Accept", modifier = Modifier.fillMaxWidth()) {
                    permissionState.launchPermissionRequest()
                }
            },
            title = {
                androidx.compose.material3.Text(text = "Permission Required")
            },
            text = {
                androidx.compose.material3.Text(text = "We need this permission for the app to work correctly")
            }
        )
    }
}
