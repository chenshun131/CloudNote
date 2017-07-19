package com.chenshun.studyapp.service;

import com.chenshun.studyapp.domain.Note;
import com.chenshun.studyapp.entity.SearchNote;
import com.chenshun.utils.web.rest.RestResultDTO;

/**
 * User: mew <p />
 * Time: 17/7/15 12:23  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public interface NoteService {

    RestResultDTO loadNotes(String bookId);

    RestResultDTO addNote(Note note);

    RestResultDTO loadNote(String noteId);

    RestResultDTO updateNote(Note note);

    RestResultDTO deleteNote(String noteId);

    RestResultDTO moveNote(String bookId, String noteId);



    RestResultDTO loadRecycleNotes(String userId);

    RestResultDTO shareNote(String noteId);

    RestResultDTO searchShareNotes(String keyword);

    RestResultDTO searchNotes(SearchNote search);

    RestResultDTO batchDelete(String[] ids);

}
