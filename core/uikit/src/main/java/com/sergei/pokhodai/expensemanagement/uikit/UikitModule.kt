package com.sergei.pokhodai.expensemanagement.uikit

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItemView
import com.sergei.pokhodai.expensemanagement.uikit.dropdown.DropDownItem
import com.sergei.pokhodai.expensemanagement.uikit.dropdown.DropDownItemView
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItemView
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItem
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItemView
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItemView
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItemView
import com.sergei.pokhodai.expensemanagement.uikit.select.SelectItem
import com.sergei.pokhodai.expensemanagement.uikit.select.SelectItemView
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItemView

object UikitModule {
    fun register() {
        RecyclerRegister.Builder()
            .add(clazz = ButtonItem.State::class.java, onView = ::ButtonItemView)
            .add(clazz = TextFieldItem.State::class.java, onView = ::TextFieldItemView)
            .add(clazz = DropDownItem.State::class.java, onView = ::DropDownItemView)
            .add(clazz = SelectItem.State::class.java, onView = ::SelectItemView)
            .add(clazz = CategoryKindItem.State::class.java, onView = ::CategoryKindItemView)
            .add(clazz = RequestItem.State::class.java, onView = ::RequestItemView)
            .add(clazz = RequestItem.State.Empty::class.java, onView = ::RequestItemView)
            .add(clazz = HeaderItem.State::class.java, onView = ::HeaderItemView)
            .add(clazz = TagItem.State::class.java, onView = ::TagItemView)
            .build()
    }
}