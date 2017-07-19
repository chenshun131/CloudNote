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
    public RestResultDTO moveNote(String bookId,String noteId){
        return noteService.moveNote(bookId, noteId);
    }

    @RequestMapping("/loadRecycleNotes.do")
    @ResponseBody
    public RestResultDTO loadRecycleNotes(String userId){
        NoteResult<List<Note>> result = noteService.loadRecycleNotes(userId);
        return result;
    }

    @RequestMapping("/finalDelete")//.do可以省略，可以进入Servlet说明自带.do
    @ResponseBody
    public NoteResult finalDeleteNote(String noteId){//参数和html里面传过来的（key）名字一样
        NoteResult result = noteService.finalDeleteNote(noteId);
        return result;
    }

    @RequestMapping("/addStore.do")//.do可以省略，可以进入Servlet说明自带.do
    @ResponseBody
    public NoteResult execute8(String shareId){//参数和html里面传过来的（key）名字一样
        NoteResult result = noteService.updateToStore(shareId);
        return result;
    }

    @RequestMapping("/loadStoreNotes.do")//.do可以省略，可以进入Servlet说明自带.do
    @ResponseBody
    public NoteResult<List<Note>> execute9(String userId){//参数和html里面传过来的（key）名字一样
        NoteResult<List<Note>> result = noteService.loadStoreNotes(userId);
        return result;
    }

    @RequestMapping("/manager.do")//.do可以省略，可以进入Servlet说明自带.do
    @ResponseBody
    public NoteResult<List<Note>> execute10(String title, String status, String begin,String end,String userId){//参数和html里面传过来的（key）名字一样
        NoteResult<List<Note>> result = noteService.loadManager(title, status, begin, end,userId);
        return result;
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
    public RestResultDTO execute(SearchNote search) {
        return noteService.searchNotes(search);
    }

    @RequestMapping("/batch_delete")
    @ResponseBody
    public RestResultDTO batchDelete(String noteIds) {
        String[] ids = noteIds.split(",");
        return noteService.batchDelete(ids);
    }

}
