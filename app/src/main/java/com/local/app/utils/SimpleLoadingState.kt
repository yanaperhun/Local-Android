package org.ifpri.frani.ui.states

import com.local.app.data.AppException

sealed class SimpleLoadingState<out T> {
  object Loading : SimpleLoadingState<Nothing>()
  class Error(val error: AppException) : SimpleLoadingState<Nothing>()
  class Success<T>(val value: T) : SimpleLoadingState<T>()
}