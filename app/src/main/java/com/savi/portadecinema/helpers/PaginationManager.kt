package com.savi.portadecinema.helpers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationManager(
    private val pageSize: Int,
    private val triggerIndex: Int,
    private val pageLoadCallback: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private lateinit var layoutManager: LinearLayoutManager
    private val scrollIndex: MutableLiveData<Int> = MutableLiveData(0)
    private var currentPage: Int = 0
    private val absoluteTrigger: Int
        get() = ((currentPage * pageSize) + triggerIndex)

    init {
        scrollIndex.observeForever { index ->
            Log.i("SCROLL_TEST", "onScrolled() index: $index | trigger: $absoluteTrigger")
            if (shouldLoadNextPage(index)) {
                loadNextPage()
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // Não fazer nada se o usuário rolar para cima.
        if (dy < 0)
            return

        // Se não foi inicializado, obter o layout manager do recycler view.
        if (!this::layoutManager.isInitialized) {
            layoutManager = recyclerView.layoutManager as LinearLayoutManager
        }

        val index = layoutManager.findLastVisibleItemPosition()
        if (index > scrollIndex.value!!) {
            scrollIndex.postValue(index)
        }
    }

    private fun shouldLoadNextPage(lastVisibleItemIndex: Int): Boolean {
        return lastVisibleItemIndex >= absoluteTrigger
    }

    private fun loadNextPage() {
        currentPage += 1
        pageLoadCallback.invoke(currentPage)
    }
}