package com.icerockdev.library

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class SampleViewModel(
    override val eventsDispatcher: EventsDispatcher<EventListener>,
    private val permissionsController: PermissionsController
) : ViewModel(), EventsDispatcherOwner<SampleViewModel.EventListener> {

    /**
     * An example of using [PermissionsController] in common code.
     */
    fun onRequestPermissionButtonPressed() {
        // For example, let's request the camera permission:
        val permissionType = Permission.RECORD_AUDIO
        requestPermission(permissionType)
    }

    private fun requestPermission(permission: Permission) {
        viewModelScope.launch {
            try {
                // Calls suspend function in a coroutine to request some permission.
                permissionsController.providePermission(permission)
                // If there are no exceptions, permission has been granted successfully.
                eventsDispatcher.dispatchEvent { onSuccess() }
            } catch (deniedAlwaysException: DeniedAlwaysException) {
                eventsDispatcher.dispatchEvent { onDeniedAlways(deniedAlwaysException) }
            } catch (deniedException: DeniedException) {
                eventsDispatcher.dispatchEvent { onDenied(deniedException) }
            }
        }
    }

    interface EventListener {

        fun onSuccess()

        fun onDenied(exception: DeniedException)

        fun onDeniedAlways(exception: DeniedAlwaysException)
    }
}
