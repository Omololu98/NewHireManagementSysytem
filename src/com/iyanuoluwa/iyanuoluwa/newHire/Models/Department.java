package com.iyanuoluwa.iyanuoluwa.newHire.Models;

public enum Department {
    Sales(24),
    Operations(87),
    IT(56),
    HumanResources(14);

    final int myDepartmentCode;
    // a constructor is needed when we pass in arguments to this enums attributes
    // also note that Enums can only be private or package level, so create them in packages that you need them in
    Department(int myDepartment){
        this.myDepartmentCode =myDepartment;
    }
}
