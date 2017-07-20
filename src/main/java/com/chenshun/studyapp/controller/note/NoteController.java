package com.chenshun.studyapp.controller.note;

import com.chenshun.studyapp.domain.Note;
import com.chenshun.studyapp.entity.SearchNote;
import com.chenshun.studyapp.service.NoteService;
import com.chenshun.utils.web.rest.RestResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * User: mew <p />
 * Time: 17/7/15 12:21  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Controller
@RequestMapping("/note")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Resource
    private NoteService noteService;

    @RequestMapping("/loadnotes")
    @ResponseBody
    public RestResultDTO loadNotes(String bookId) {
        logger.info("进入LoadNotesController");

        return noteService.loadNotes(bookId);
    }

    @RequestMapping("/add")
    @ResponseBody
    public RestResultDTO addNote(Note note) {
        return noteService.addNote(note);
    }

    @RequestMapping("/loadnote")
    @ResponseBody
    public RestResultDTO loadNote(String noteId) {
        return noteService.loadNote(noteId);
    }

    @RequestMapping("/update")
    @ResponseBody
    public RestResultDTO updateNote(Note note) {
        return noteService.updateNote(note);
    }

    @RequestMapping("/first_delete")
    @ResponseBody
    public RestResultDTO firstDelete(String noteId) {
        return noteService.deleteNote(noteId);
    }

    @RequestMapping("/move")
    @ResponseBody
    public RestResultDTO moveNote(String bookId, String noteId) {
        return noteService.moveNote(bookId, noteId);
    }

    @RequestMapping("/loadRecycleNotes")
    @ResponseBody
    public RestResultDTO loadRecycleNotes(String userId) {
        return noteService.loadRecycleNotes(userId);
    }

    @RequestMapping("/finalDelete")
    @ResponseBody
    public RestResultDTO finalDeleteNote(String noteId) {
        return noteService.finalDeleteNote(noteId);
    }

    @RequestMapping("/addStore")
    @ResponseBody
    public RestResultDTO updateToStore(String shareId) {
        return noteService.updateToStore(shareId);
    }

    @RequestMapping("/loadStoreNotes")
    @ResponseBody
    public RestResultDTO loadStoreNotes(String userId) {
        return noteService.loadStoreNotes(userId);
    }

    @RequestMapping("/manager")
    @ResponseBody
    public RestResultDTO loadManager(String title, String status, String begin, String end, String userId) {
        return noteService.loadManager(title, status, begin, end, userId);
    }

    @RequestMapping("/share")
    @ResponseBody
    public RestResultDTO shareNote(String noteId) {
        return noteService.shareNote(noteId);
    }

    @RequestMapping("/search")
    @ResponseBody
    public RestResultDTO searchShareNotes(String keyword) {
        return noteService.searchShareNotes(keyword);
    }

    @RequestMapping("/hight_search")
    @ResponseBody
    public RestResultDTO searchNotes(SearchNote search) {
        return noteService.searchNotes(search);
    }

    @RequestMapping("/batch_delete")
    @ResponseBody
    public RestResultDTO batchDelete(String noteIds) {
        String[] ids = noteIds.split(",");
        return noteService.batchDelete(ids);
    }

}
