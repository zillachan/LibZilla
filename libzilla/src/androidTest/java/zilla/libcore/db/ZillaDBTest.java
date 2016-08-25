package zilla.libcore.db;

import org.junit.Before;
import org.junit.Test;
import zilla.libcore.db.model.UserModel;
import zilla.libcore.file.PropertiesManager;

/**
 * Description:
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-08-25
 */
public class ZillaDBTest {

    @Before
    public void init() {
//        DBHelper.init(Application, PropertiesManager.get("dbname"), Integer.parseInt(PropertiesManager.get("dbversion", "1")));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() throws Exception {
        UserModel user = new UserModel();
        user.setName("bob");
        user.setAddress("beijing");
        boolean result = ZillaDB.getInstance().save(user);
        assert result;
    }

    @Test
    public void saveList() throws Exception {

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

    @Test
    public void query() throws Exception {

    }

    @Test
    public void query1() throws Exception {

    }

    @Test
    public void query2() throws Exception {

    }

    @Test
    public void query3() throws Exception {

    }

}