package com.devsparkle.spacexclient.base.resource

import androidx.lifecycle.Observer

class ResourceObserver<T>(
    private var loading: (() -> Unit)? = null,
    private var success: ((T?) -> Unit)? = null,
    private var successWithoutContent: (() -> Unit)? = null,
    private var error: ((Exception) -> Unit)? = null
) : Observer<Resource<T>> {

    override fun onChanged(resource: Resource<T>?) {
        when (resource) {
            is Resource.Loading -> loading?.invoke()
            is Resource.Success -> success?.invoke(resource.value())
            is Resource.SuccessWithoutContent -> successWithoutContent?.invoke()
            is Resource.Error -> error?.invoke(resource.error())
        }
    }
}
