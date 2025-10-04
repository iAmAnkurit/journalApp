package com.ankurit.journalApp.controller;


import com.ankurit.journalApp.entity.JournalEntry;
import com.ankurit.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll()
    {
        List<JournalEntry> journalEntryList = journalEntryService.getAll();
        if(journalEntryList!=null && !journalEntryList.isEmpty())
        {
            return new ResponseEntity<List<JournalEntry>>(journalEntryList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry)
    {
        try
        {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry( myEntry );
            return new ResponseEntity<JournalEntry>(myEntry,HttpStatus.CREATED) ;
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId)
    {
        Optional<JournalEntry> journalEntry=journalEntryService.getJournalEntryById(myId);
        if(journalEntry.isPresent())
        {
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId myId)
    {
        journalEntryService.deleteJournalEntry(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry)
    {
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(id).orElse(null);

        if(oldEntry != null)
        {
            oldEntry.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("")?myEntry.getTitle():oldEntry.getTitle());
            oldEntry.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")?myEntry.getContent():oldEntry.getContent());
            journalEntryService.saveEntry( oldEntry );
            return new ResponseEntity<JournalEntry>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
