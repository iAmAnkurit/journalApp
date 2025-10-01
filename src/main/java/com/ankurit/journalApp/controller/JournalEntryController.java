package com.ankurit.journalApp.controller;


import com.ankurit.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries=new HashMap<Long, JournalEntry>();


    @GetMapping
    public List<JournalEntry> getAll()
    {
        return new ArrayList<JournalEntry>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry)
    {
        journalEntries.put(myEntry.getId(),myEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getEntryById(@PathVariable Long myId)
    {
        return journalEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournal(@PathVariable Long myId)
    {
        return journalEntries.remove(myId);
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(@PathVariable Long id,@RequestBody JournalEntry myEntry)
    {
        return journalEntries.put(id,myEntry);
    }
}
