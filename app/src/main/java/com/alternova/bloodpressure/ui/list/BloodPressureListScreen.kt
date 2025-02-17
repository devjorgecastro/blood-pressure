@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alternova.bloodpressure.ui.list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.constraintlayout.compose.ConstraintLayout
import com.alternova.bloodpressure.FAB_EXPLODE_BOUNDS_KEY
import com.alternova.bloodpressure.R
import com.alternova.bloodpressure.ui.theme.BloodPressureTheme

@Composable
fun SharedTransitionScope.BloodPressureListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNewBloodPressureClick: () -> Unit = {}
) {
    BloodPressureListScreen(
        onNewBloodPressureClick = onNewBloodPressureClick,
        modifier = Modifier
            .sharedBounds(
                sharedContentState = rememberSharedContentState(
                    key = FAB_EXPLODE_BOUNDS_KEY
                ),
                animatedVisibilityScope = animatedVisibilityScope
            )
    )
}

@Composable
private fun BloodPressureListScreen(
    modifier: Modifier = Modifier,
    onNewBloodPressureClick: () -> Unit = {}
) {
    //val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onNewBloodPressureClick() },
                expanded = false,
                icon = { Icon(Icons.Filled.Add, "Localized Description") },
                text = { Text(text = "Add Blood Pressure") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = modifier
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)

        ) {
            val (imageRef) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.blood_pressure),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)

                    }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun BloodPressureListScreenPreview() {
    BloodPressureTheme(
        dynamicColor = false
    ) {
        BloodPressureListScreen()
    }
}
