@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alternova.bloodpressure.ui.list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alternova.bloodpressure.FAB_EXPLODE_BOUNDS_KEY
import com.alternova.bloodpressure.R
import com.alternova.bloodpressure.common.compose.CollectEffect
import com.alternova.bloodpressure.domain.model.BloodPressureCategory
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.model.MeasurementState
import com.alternova.bloodpressure.ui.theme.BloodPressureTheme

@Composable
fun SharedTransitionScope.BloodPressureListScreen(
    viewModel: BloodPressureListViewModel,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val context = LocalContext.current
    val errorMessage = stringResource(R.string.error_loading_blood_pressure)
    val state = viewModel.state.collectAsStateWithLifecycle()

    CollectEffect(viewModel.effect) {
        when (it) {
            BloodPressureListContract.ViewEffect.ShowLoadError -> {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    BloodPressureListScreen(
        state = state.value,
        onNewBloodPressureClick = viewModel::onNewBloodPressureClick,
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
    state: BloodPressureListContract.State = BloodPressureListContract.State(),
    modifier: Modifier = Modifier,
    onNewBloodPressureClick: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onNewBloodPressureClick() },
                expanded = expandedFab,
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
            val (contentRef) = createRefs()
            if (state.data.isEmpty()) {
                EmptyState(contentRef)
            } else {
                BloodPressureList(
                    data = state.data,
                    lazyListState = listState,
                    reference = contentRef
                )
            }
        }
    }
}

@Composable
private fun ConstraintLayoutScope.EmptyState(reference: ConstrainedLayoutReference) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .constrainAs(reference) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.blood_pressure),
            contentDescription = null,
            alpha = 0.4f
        )

        Text(
            text = stringResource(R.string.no_measurements_yet),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ConstraintLayoutScope.BloodPressureList(
    data: List<BloodPressureMeasurement> = emptyList(),
    lazyListState: LazyListState,
    reference: ConstrainedLayoutReference
) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .constrainAs(reference) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
    ) {
        items(data) {
            BloodPressureListItem(it)
        }
    }
}

@Composable
private fun BloodPressureListItem(item: BloodPressureMeasurement) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {

            TextCategory(item.getBloodPressureCategory())

            HorizontalDivider(
                modifier = Modifier.height(1.dp)
            )

            Text(
                text = "Systolic: ${item.systolic} mmHg"
            )

            Text(
                text = "Diastolic: ${item.diastolic} mmHg"
            )
        }
    }
}

@Composable
private fun TextCategory(category: BloodPressureCategory) {

    val description = when (category) {
        BloodPressureCategory.HYPERTENSIVE_CRISIS -> stringResource(R.string.blood_pressure_hypertension_crisis)
        BloodPressureCategory.HYPERTENSION_STAGE_1 -> stringResource(R.string.blood_pressure_hypertension_stage_1)
        BloodPressureCategory.HYPERTENSION_STAGE_2 -> stringResource(R.string.blood_pressure_hypertension_stage_2)
        BloodPressureCategory.ELEVATED_PRESSURE -> stringResource(R.string.blood_pressure_elevated_pressure)
        BloodPressureCategory.NORMAL_PRESSURE -> stringResource(R.string.blood_pressure_normal_pressure)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = description,
                color = Color(category.color),
                style = MaterialTheme.typography.headlineSmall,
            )

            if (category == BloodPressureCategory.HYPERTENSIVE_CRISIS) {
                Text(
                    text = stringResource(R.string.blood_pressure_urgent_medical_attention_required),
                    color = Color(category.color),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
        Icon(
            painter = painterResource(R.drawable.baseline_monitor_heart_24),
            contentDescription = null,
            tint = Color(category.color),
            modifier = Modifier
                .size(32.dp)
        )
    }
}

@PreviewLightDark
@Composable
fun BloodPressureListScreenPreview(
    @PreviewParameter(BloodPressureMeasurementParameterProvider::class) data: List<BloodPressureMeasurement>
) {
    BloodPressureTheme(
        dynamicColor = false
    ) {
        BloodPressureListScreen(
            state = BloodPressureListContract.State(
                data = data
            )
        )
    }
}


private class BloodPressureMeasurementParameterProvider : PreviewParameterProvider<List<BloodPressureMeasurement>> {
    override val values = sequenceOf(
        listOf(
            BloodPressureMeasurement(
                systolic = 120,
                diastolic = 80,
                date = "14/02/2024 15:30",
                state = MeasurementState.Rest
            ),
            BloodPressureMeasurement(
                systolic = 120,
                diastolic = 130,
                date = "14/02/2024 15:30",
                state = MeasurementState.Rest
            )
        ),
        emptyList()
    )
}
