package com.ankurit.journalApp.controller;


import com.ankurit.journalApp.entity.JournalEntry;
import com.ankurit.journalApp.entity.User;
import com.ankurit.journalApp.service.JournalEntryService;
import com.ankurit.journalApp.service.UserService;
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

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName)
    {
        User user= userService.findByUserName(userName);
        List<JournalEntry> journalEntryList = user.getJournalEntries();
        if(journalEntryList!=null && !journalEntryList.isEmpty())
        {
            return new ResponseEntity<List<JournalEntry>>(journalEntryList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName)
    {
        try
        {
            journalEntryService.saveEntry( myEntry , userName);
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

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId myId,@PathVariable String userName)
    {
        journalEntryService.deleteJournalEntry(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable String userName, @PathVariable ObjectId id,@RequestBody JournalEntry myEntry)
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
