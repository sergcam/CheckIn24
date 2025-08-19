package dev.secam.checkin24.ui.history.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.secam.checkin24.R
import dev.secam.checkin24.util.SetDialogDim

@Composable
fun DeleteAllDialog(onConfirm: () -> Unit, onCancel: () -> Unit){
    Dialog(onDismissRequest = { onCancel() }) {
        SetDialogDim()
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 26.dp, vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.delete_data),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
                Text(
                    text = stringResource(R.string.delete_data_warning),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                Column(

                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Row {
                        TextButton(
                            onClick = { onCancel() },

                            ) {
                            Text(stringResource(R.string.dialog_cancel))
                        }
                        TextButton(
                            onClick = {
                                onConfirm()
                                onCancel()
                            },
                        ) {
                            Text(stringResource(R.string.dialog_delete))
                        }
                    }
                }
            }

        }
    }
}