package com.daniel.codetest.presentation.interfaces

import com.daniel.codetest.localdb.PeoplesInfoListItem

interface PeopleItemClickListener {
    fun onPeopleItemClickListener(model: PeoplesInfoListItem)
}