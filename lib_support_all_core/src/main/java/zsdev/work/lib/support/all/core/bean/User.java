package zsdev.work.lib.support.all.core.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created: by 2023-10-03 19:09
 * Description:
 * Author: 张松
 */
public class User implements Serializable {
    private String name;
    private String sex;

    public User() {
    }

    public User(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }


    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
