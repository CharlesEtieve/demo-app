package com.demo.app.app.ui.protocols

/**
 * Common Interface for Cell Selection in a RecyclerView
 */
interface CellSelectionDelegate {

    /**
     * Method called when a cell has been selected
     * @param index: Position of the selected cell
     */
    fun didSelectCellAt(index: Int)
}
