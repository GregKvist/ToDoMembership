package se.f4.todof4;

import java.util.Objects;

public class Account {

    private Long id;
    private String name;
    private String email;
    private String pwd;

    public Account() {
    }

    public Account(Long id, String name, String email, String pwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(name, account.name) &&
                Objects.equals(email, account.email) &&
                Objects.equals(pwd, account.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, pwd);
    }
}
