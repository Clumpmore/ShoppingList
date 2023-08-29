package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            ITEM_ACTIVE -> R.layout.item_shop_enabled
            ITEM_INACTIVE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.shopItem =shopItem
            }
            is ItemShopEnabledBinding -> {
                binding.shopItem =shopItem
            }
        }

    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) ITEM_ACTIVE
        else ITEM_INACTIVE
    }


    companion object {
        const val ITEM_ACTIVE = 100
        const val ITEM_INACTIVE = 101
        const val MAX_POOL_SIZE = 30
    }
}