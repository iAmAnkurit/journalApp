package com.ankurit.journalApp.controller;


import com.ankurit.journalApp.entity.JournalEntry;
import com.ankurit.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll()
    {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry)
    {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry( myEntry );
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getEntryById(@PathVariable ObjectId myId)
    {
        return journalEntryService.getJournalEntryById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournal(@PathVariable ObjectId myId)
    {
        journalEntryService.deleteJournalEntry(myId);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry)
    {
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(id).orElse(null);

        if(oldEntry != null)
        {
            oldEntry.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("")?myEntry.getTitle():oldEntry.getTitle());
            oldEntry.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")?myEntry.getContent():oldEntry.getContent());

        }
        journalEntryService.saveEntry( oldEntry );
        return oldEntry;
    }
}
