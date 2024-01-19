package com.iyanuoluwa.iyanuoluwa.newHire.Repository;

import com.iyanuoluwa.iyanuoluwa.newHire.Controller.newHireServices;
import com.iyanuoluwa.iyanuoluwa.newHire.Models.newHire;

import java.sql.SQLException;

public interface newHireDAO {
    public void createInfoNewHire(newHire newHire,newHire newHire1, newHireServices newHireServices);

    public void updateInfoNewHire(newHire newHire, newHireServices newHireServices) throws SQLException;

    public void getInfoNewHire(newHire newHire,newHireServices newHireServices ) throws SQLException;

    public void deleteInfoNewHire(newHire newHire,newHireServices newHireServices) throws SQLException;


}
