/*
 * Copyright (c) 2021 Maritime Connectivity Platform Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.maritimeconnectivity.serviceregistry.services;

import lombok.extern.slf4j.Slf4j;
import net.maritimeconnectivity.serviceregistry.exceptions.DataNotFoundException;
import net.maritimeconnectivity.serviceregistry.models.domain.Xml;
import net.maritimeconnectivity.serviceregistry.repos.XmlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Xml.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Service
@Slf4j
@Transactional
public class XmlService {

    /**
     * The XML Repository.
     */
    @Autowired
    private XmlRepo xmlRepo;

    /**
     *  Get all the xmls.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Xml> findAll(Pageable pageable) {
        log.debug("Request to get all Xmls");
        return this.xmlRepo.findAll(pageable);
    }

    /**
     *  Get one xml by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Xml findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get Xml : {}", id);
        return this.xmlRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("", null));
    }

    /**
     * Save a xml.
     *
     * @param xml the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Xml save(Xml xml) {
        log.debug("Request to save Xml : {}", xml);
        return this.xmlRepo.save(xml);
    }

    /**
     *  Delete the  xml by id.
     *
     *  @param id the id of the entity
     */
    @Transactional(propagation = Propagation.NESTED)
    public void delete(Long id) throws DataNotFoundException {
        log.debug("Request to delete Xml : {}", id);
        if(this.xmlRepo.existsById(id)) {
            this.xmlRepo.deleteById(id);
        } else {
            throw new DataNotFoundException("No xml found for the provided ID", null);
        }
    }

}
