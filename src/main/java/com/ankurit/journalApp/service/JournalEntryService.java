package com.ankurit.journalApp.service;

import com.ankurit.journalApp.entity.JournalEntry;
import com.ankurit.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
}
