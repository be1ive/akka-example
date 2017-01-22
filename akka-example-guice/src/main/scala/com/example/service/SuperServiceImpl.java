package com.example.service;

import com.google.inject.Singleton;

/**
 * Created by nik.denisenko on 22/01/2017.
 */
@Singleton
public class SuperServiceImpl implements SuperService {

    @Override
    public void superMethod(String id) {
        System.out.println(id);
    }

}
