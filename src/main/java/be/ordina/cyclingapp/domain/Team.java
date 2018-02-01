package be.ordina.cyclingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount_of_victories")
    private Integer amountOfVictories;

    @Column(name = "year_founded")
    private Integer yearFounded;

    @Column(name = "team_manager")
    private String teamManager;

    @Column(name = "brand_of_bicycle")
    private String brandOfBicycle;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private Set<Rider> riders = new HashSet<>();

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

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountOfVictories() {
        return amountOfVictories;
    }

    public Team amountOfVictories(Integer amountOfVictories) {
        this.amountOfVictories = amountOfVictories;
        return this;
    }

    public void setAmountOfVictories(Integer amountOfVictories) {
        this.amountOfVictories = amountOfVictories;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public Team yearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
        return this;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getTeamManager() {
        return teamManager;
    }

    public Team teamManager(String teamManager) {
        this.teamManager = teamManager;
        return this;
    }

    public void setTeamManager(String teamManager) {
        this.teamManager = teamManager;
    }

    public String getBrandOfBicycle() {
        return brandOfBicycle;
    }

    public Team brandOfBicycle(String brandOfBicycle) {
        this.brandOfBicycle = brandOfBicycle;
        return this;
    }

    public void setBrandOfBicycle(String brandOfBicycle) {
        this.brandOfBicycle = brandOfBicycle;
    }

    public Set<Rider> getRiders() {
        return riders;
    }

    public Team riders(Set<Rider> riders) {
        this.riders = riders;
        return this;
    }

    public Team addRider(Rider rider) {
        this.riders.add(rider);
        rider.setTeam(this);
        return this;
    }

    public Team removeRider(Rider rider) {
        this.riders.remove(rider);
        rider.setTeam(null);
        return this;
    }

    public void setRiders(Set<Rider> riders) {
        this.riders = riders;
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
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amountOfVictories=" + getAmountOfVictories() +
            ", yearFounded=" + getYearFounded() +
            ", teamManager='" + getTeamManager() + "'" +
            ", brandOfBicycle='" + getBrandOfBicycle() + "'" +
            "}";
    }
}
