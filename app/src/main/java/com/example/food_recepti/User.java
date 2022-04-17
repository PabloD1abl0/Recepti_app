package com.example.food_recepti;

public class User {
    String _id, username, name, email, password;
    int __v;
    public User() {
        _id = username = name = email = password = "";
        __v = 0;
    }
    public User(String i, String u, String n, String m, String p, int v){
        _id = i;
        username = u;
        //name = n;
        email = m;
        password = p;
        this.__v = v;
    }
    public User(User user){
        this._id = user._id;
        this.username = user.username;
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.__v = user.__v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + email + '\'' +
                ", password='" + password + '\'' +
                ", v=" + __v +
                '}';
    }
}
