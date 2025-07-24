package com.example.lucyideamemo.utils

import com.example.lucyideamemo.App
import com.example.lucyideamemo.bean.Note
import com.example.lucyideamemo.db.repo.TagNoteRepo
import javax.inject.Inject

class FirstTimeManager @Inject constructor() {

    @Inject
    lateinit var tagNoteRepo: TagNoteRepo

    fun generateIntroduceNote() {
        lunchIo {
            if (tagNoteRepo.queryAllNotes().isNotEmpty()) {
                return@lunchIo
            }
            if (App.instance.isSystemLanguageEnglish()) {
                generateEnglisnIntroduceNote()
            } else {
                generateChineseIntroduceNote()
            }
        }

    }

    private fun generateChineseIntroduceNote() {
        val functionNote = Note(
            content = "#灵感 \n生活不止眼前的苟且 还有诗和远方. @深圳市"
        )
        tagNoteRepo.insertOrUpdate(functionNote)
    }

    private fun generateEnglisnIntroduceNote() {
        val functionNote = Note(
            content = "#Life \nLess is more.@New York City"
        )
        tagNoteRepo.insertOrUpdate(functionNote)
    }
}