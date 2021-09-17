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

package net.maritimeconnectivity.serviceregistry.models.dto;

import net.maritimeconnectivity.serviceregistry.models.JsonSerializable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The Doc DTO Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DocDto implements Serializable, JsonSerializable  {

    // Class Variables
    private Long id;
    @NotNull
    private String name;
    private String comment;
    @NotNull
    private String mimetype;
    @NotNull
    private byte[] filecontent;
    private String filecontentContentType;

    /**
     * Instantiates a new Doc dto.
     */
    public DocDto() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets mimetype.
     *
     * @return the mimetype
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * Sets mimetype.
     *
     * @param mimetype the mimetype
     */
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    /**
     * Get filecontent byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getFilecontent() {
        return filecontent;
    }

    /**
     * Sets filecontent.
     *
     * @param filecontent the filecontent
     */
    public void setFilecontent(byte[] filecontent) {
        this.filecontent = filecontent;
    }

    /**
     * Gets filecontent content type.
     *
     * @return the filecontent content type
     */
    public String getFilecontentContentType() {
        return filecontentContentType;
    }

    /**
     * Sets filecontent content type.
     *
     * @param filecontentContentType the filecontent content type
     */
    public void setFilecontentContentType(String filecontentContentType) {
        this.filecontentContentType = filecontentContentType;
    }

}
