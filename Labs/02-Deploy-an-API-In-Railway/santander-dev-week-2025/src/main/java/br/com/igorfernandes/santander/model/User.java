package br.com.igorfernandes.santander.model;


import br.com.igorfernandes.santander.model.item.Feature;
import br.com.igorfernandes.santander.model.item.News;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @OneToOne(cascade = CascadeType.ALL)
    private Card card;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Feature> features;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<News> news;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(final Card card) {
        this.card = card;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(final List<Feature> features) {
        this.features = features;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(final List<News> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account=" + account +
                ", card=" + card +
                ", features=" + features +
                ", news=" + news +
                '}';
    }
}
