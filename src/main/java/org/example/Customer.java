package org.example;

import java.util.List;

public class Customer {
    private String title;

    @Override
    public String toString() {
        return "Customer{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", tags=" + tags +
                ", tablerons=" + tablerons +
                ", content='" + content + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    private Author author;
    private List<String> tags;


    private List<Tableron> tablerons;
    private String content;
    private String phoneNumber;

    public List<Tableron> getTablerons() {
        return tablerons;
    }

    public void setTablerons(List<Tableron> tablerons) {
        this.tablerons = tablerons;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
