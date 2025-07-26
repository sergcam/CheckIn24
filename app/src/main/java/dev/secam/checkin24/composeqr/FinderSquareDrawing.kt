package dev.secam.checkin24.composeqr

//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.size
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp

private const val FINDER_SQUARE_DOT_SIZE = 7

internal fun DrawScope.drawFinderSquares(
    cellSize: Float,
) {
    drawFinderSquare(cellSize, Offset(0f, 0f))
    drawFinderSquare(
        cellSize,
        Offset(size.width - FINDER_SQUARE_DOT_SIZE * cellSize, 0f)
    )
    drawFinderSquare(
        cellSize,
        Offset(0f, size.width - FINDER_SQUARE_DOT_SIZE * cellSize)
    )
}

internal fun isFinderCell(x: Int, y: Int, gridSize: Int) =
    (x < FINDER_SQUARE_DOT_SIZE && y < FINDER_SQUARE_DOT_SIZE) ||
            (x < FINDER_SQUARE_DOT_SIZE && y > gridSize - 1 - FINDER_SQUARE_DOT_SIZE) ||
            (x > gridSize - 1 - FINDER_SQUARE_DOT_SIZE && y < FINDER_SQUARE_DOT_SIZE)

private fun DrawScope.drawFinderSquare(
    cellSize: Float,
    topLeft: Offset,
) {


    drawRect(
        color = Color.Black,
        topLeft = topLeft,
        size = Size(cellSize * FINDER_SQUARE_DOT_SIZE, cellSize * FINDER_SQUARE_DOT_SIZE),
    )
    drawRect(
        color = Color.White,
        topLeft = topLeft + Offset(cellSize, cellSize),
        size = Size(
            cellSize * (FINDER_SQUARE_DOT_SIZE - 2),
            cellSize * (FINDER_SQUARE_DOT_SIZE - 2)
        ),
    )


    drawRect(
        color = Color.Black,
        topLeft = topLeft + Offset(cellSize * 2, cellSize * 2),
        size = Size(
            cellSize * (FINDER_SQUARE_DOT_SIZE - 4),
            cellSize * (FINDER_SQUARE_DOT_SIZE - 4)
        ),
    )
}

//@Preview(showBackground = true)
//@Composable
//fun FinderSquaresPreview() {
//    Canvas(
//        modifier = Modifier
//            .size(300.dp)
//            .background(Color.White)
//    ) {
//        drawFinderSquares(cellSize = 10f)
//    }
//}

