public abstract class User { //superclass user
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract boolean login(String username, String password);

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
