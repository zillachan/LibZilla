package zilla.libcore.db.model;

import zilla.libcore.db.Id;
import zilla.libcore.db.Table;

import java.io.Serializable;

/**
 * Description: user model
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-08-25
 */
@Table("t_user")
public class UserModel implements Serializable {

    @Id
    private long id;

    private String name;

    private String address;

    public UserModel() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
