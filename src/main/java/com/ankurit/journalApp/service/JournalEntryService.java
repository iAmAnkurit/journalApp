package com.ankurit.journalApp.service;

import com.ankurit.journalApp.entity.JournalEntry;
import com.ankurit.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void  deleteJournalEntry(ObjectId id)
    {
        journalEntryRepository.deleteById(id);
    }
}
