package com.sergei.pokhodai.expensemanagement.ui.home.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sergei.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.sergei.pokhodai.expensemanagement.data.service.WalletDao

class WalletPagingSource(
    private val walletDao: WalletDao,
    private val date: String,
) : PagingSource<Int, WalletEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WalletEntity> {
        val page = params.key ?: 0

        return try {

            val entities = walletDao.getWalletByMonth(
                date = date,
                limit = params.loadSize,
                offset = page * params.loadSize
            )

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1,
                itemsBefore = LoadResult.Page.COUNT_UNDEFINED,
                itemsAfter = LoadResult.Page.COUNT_UNDEFINED
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WalletEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}