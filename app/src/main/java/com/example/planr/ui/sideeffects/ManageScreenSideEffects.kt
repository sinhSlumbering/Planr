package com.example.planr.ui.sideeffects

sealed class ManageScreenSideEffects {
    data class ShowSnackBarMessage(val message: String): ManageScreenSideEffects()
}