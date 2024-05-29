package `in`.iot.lab.network.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource


internal const val PAGE_SIZE = 10
internal const val PREFETCH_DISTANCE = 5


fun <Key : Any, Value : Any> providePager(
    config: PagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        prefetchDistance = PREFETCH_DISTANCE
    ),
    pagingSourceFactory: PagingSource<Key, Value>
): Pager<Key, Value> =
    Pager(
        config = config,
        pagingSourceFactory = { pagingSourceFactory }
    )