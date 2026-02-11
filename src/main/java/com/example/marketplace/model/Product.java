package com.example.marketplace.model;
import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    private String genre;
    private String publisher;
    private String description;
    private double price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", unique = true)
    private Image image;

    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setAuthor(String author) { this.author = author; }
    public String getAuthor() { return author; }

    public void setGenre(String genre) { this.genre = genre; }
    public String getGenre() { return genre; }

    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getPublisher() { return publisher; }

    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }

    public void setPrice(double price) { this.price = price; }
    public double getPrice() { return price; }

    public Long getId() { return id; }

}
