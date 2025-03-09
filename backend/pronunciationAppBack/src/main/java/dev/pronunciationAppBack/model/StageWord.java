package dev.pronunciationAppBack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StageWord {

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int listenedQty;
    private Date lastUpdatedDateTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_FK")
    private Word wordFK;

    public enum Status {
        DONE, PENDING, FAIL
    }
}
