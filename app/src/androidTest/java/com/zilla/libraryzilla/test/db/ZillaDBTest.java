package com.zilla.libraryzilla.test.db;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import com.zilla.libraryzilla.test.db.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import zilla.libcore.db.ZillaDB;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Description:
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-08-25
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ZillaDBTest {

//    @Rule
//    public ActivityTestRule<BindingActivity> mActivityRule = new ActivityTestRule<>(
//            BindingActivity.class);

//    @Before
//    public void setUp() throws Exception {
//
//    }

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setName("bob");
        user.setAddress("beijing");
        user.setEmail("bob@softtek.com");
        boolean result = ZillaDB.getInstance().save(user);
        assert result;
    }

    @Test
    public void saveList() throws Exception {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName("bob" + i);
            user.setAddress("beijing" + i);
            user.setEmail(i + "bob@softtek.com");
            userList.add(user);
        }
        boolean result = ZillaDB.getInstance().saveList(userList);
        assert result;
    }

    @Test
    public void query() throws Exception {
        List<User> users = ZillaDB.getInstance().query(User.class, "name = 'bob'");
        assert users.size() == 1;
    }

    @Test
    public void queryOne() throws Exception {
        User bob = ZillaDB.getInstance().query(User.class, "name = ?", new String[]{"bob"});
        assertThat(bob.getName(), is(equalTo("bob")));
    }

    @Test
    public void queryLimit() throws Exception {
        List<User> users = ZillaDB.getInstance().query(User.class, "name like ", new String[]{"bob%"}, "10");
        assert users.size() == 10;
    }

    @Test
    public void queryOrder() throws Exception {
        List<User> users = ZillaDB.getInstance().query(User.class, "name like ", new String[]{"bob%"}, "name DESC", "10");
        assert users.size() == 10;
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void deleteAll() throws Exception {

    }

    @Test
    public void delete1() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void update1() throws Exception {

    }

    @Test
    public void merge() throws Exception {

    }

    @Test
    public void updateAll() throws Exception {

    }

    @Test
    public void update2() throws Exception {

    }

    @Test
    public void update3() throws Exception {

    }

    @Test
    public void queryAll() throws Exception {

    }

    @Test
    public void queryById() throws Exception {

    }


}