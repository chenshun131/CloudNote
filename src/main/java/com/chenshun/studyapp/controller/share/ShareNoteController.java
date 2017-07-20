package com.chenshun.studyapp.controller.share;

import com.chenshun.studyapp.service.ShareService;
import com.chenshun.utils.web.rest.RestResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * User: mew <p />
 * Time: 17/7/20 11:08  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Controller
@RequestMapping("/share")
public class ShareNoteController {

    @Resource
    private ShareService shareService;

    @RequestMapping("/add")
    @ResponseBody
    public RestResultDTO execute(String noteId) {
        return shareService.shareNote(noteId);
    }

    @RequestMapping("/search")
    @ResponseBody
    public RestResultDTO execute1(String shareTitle, Integer page) {
        return shareService.searchNote(shareTitle, page);
    }

    @RequestMapping("/loadnote")
    @ResponseBody
    public RestResultDTO execute2(String shareId) {
        return shareService.searchShare(shareId);
    }

}
