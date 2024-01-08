package com.iyanuoluwa.newHire.Repository;

import com.iyanuoluwa.newHire.Models.newHire;

public interface newHireDAO {
    public void createNewHire(newHire newHire);

    public void updateNewHire(newHire newHire);

    newHire findNewhire(String id);

}
