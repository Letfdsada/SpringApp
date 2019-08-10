package com.letfdsada.springTest.controller;

import com.letfdsada.springTest.StringShortcuts;
import com.letfdsada.springTest.domain.Message;
import com.letfdsada.springTest.domain.User;
import com.letfdsada.springTest.repos.MessageRepo;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class MainController {

    //Зависимость автоматически получится из конструктора
    private final MessageRepo messageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping("/")
    public String greeting(){
        return "greeting";
    }

    @GetMapping("/messages")
    public String getMessages(@RequestParam(required = false, defaultValue = "") String filter,
                              Model model){
        Iterable<Message> messages;

        messages = filter.isEmpty()
                ? messageRepo.findAll()
                : messageRepo.findByTag(filter);

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "messages";
    }

    @PostMapping("/messages")
    public String addMessage(@AuthenticationPrincipal User user,
                             @RequestParam String text,
                             @RequestParam String tag,
                             @RequestParam("file") MultipartFile file,
                             Model model) throws IOException {
        Message message = new Message(text, tag, user);

        if(file != null && !file.getOriginalFilename().isEmpty()){
            String realPath = StringShortcuts.getRelativeToAbsolutePath(uploadPath);
            File dir = new File(realPath);

            if(!dir.exists()){
                dir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resFilename = uuidFile + "." + Paths.get(Objects.requireNonNull(file.getOriginalFilename())).getFileName();

            file.transferTo(Paths.get(realPath, resFilename).toAbsolutePath());

            message.setFilename(resFilename);
        }

        messageRepo.save(message);
        model.addAttribute("messages",messageRepo.findAll());
        return "messages";
    }


}