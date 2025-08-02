package com.lightspark.composeqr

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

/**
 * A composable that renders a QR code from a data string.
 *
 * @param data The data to encode into a QR code.
 * @param modifier The Compose modifier to apply to the QR code.
 * @param encoder The encoder to use to encode the data into a QR code. Meant to be able to stub out in tests if needed.
 * @param overlayContent Optional content to overlay on top of the QR code. This overlay is limited to 25% of the size
 *      of the QR code and will be positioned in the center of it.
 */
@Composable
fun QrCodeView(
    data: String,
    modifier: Modifier = Modifier,
    encoder: QrEncoder = ZxingQrEncoder(),
    overlayContent: (@Composable () -> Unit)? = null,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        QrCodeView(
            data,
            modifier = Modifier.fillMaxSize(),
            encoder = encoder
        )
        if (overlayContent != null) {
            Box(modifier = Modifier.fillMaxSize(fraction = 0.25f)) {
                overlayContent()
            }
        }
    }
}

/**
 * A composable that renders a QR code from a data string with no overlay content.
 *
 * @param data The data to encode into a QR code.
 * @param modifier The Compose modifier to apply to the QR code.
 * @param encoder The encoder to use to encode the data into a QR code. Meant to be able to stub out in tests if needed.
 */
@Composable
fun QrCodeView(
    data: String,
    modifier: Modifier = Modifier,
    encoder: QrEncoder = ZxingQrEncoder()
) {
    val encodedData = remember(data, encoder) { encoder.encode(data) }

    Canvas(modifier = modifier.background(Color.White)) {
        encodedData?.let { matrix ->
            val cellSize = size.width / matrix.width
            for (x in 0 until matrix.width) {
                for (y in 0 until matrix.height) {
                    if (matrix.get(x, y) != 1.toByte() || isFinderCell(x, y, matrix.width)) continue
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(x * cellSize, y * cellSize),
                        size = Size(cellSize + 1, cellSize + 1)
                    )
                }
            }
            drawFinderSquares(cellSize)
        }
    }
}

