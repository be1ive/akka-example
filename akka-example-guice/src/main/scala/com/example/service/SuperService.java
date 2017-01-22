package com.example.service;

import com.google.inject.ImplementedBy;

/**
 * Created by nik.denisenko on 22/01/2017.
 */
@ImplementedBy(value = SuperServiceImpl.class)
public interface SuperService {

    void superMethod(String id);

}
