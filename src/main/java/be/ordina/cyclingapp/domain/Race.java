package be.ordina.cyclingapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import be.ordina.cyclingapp.domain.enumeration.TypeOfRace;

/**
 * A Race.
 */
@Entity
@Table(name = "race")
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "date_of_race")
    private LocalDate dateOfRace;

    @Column(name = "amount_of_days")
    private Integer amountOfDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_race")
    private TypeOfRace typeOfRace;

    @Column(name = "year_of_first_race")
    private String yearOfFirstRace;

    @Column(name = "last_winner")
    private String lastWinner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Race name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public Race nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getDateOfRace() {
        return dateOfRace;
    }

    public Race dateOfRace(LocalDate dateOfRace) {
        this.dateOfRace = dateOfRace;
        return this;
    }

    public void setDateOfRace(LocalDate dateOfRace) {
        this.dateOfRace = dateOfRace;
    }

    public Integer getAmountOfDays() {
        return amountOfDays;
    }

    public Race amountOfDays(Integer amountOfDays) {
        this.amountOfDays = amountOfDays;
        return this;
    }

    public void setAmountOfDays(Integer amountOfDays) {
        this.amountOfDays = amountOfDays;
    }

    public TypeOfRace getTypeOfRace() {
        return typeOfRace;
    }

    public Race typeOfRace(TypeOfRace typeOfRace) {
        this.typeOfRace = typeOfRace;
        return this;
    }

    public void setTypeOfRace(TypeOfRace typeOfRace) {
        this.typeOfRace = typeOfRace;
    }

    public String getYearOfFirstRace() {
        return yearOfFirstRace;
    }

    public Race yearOfFirstRace(String yearOfFirstRace) {
        this.yearOfFirstRace = yearOfFirstRace;
        return this;
    }

    public void setYearOfFirstRace(String yearOfFirstRace) {
        this.yearOfFirstRace = yearOfFirstRace;
    }

    public String getLastWinner() {
        return lastWinner;
    }

    public Race lastWinner(String lastWinner) {
        this.lastWinner = lastWinner;
        return this;
    }

    public void setLastWinner(String lastWinner) {
        this.lastWinner = lastWinner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Race race = (Race) o;
        if (race.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), race.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Race{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", dateOfRace='" + getDateOfRace() + "'" +
            ", amountOfDays=" + getAmountOfDays() +
            ", typeOfRace='" + getTypeOfRace() + "'" +
            ", yearOfFirstRace='" + getYearOfFirstRace() + "'" +
            ", lastWinner='" + getLastWinner() + "'" +
            "}";
    }
}
