package com.example.myapplication.view

sealed class MainViewState {
    /**
     * Network state loading.
     */
    object Loading : MainViewState()

    /**
     * Network state Success.
     */
    object Success : MainViewState()


    /**
     * Error occurred state.
     */
    data class Error(
        val errorMessage: String?,
        val stringResourceId: String?,
        val errorCode: Int?,
    ) : MainViewState()


    object someOtherOne : MainViewState()
}