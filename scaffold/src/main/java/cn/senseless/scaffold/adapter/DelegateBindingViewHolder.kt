package cn.senseless.scaffold.adapter

import androidx.databinding.ViewDataBinding

class DelegateBindingViewHolder<T : ViewDataBinding>(val binding: T) : DelegateViewHolder(binding.root) {
}