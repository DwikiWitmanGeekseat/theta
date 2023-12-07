package co.flexidev.theta.model;

import co.flexidev.theta.helper.SecurityHelper;
import co.flexidev.theta.helper.Utility;
import co.flexidev.theta.security.Principal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @CreatedBy
    @Column(name = "creator_id", length = 255)
    private String creatorId;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "editor_id", length = 255)
    private String editorId;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created")
    private LocalDateTime created;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "edited")
    private LocalDateTime edited;

    @JsonIgnore
    @Column(name = "creator", length = 255)
    protected String creator;

    @JsonIgnore
    @Column(name = "editor", length = 255)
    protected String editor;

    @Column(name = "storageMap", columnDefinition = "TEXT")
    protected String storageMap;

    @Column(name = "active")
    private Boolean active = true;

    @Transient
    protected Map<String, Object> map = new HashMap<>();

    @PrePersist
    public void onSave() {
        Principal principal = SecurityHelper.getPrincipal();
        if (principal != null) {
            this.creator = Utility.gson.toJson(principal.essence());
            this.creatorId = principal.getId().toString();
        }
        this.created = Utility.now();
    }

    @PreUpdate
    public void onUpdate() {
        Principal principal = SecurityHelper.getPrincipal();
        if (principal != null) {
            this.editor = Utility.gson.toJson(principal.essence());
            this.editorId = principal.getId().toString();
        }
        this.edited = Utility.now();
    }

    public Object get(String key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    public Object set(String key, Object value) {
        if (map != null) {
            return map.put(key, value);
        }
        return null;
    }

    // user id and date managed by auditoraware
    public void createdBy(Principal principal) {
        this.setCreator(Utility.gson.toJson(principal.essence()));
    }

    public void editedBy(Principal principal) {
        this.setEditor(Utility.gson.toJson(principal.essence()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getEditorId() {
        return editorId;
    }

    public void setEditorId(String editorId) {
        this.editorId = editorId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getStorageMap() {
        return storageMap;
    }

    public void setStorageMap(String storageMap) {
        this.storageMap = storageMap;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
