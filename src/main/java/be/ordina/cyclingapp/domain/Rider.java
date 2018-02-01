package be.ordina.cyclingapp.domain;


import be.ordina.cyclingapp.converter.LocalDateConverter;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Rider.
 */
@Entity
@Table(name = "rider")
public class Rider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate dateOfBirth;

    @Column(name = "amount_of_victories")
    private Integer amountOfVictories;

    @Column(name = "length")
    private Integer length;

    @Column(name= "weight")
    private Double weight;

    @ManyToOne
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Rider firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Rider lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Rider dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAmountOfVictories() {
        return amountOfVictories;
    }

    public Rider amountOfVictories(Integer amountOfVictories) {
        this.amountOfVictories = amountOfVictories;
        return this;
    }

    public void setAmountOfVictories(Integer amountOfVictories) {
        this.amountOfVictories = amountOfVictories;
    }

    public Integer getLength() {
        return length;
    }

    public Rider length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Team getTeam() {
        return team;
    }

    public Rider team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
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
        Rider rider = (Rider) o;
        if (rider.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rider.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rider{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", amountOfVictories=" + getAmountOfVictories() +
            ", length=" + getLength() +
            "}";
    }
}
