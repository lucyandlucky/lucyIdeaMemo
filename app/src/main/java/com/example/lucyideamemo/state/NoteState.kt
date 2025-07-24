package com.example.lucyideamemo.state

import com.example.lucyideamemo.bean.NoteShowBean

data class NoteState(
    val notes: List<NoteShowBean> = emptyList(),
    val title: String = "",
    val content: String = "",
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val editingNote: NoteShowBean? = null
)
