package com.journal.discipline.tracker.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.journal.discipline.tracker.Config.AppConfig;
import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Exceptions.UnauthorizationException;
import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Repository.JournalRepository;
import com.journal.discipline.tracker.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class JournalServiceImpl implements JournalService{


    private static final Logger logger = LoggerFactory.getLogger(JournalServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JournalRepository repository;

    @Autowired
    private UserRepository userRespository;

 
    /*TODO:
       - Ensure 1 Journal PER DAY
     */

     @Transactional
    public JournalDTO createJournalEntry(JournalDTO journalDTO, UUID userId) {
        LocalDate loggedDate = LocalDate.now();
  
        /*First map the DTO to 
         * to the Journal Model so you can query to see if the entry is present in the DB
        - Add a flag for journal Create today
         */



        /*Only use when your sure they exist i.e when user sends jwt they are authenticated so they exist
        it will allow you to set the relationship with the foriegn key with loading the whole object*/
      User user = userRespository.getReferenceById(userId);

      JournalEntry entry = modelMapper.map(journalDTO, JournalEntry.class);
        
      /*Chcking if they have made a submission today, if so then do not allow another
       */
       Optional<JournalEntry> savedEntry = repository.findByUserIdAndSubmissionDate(userId, LocalDate.now());
       if(savedEntry.isPresent() ){
        throw new ApiException("Daily Submission is already pressent");
       }
       /*Save submission and user*/
            entry.setUser(user);
            entry.setSubmissionDate(loggedDate);
             repository.save(entry);
             return modelMapper.map(entry, JournalDTO.class);
           
         
    

     
    }

    

    
    @Override
    public JournalResponse getAllJournalEntry(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, UUID userId) {
       Sort sortAndOrderBy = sortOrder.equalsIgnoreCase("asc") ? 
       Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

      


       PageRequest pageDetails = PageRequest.of(pageNumber, pageSize, sortAndOrderBy);

       Page<JournalEntry> journalPage =repository.findAllByUserId(pageDetails, userId);
       List<JournalEntry> journals = journalPage.getContent();

        if(journals.isEmpty()){
            return null;
        }

       List<JournalDTO> journalDTOs = journals.stream()
       .map(journal -> modelMapper.map(journal, JournalDTO.class)).collect(Collectors.toList());

       JournalResponse response = new JournalResponse();
       response.setContent(journalDTOs);
       response.setPageNumber(journalPage.getNumber());
       response.setTotalPages(journalPage.getTotalPages());
       response.setPageSize(journalPage.getSize());
       response.setLastPage(journalPage.isLast());


       return response;

    }


    /*To do  ADD if the user Id doesnt match throw 403
     * or do find by UserId and Journal Id
    */
    @Override
    public JournalDTO updateJournalEntry(JournalDTO journalDTO, UUID entryId, UUID userId){
        // 1) Search for the entry by Id


        Optional<JournalEntry> entry =repository.findById(entryId);
        JournalEntry foundEntry = entry.orElseThrow(()-> new ApiException("Cannot find Entry with Id: " +entryId));

        if(foundEntry.getUser().getId().compareTo(userId) !=0){
        throw new UnauthorizationException("User does not have access to this habit");
        }

        JournalEntry savedJournalEntry = modelMapper.map(journalDTO, JournalEntry.class);
        savedJournalEntry.setJournalId(entryId);
        savedJournalEntry.setCreatedAt(LocalDateTime.now());
        foundEntry = repository.save(savedJournalEntry);
        return modelMapper.map(foundEntry, JournalDTO.class);
    }
  

     /*To do  ADD if the user Id doesnt match throw 403
     * or do find by UserId and Journal Id
    */

    @Override
    public JournalDTO deleteJournalEntry(UUID jorunalId, UUID userId){
        JournalEntry entry = repository.findById(jorunalId).orElseThrow(
            ()-> new ApiException("Entry with Id :" + jorunalId + "not found"));

            if(entry.getUser().getId().compareTo(userId) !=0){
                throw new UnauthorizationException("User does not have access to this habit");
            }
        repository.delete(entry);
        return modelMapper.map(entry, JournalDTO.class);
       

    }


}
